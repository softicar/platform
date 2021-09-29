package com.softicar.platform.db.core.connection.profiler;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.statement.IDbStatement;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DbConnectionProfiler implements IDbConnectionProfiler {

	private final Map<String, Long> millisecondsPerStatement;
	private final Map<String, Integer> executionCountPerStatement;
	private IDbStatement currentStatement;
	private long currentStatementStart;

	public DbConnectionProfiler() {

		this.millisecondsPerStatement = new TreeMap<>();
		this.executionCountPerStatement = new TreeMap<>();
		this.currentStatement = null;
	}

	@Override
	public void acceptStatementStarted(IDbStatement statement) {

		this.currentStatement = statement;
		this.currentStatementStart = System.currentTimeMillis();
	}

	@Override
	public void acceptStatementFinished(IDbStatement statement) {

		if (currentStatement != null) {
			long duration = System.currentTimeMillis() - currentStatementStart;
			millisecondsPerStatement.merge(currentStatement.getText(), duration, (a, b) -> a + b);
			executionCountPerStatement.merge(currentStatement.getText(), 1, (a, b) -> a + b);
			this.currentStatement = null;
		}
	}

	public Collection<String> getStatements() {

		return millisecondsPerStatement.keySet();
	}

	public long getTotalMilliseconds(String statement) {

		return millisecondsPerStatement.getOrDefault(statement, 0L);
	}

	public int getExecutionCount(String statement) {

		return executionCountPerStatement.getOrDefault(statement, 0);
	}

	public List<String> getStatementsWithHighestLoad(int statementCount) {

		return millisecondsPerStatement//
			.keySet()
			.stream()
			.sorted(
				Comparator//
					.comparing((String statement) -> -getTotalMilliseconds(statement))
					.thenComparing(statement -> -getExecutionCount(statement))
					.thenComparing(text -> text))
			.limit(statementCount)
			.collect(Collectors.toList());
	}

	public void printStatementsWithHighestLoad(int statementCount) {

		for (String statement: getStatementsWithHighestLoad(statementCount)) {
			Log.finfo("[%6sms, %4sx] %s", getTotalMilliseconds(statement), getExecutionCount(statement), statement);
		}
	}
}
