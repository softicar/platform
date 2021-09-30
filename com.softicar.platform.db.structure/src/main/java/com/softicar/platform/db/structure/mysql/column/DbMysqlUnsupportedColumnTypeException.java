package com.softicar.platform.db.structure.mysql.column;

import com.softicar.platform.db.core.table.DbTableName;

public class DbMysqlUnsupportedColumnTypeException extends RuntimeException {

	private final DbTableName tableName;
	private final String columnName;
	private final String columnType;

	public DbMysqlUnsupportedColumnTypeException(DbTableName tableName, String columnName, String columnType) {

		super(String.format("Column type '%s' not supported in table %s, column '%s'.", columnType, tableName, columnName));

		this.tableName = tableName;
		this.columnName = columnName;
		this.columnType = columnType;
	}

	public DbTableName getTableName() {

		return tableName;
	}

	public String getColumnName() {

		return columnName;
	}

	public String getColumnType() {

		return columnType;
	}
}
