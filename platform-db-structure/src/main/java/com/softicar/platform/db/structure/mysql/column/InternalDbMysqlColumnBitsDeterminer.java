package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.sql.type.SqlFieldType;

class InternalDbMysqlColumnBitsDeterminer {

	private final SqlFieldType fieldType;
	private final InternalDbMysqlColumnType columnType;

	public InternalDbMysqlColumnBitsDeterminer(SqlFieldType fieldType, InternalDbMysqlColumnType columnType) {

		this.fieldType = fieldType;
		this.columnType = columnType;
	}

	public int determineBits() {

		if (fieldType == SqlFieldType.INTEGER || fieldType == SqlFieldType.LONG) {
			switch (columnType) {
			case TINYINT:
				return 8;
			case SMALLINT:
				return 16;
			case MEDIUMINT:
				return 24;
			case INT:
				return 32;
			case BIGINT:
				return 64;
			default:
				return 0;
			}
		} else {
			return 0;
		}
	}
}
