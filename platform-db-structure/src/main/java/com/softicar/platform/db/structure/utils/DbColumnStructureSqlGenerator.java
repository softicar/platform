package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import java.util.List;

public class DbColumnStructureSqlGenerator {

	private final IDbColumnStructure columnStructure;
	private boolean isH2Database = false;
	private Integer autoIncrementStart = null;

	public DbColumnStructureSqlGenerator(IDbColumnStructure columnStructure) {

		this.columnStructure = columnStructure;
	}

	public DbColumnStructureSqlGenerator setIsH2Database(boolean isH2Database) {

		this.isH2Database = isH2Database;
		return this;
	}

	public DbColumnStructureSqlGenerator setAutoIncrementStart(Integer autoIncrementStart) {

		this.autoIncrementStart = autoIncrementStart;
		return this;
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append("`" + columnStructure.getNameOrThrow() + "` ")
			.append(getTypePart())
			.append(getUnsignedPart())
			.append(getCharacterSetPart())
			.append(getCollationPart())
			.append(getNullablePart())
			.append(getDefaultPart())
			.append(getOnUpdatePart())
			.append(getAutoIncrementPart())
			.append(getCommentPart())
			.toString();
	}

	private String getTypePart() {

		SqlFieldType fieldType = columnStructure.getFieldType();
		switch (fieldType) {
		case BIG_DECIMAL:
			return String.format("DECIMAL(%s, %s)", columnStructure.getPrecision(), columnStructure.getScale());
		case BOOLEAN:
			return "BOOL";
		case BYTE_ARRAY:
			return getByteArrayType();
		case DAY:
			return "DATE";
		case DAY_TIME:
			if (columnStructure.isTimestamp()) {
				return "TIMESTAMP";
			} else {
				return "DATETIME";
			}
		case DOUBLE:
			return "DOUBLE";
		case ENUM:
			List<String> enumValues = columnStructure.getEnumValues();
			if (isH2Database) {
				enumValues.removeIf(String::isEmpty);
			}
			return String.format("ENUM(%s)", Imploder.implodeQuoted(enumValues, ",", "'"));
		case FLOAT:
			return "FLOAT";
		case INTEGER:
			return getIntegerType();
		case LONG:
			return "BIGINT";
		case STRING:
			return getStringType();
		case TIME:
			return "TIME";
		}
		throw new SofticarUnknownEnumConstantException(fieldType);
	}

	private String getByteArrayType() {

		switch (columnStructure.getLengthBits()) {
		case 0:
			return getVarbinaryType();
		case 8:
			return "TINYBLOB";
		case 16:
			return "BLOB";
		case 24:
			return "MEDIUMBLOB";
		case 32:
			return "LONGBLOB";
		default:
			throw new SofticarDeveloperException(
				String.format("Type of byte array field with %s length bits cannot be determined.", columnStructure.getLengthBits()));
		}
	}

	private String getIntegerType() {

		switch (columnStructure.getBits()) {
		case 8:
			return "TINYINT";
		case 16:
			return "SMALLINT";
		case 24:
			return "MEDIUMINT";
		case 32:
			return "INT";
		case 64:
			return "BIGINT";
		default:
			throw new SofticarDeveloperException(String.format("Type of integer field with %s bits cannot be determined.", columnStructure.getBits()));
		}
	}

	private String getStringType() {

		switch (columnStructure.getLengthBits()) {
		case 0:
			return getVarcharType();
		case 8:
			return "TINYTEXT";
		case 16:
			return "TEXT";
		case 24:
			return "MEDIUMTEXT";
		case 32:
			return "LONGTEXT";
		default:
			throw new SofticarDeveloperException(
				String.format("Type of string field with %s length bits cannot be determined.", columnStructure.getLengthBits()));
		}
	}

	private String getVarbinaryType() {

		int maxLength = columnStructure.getMaxLength();
		if (isH2Database && maxLength == 0) {
			return "VARBINARY";
		} else {
			return String.format("VARBINARY(%s)", maxLength);
		}
	}

	private String getVarcharType() {

		int maxLength = columnStructure.getMaxLength();
		if (isH2Database && maxLength == 0) {
			return "VARCHAR";
		} else {
			return String.format("VARCHAR(%s)", maxLength);
		}
	}

	private String getUnsignedPart() {

		return columnStructure.isUnsigned()? " UNSIGNED" : "";
	}

	private String getCharacterSetPart() {

		String characterSet = columnStructure.getCharacterSet();
		return characterSet == null || isH2Database? "" : String.format(" CHARACTER SET %s", characterSet);
	}

	private String getCollationPart() {

		String collation = columnStructure.getCollation();
		return collation == null || isH2Database? "" : String.format(" COLLATE %s", collation);
	}

	private String getNullablePart() {

		if (columnStructure.isNullable()) {
			return isExplicitNullableRequired()? " NULL" : "";
		} else {
			return " NOT NULL";
		}
	}

	private boolean isExplicitNullableRequired() {

		// This is to fix a defect in MariaDb:
		// The implicit NULL must be specified for timestamp columns that are DEFAULT NULL.
		return columnStructure.isTimestamp() && columnStructure.getDefaultType() == DbColumnDefaultType.NULL;
	}

	private String getDefaultPart() {

		DbColumnDefaultType defaultType = columnStructure.getDefaultType();
		switch (defaultType) {
		case NORMAL:
			if (isH2Database && columnStructure.getDefaultValue().isEmpty() && columnStructure.getFieldType().equals(SqlFieldType.ENUM)) {
				return "";
			}
			return String.format(" DEFAULT '%s'", columnStructure.getDefaultValue().replace("'", "''"));
		case CURRENT_TIMESTAMP:
			return " DEFAULT CURRENT_TIMESTAMP";
		case NULL:
			return " DEFAULT NULL";
		case NONE:
			return "";
		}
		throw new SofticarUnknownEnumConstantException(defaultType);
	}

	private String getOnUpdatePart() {

		if (columnStructure.isOnUpdateNow()) {
			return " ON UPDATE CURRENT_TIMESTAMP";
		}
		return "";
	}

	private String getAutoIncrementPart() {

		if (columnStructure.isAutoIncrement()) {
			if (autoIncrementStart != null) {
				return " AUTO_INCREMENT(" + autoIncrementStart + ")";
			} else {
				return " AUTO_INCREMENT";
			}
		}
		return "";
	}

	private String getCommentPart() {

		String comment = columnStructure.getComment();
		return comment != null? String.format(" COMMENT '%s'", comment.replace("'", "''")) : "";
	}
}
