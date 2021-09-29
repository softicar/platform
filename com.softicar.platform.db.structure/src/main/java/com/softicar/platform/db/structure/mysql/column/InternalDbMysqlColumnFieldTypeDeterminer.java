package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ColumnRow;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.utils.DbColumnCommentParser;
import java.util.List;

class InternalDbMysqlColumnFieldTypeDeterminer {

	private final IDbTableStructure tableStructure;
	private final ColumnRow columnRow;

	public InternalDbMysqlColumnFieldTypeDeterminer(IDbTableStructure tableStructure, DbMysqlCreateTableStatementParser.ColumnRow columnRow) {

		this.tableStructure = tableStructure;
		this.columnRow = columnRow;
	}

	public SqlFieldType determineFieldType() {

		InternalDbMysqlColumnType columnType = new InternalDbMysqlColumnTypeDeterminer(columnRow).determineColumnType();

		if (isBoolean(columnType)) {
			return SqlFieldType.BOOLEAN;
		}

		if (isDouble(columnType)) {
			return SqlFieldType.DOUBLE;
		}

		switch (columnType) {
		case CHAR:
		case TINYTEXT:
		case TEXT:
		case MEDIUMTEXT:
		case LONGTEXT:
		case VARCHAR:
			return SqlFieldType.STRING;
		case DATE:
			return SqlFieldType.DAY;
		case DATETIME:
		case TIMESTAMP:
			return SqlFieldType.DAY_TIME;
		case TIME:
			return SqlFieldType.TIME;
		case DECIMAL:
			return SqlFieldType.BIG_DECIMAL;
		case DOUBLE:
			return SqlFieldType.DOUBLE;
		case FLOAT:
			return SqlFieldType.FLOAT;
		case ENUM:
			return SqlFieldType.ENUM;
		case BINARY:
		case VARBINARY:
		case TINYBLOB:
		case BLOB:
		case MEDIUMBLOB:
		case LONGBLOB:
			return SqlFieldType.BYTE_ARRAY;
		case BIGINT:
			return SqlFieldType.LONG;
		case BOOL:
			return SqlFieldType.BOOLEAN;
		case INT:
		case MEDIUMINT:
		case SMALLINT:
		case TINYINT:
			return SqlFieldType.INTEGER;
		default:
			throw new DbMysqlUnsupportedColumnTypeException(tableStructure.getTableName(), columnRow.getColumnName(), columnType.toString());
		}
	}

	private boolean isBoolean(InternalDbMysqlColumnType columnType) {

		if (columnType == InternalDbMysqlColumnType.TINYINT) {
			List<String> typeParameters = columnRow.getTypeParameters();
			if (typeParameters.size() == 1 && columnRow.getTypeParameters().get(0).equals("1")) {
				return true;
			}
		}
		return false;
	}

	private boolean isDouble(InternalDbMysqlColumnType columnType) {

		if (columnType == InternalDbMysqlColumnType.DECIMAL) {
			return new DbColumnCommentParser(columnRow.getComment()).isDoubleOverride();
		} else {
			return false;
		}
	}
}
