package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.db.core.table.DbTableName;
import java.util.List;

public class DbMysqlParsedInsertStatement {

	private final DbTableName tableName;
	private final List<String> columnNames;
	private final List<List<String>> valueRows;

	public DbMysqlParsedInsertStatement(DbTableName tableName, List<String> columnNames, List<List<String>> valueRows) {

		this.tableName = tableName;
		this.columnNames = columnNames;
		this.valueRows = valueRows;
	}

	public DbTableName getTableName() {

		return tableName;
	}

	public List<String> getColumnNames() {

		return columnNames;
	}

	public List<List<String>> getValueRows() {

		return valueRows;
	}
}
