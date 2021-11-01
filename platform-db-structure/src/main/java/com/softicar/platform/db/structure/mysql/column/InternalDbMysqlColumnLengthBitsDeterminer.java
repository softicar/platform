package com.softicar.platform.db.structure.mysql.column;

class InternalDbMysqlColumnLengthBitsDeterminer {

	private final InternalDbMysqlColumnType columnType;

	public InternalDbMysqlColumnLengthBitsDeterminer(InternalDbMysqlColumnType columnType) {

		this.columnType = columnType;
	}

	public int determineLengthBits() {

		switch (columnType) {
		case TINYBLOB:
		case TINYTEXT:
			return 8;
		case BLOB:
		case TEXT:
			return 16;
		case MEDIUMBLOB:
		case MEDIUMTEXT:
			return 24;
		case LONGBLOB:
		case LONGTEXT:
			return 32;
		default:
			return 0;
		}
	}
}
