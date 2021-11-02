package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ColumnRow;
import java.util.List;

class InternalDbMysqlColumnMaxLengthDeterminer {

	private final SqlFieldType fieldType;
	private final List<String> typeParameters;
	private final String columnName;

	public InternalDbMysqlColumnMaxLengthDeterminer(SqlFieldType fieldType, ColumnRow columnRow) {

		this.fieldType = fieldType;
		this.typeParameters = columnRow.getTypeParameters();
		this.columnName = columnRow.getColumnName();
	}

	public int determineMaxLength() {

		if (fieldType == SqlFieldType.BYTE_ARRAY || fieldType == SqlFieldType.STRING) {
			if (typeParameters.size() == 0) {
				return 0;
			} else if (typeParameters.size() == 1) {
				return Integer.parseInt(typeParameters.get(0));
			} else {
				throw new SofticarException("Too many type parameters for column '%s'.", columnName);
			}
		} else {
			return 0;
		}
	}
}
