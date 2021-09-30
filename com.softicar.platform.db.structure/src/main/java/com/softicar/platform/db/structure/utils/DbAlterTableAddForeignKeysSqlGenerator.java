package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.List;
import java.util.stream.Collectors;

public class DbAlterTableAddForeignKeysSqlGenerator {

	private final IDbTableStructure tableStructure;
	private boolean isH2Database;

	public DbAlterTableAddForeignKeysSqlGenerator(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
		this.isH2Database = false;
	}

	public DbAlterTableAddForeignKeysSqlGenerator setIsH2Database(boolean isH2Database) {

		this.isH2Database = isH2Database;
		return this;
	}

	public String getAlterTableStatement() {

		StringBuilder statementBuilder = new StringBuilder();
		if (isH2Database) {
			getCreateForeignKeyRows()//
				.stream()
				.forEach(
					row -> statementBuilder//
						.append(getAlterTableStatementHeader())
						.append(row)
						.append(";\n"));
		} else {
			statementBuilder//
				.append(getAlterTableStatementHeader())
				.append(Imploder.implode(getCreateForeignKeyRows(), ",\n"));
		}
		return statementBuilder.toString();
	}

	private List<String> getCreateForeignKeyRows() {

		return tableStructure//
			.getForeignKeys()
			.stream()
			.map(DbForeignKeyStructureSqlGenerator::new)
			.map(converter -> converter.setIsAlterTableMode(true))
			.map(DbForeignKeyStructureSqlGenerator::toString)
			.collect(Collectors.toList());
	}

	private String getAlterTableStatementHeader() {

		return new StringBuilder()//
			.append("ALTER TABLE ")
			.append(tableStructure.getTableName().getQuoted())
			.append(" ")
			.toString();
	}
}
