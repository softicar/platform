package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.structure.mysql.parser.DbMysqlCreateTableStatementParser.ColumnRow;

class InternalDbMysqlColumnTypeDeterminer {

	private final String typeName;

	public InternalDbMysqlColumnTypeDeterminer(ColumnRow columnRow) {

		this.typeName = columnRow.getTypeName();
	}

	public InternalDbMysqlColumnType determineColumnType() {

		try {
			return InternalDbMysqlColumnType.valueOf(typeName.toUpperCase());
		} catch (IllegalArgumentException exception) {
			throw new SofticarException(exception, "The database column type %s is not supported.", typeName.toUpperCase());
		}
	}
}
