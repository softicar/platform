package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.statement.DbStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class DbConnectionStatementExecutor {

	private final DbConnection connection;
	private final Connection jdbcConnection;
	private final DbConnectionStatementCache statementCache;
	private Statement statement;

	public DbConnectionStatementExecutor(DbConnection connection, DbConnectionStatementCache statementCache, Connection jdbcConnection) {

		this.connection = connection;
		this.jdbcConnection = jdbcConnection;
		this.statementCache = statementCache;
		this.statement = null;
	}

	@SuppressWarnings("resource")
	public void execute(DbStatement statement) {

		try {
			connection.notifyStatementStarted(statement);
			statementCache.prepareStatement(statement, false).execute();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		} finally {
			connection.notifyStatementFinished(statement);
		}
	}

	@SuppressWarnings("resource")
	public DbResultSet executeQuery(DbStatement statement) {

		try {
			connection.notifyStatementStarted(statement);
			return new DbResultSet(statementCache.prepareStatement(statement, false).executeQuery());
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		} finally {
			connection.notifyStatementFinished(statement);
		}
	}

	@SuppressWarnings("resource")
	public int executeUpdate(DbStatement statement, boolean generateKeys) {

		try {
			connection.notifyStatementStarted(statement);
			return statementCache.prepareStatement(statement, generateKeys).executeUpdate();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		} finally {
			connection.notifyStatementFinished(statement);
		}
	}

	@SuppressWarnings("resource")
	public void executeUncached(DbStatement statement) {

		try {
			connection.notifyStatementStarted(statement);
			if (statement.getParameters().isEmpty()) {
				getStatement().execute(statement.getText());
			} else {
				try (PreparedStatement preparedStatement = jdbcConnection.prepareStatement(statement.getText())) {
					new InternalStatementParameterSetter(preparedStatement).setParameters(statement.getParameters());
					preparedStatement.execute();
				}
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		} finally {
			connection.notifyStatementFinished(statement);
		}
	}

	private Statement getStatement() throws SQLException {

		if (statement == null) {
			statement = jdbcConnection.createStatement();
		}
		return statement;
	}
}
