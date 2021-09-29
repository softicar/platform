package com.softicar.platform.db.core.connection;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.statement.DbStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class DbConnectionStatementCache {

	private static final int WARN_STATEMENT_COUNT = 512;
	private final Connection connection;
	private final Map<String, PreparedStatement> preparedStatements;
	private PreparedStatement currentPreparedStatement;
	private int logCacheSize = 16;

	public DbConnectionStatementCache(Connection connection) {

		this.connection = connection;
		this.preparedStatements = new TreeMap<>();
		this.currentPreparedStatement = null;
		this.logCacheSize = 16;
	}

	public PreparedStatement getCurrentPreparedStatement() {

		return currentPreparedStatement;
	}

	public PreparedStatement prepareStatement(DbStatement statement, boolean generateKeys) {

		prepareStatement(statement.getText(), generateKeys);
		new InternalStatementParameterSetter(currentPreparedStatement).setParameters(statement.getParameters());
		return currentPreparedStatement;
	}

	@SuppressWarnings("resource")
	private void prepareStatement(String statement, boolean generateKeys) {

		try {
			currentPreparedStatement = preparedStatements.get(statement);
			if (currentPreparedStatement == null) {
				if (generateKeys) {
					currentPreparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
				} else {
					currentPreparedStatement = connection.prepareStatement(statement);
				}
				preparedStatements.put(statement, currentPreparedStatement);

				// log size
				if (preparedStatements.size() == logCacheSize) {
					Log
						.flog(
							preparedStatements.size() >= WARN_STATEMENT_COUNT? Log.WARNING_LEVEL : Log.VERBOSE_LEVEL,
							"Now, %d prepared statements cached.",
							preparedStatements.size());
					logCacheSize *= 2;
				}
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}
}
