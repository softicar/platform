package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.base.AbstractSqlInsert;
import java.util.List;

public class DbTableInsert<R> extends AbstractSqlInsert<R> {

	public DbTableInsert(ISqlTable<R> table) {

		super(table);
	}

	@Override
	public int execute() {

		return createStatement().executeInsert();
	}

	@Override
	public List<Integer> executeMultiInsert() {

		return createStatement().executeMultiRowInsert();
	}

	@Override
	public void executeWithoutIdGeneration() {

		createStatement().executeUpdate();
	}

	private DbStatement createStatement() {

		return new DbStatement()//
			.addTables(getTables())
			.addText(getSqlText())
			.addParameters(getSqlParameters());
	}
}
