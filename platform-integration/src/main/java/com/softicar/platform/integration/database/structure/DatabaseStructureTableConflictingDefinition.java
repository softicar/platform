package com.softicar.platform.integration.database.structure;

public class DatabaseStructureTableConflictingDefinition {

	private final String tableName;
	private final String firstCreateStatement;
	private final String secondCreateStatement;

	public DatabaseStructureTableConflictingDefinition(String tableName, String firstCreateStatement, String secondCreateStatement) {

		this.tableName = tableName;
		this.firstCreateStatement = firstCreateStatement;
		this.secondCreateStatement = secondCreateStatement;
	}

	public String getTableName() {

		return tableName;
	}

	public String getFirstCreateStatement() {

		return firstCreateStatement;
	}

	public String getSecondCreateStatement() {

		return secondCreateStatement;
	}
}
