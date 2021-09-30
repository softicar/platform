package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ColumnRow;
import java.util.List;

class InternalDbMysqlColumnPrecisionDeterminer {

	private final SqlFieldType fieldType;
	private final List<String> typeParameters;
	private final String columnName;

	public InternalDbMysqlColumnPrecisionDeterminer(SqlFieldType fieldType, ColumnRow columnRow) {

		this.fieldType = fieldType;
		this.typeParameters = columnRow.getTypeParameters();
		this.columnName = columnRow.getColumnName();
	}

	public int determinePrecision() {

		if (fieldType == SqlFieldType.BIG_DECIMAL) {
			if (typeParameters.size() == 0) {
				return 0;
			} else if (typeParameters.size() == 1 || typeParameters.size() == 2) {
				return Integer.parseInt(typeParameters.get(0));
			} else {
				throw new SofticarException("Too many type parameters for decimal column '%s'.", columnName);
			}
		} else {
			return 0;
		}
	}
}
