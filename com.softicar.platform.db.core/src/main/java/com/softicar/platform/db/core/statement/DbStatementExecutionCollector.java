package com.softicar.platform.db.core.statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects all {@link IDbStatement} that have been executed.
 *
 * @author Oliver Richers
 */
public class DbStatementExecutionCollector implements IDbStatementExecutionListener {

	private final List<IDbStatement> statements;

	public DbStatementExecutionCollector() {

		this.statements = new ArrayList<>();
	}

	@Override
	public void beforeExecution(IDbStatement statement) {

		statements.add(statement);
	}

	public List<IDbStatement> getStatements() {

		return statements;
	}
}
