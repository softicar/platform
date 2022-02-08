package com.softicar.platform.db.core.connection;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.DbMultiResultSetIterable;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.connection.pool.DbConnectionPoolMap;
import com.softicar.platform.db.core.connection.profiler.DbDummyConnectionProfiler;
import com.softicar.platform.db.core.connection.profiler.IDbConnectionProfiler;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.connection.tracker.DbConnectionTracker;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.transaction.DbTransactionHierarchy;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * This class represents a database connection.
 * <p>
 * In Java, unclosed statement objects will not be garbage collected, so you
 * either have to close the statement after executing it or try to reuse old
 * statements. There is a cache of prepared statements in this class, so that
 * recurring statements will reuse the previously created statement object.
 * <p>
 * You should use the place holders (?) of the prepared statements. There are
 * two reasons for this: First, escaping of parameters will be done by the
 * MySQL-connector, so SQL-injection is no problem. Second, the prepared
 * statements can be reused which will improve performance.
 *
 * @author Oliver Richers
 */
public class DbConnection implements IDbConnection {

	// check every 5 minutes for validity
	private static final long VALIDITY_CHECK_INTERVAL = 5 * 60 * 1000;

	private final IDbDatabase database;
	private final DbTransactionHierarchy transactionHierarchy = new DbTransactionHierarchy();
	private Connection connection;
	private final int connectionIndex;
	private final DbConnectionStatementCache statementCache;
	private final DbConnectionStatementExecutor statementExecutor;
	private long lastTimeValid;
	private IDbConnectionProfiler profiler;

	/**
	 * Creates a new connection to the specified database.
	 *
	 * @param database
	 *            the database to connection to; the connection properties
	 *            should at least contain the <i>user</i> and <i>password</i>
	 *            properties
	 */
	public DbConnection(IDbDatabase database) {

		this.database = database;
		this.connection = createConnection(database);
		this.connectionIndex = DbConnectionTracker.getSingleton().notifyNewConnection();
		this.statementCache = new DbConnectionStatementCache(connection);
		this.statementExecutor = new DbConnectionStatementExecutor(this, statementCache, connection);
		this.lastTimeValid = System.currentTimeMillis();
		this.profiler = getDefaultProfiler();
		DbConnectionCounter.increment();
	}

	private static Connection createConnection(IDbDatabase database) {

		if (database.getConnectionProperties().isConnectionPoolEnabled()) {
			return createConnectionWithPool(database);
		} else {
			return createConnectionWithDriver(database);
		}
	}

	private static Connection createConnectionWithPool(IDbDatabase database) {

		try {
			return DbConnectionPoolMap.getSingleton().getConnectionPool(database).getConnection();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	private static Connection createConnectionWithDriver(IDbDatabase database) {

		return database.getServerType().getConnector().connect(database);
	}

	/**
	 * Creates a new connection to the specified database server.
	 *
	 * @param serverType
	 *            the database server type
	 * @param hostname
	 *            the server hostname
	 * @param databaseName
	 *            the default database or schema name
	 * @param username
	 *            the user name to use for login
	 * @param password
	 *            the password to use for login
	 */
	public DbConnection(IDbServerType serverType, String hostname, String databaseName, String username, String password) {

		this(
			new DbDatabaseBuilder()//
				.setServerType(serverType)
				.setHostname(hostname)
				.setDatabaseName(databaseName)
				.setUsername(username)
				.setPassword(password)
				.build());
	}

	/**
	 * Creates a new connection to the specified database server.
	 *
	 * @param serverType
	 *            the database server type
	 * @param hostname
	 *            the server hostname
	 * @param databaseName
	 *            the default database or schema name
	 * @param properties
	 *            the connection properties (at least <i>user</i> and
	 *            <i>password</i> must be specified)
	 */
	public DbConnection(IDbServerType serverType, String hostname, String databaseName, Properties properties) {

		this(
			new DbDatabaseBuilder()//
				.setServerType(serverType)
				.setHostname(hostname)
				.setDatabaseName(databaseName)
				.setConnectionProperties(properties)
				.build());
	}

	// -------------------- execute -------------------- //

	@Override
	public void execute(DbStatement statement) {

		statementExecutor.execute(statement);
	}

	// -------------------- execute un-cached -------------------- //

	@Override
	public void executeUncached(DbStatement statement) {

		statementExecutor.executeUncached(statement);
	}

	// -------------------- execute query -------------------- //

	@Override
	public DbResultSet executeQuery(DbStatement statement) {

		return statementExecutor.executeQuery(statement);
	}

	@Override
	@SuppressWarnings("resource")
	public DbMultiResultSetIterable executeMultiQuery(DbStatement statement) {

		return new DbMultiResultSetIterable(executeQuery(statement));
	}

	@Override
	@SuppressWarnings("resource")
	public DbResultSet nextResultSet() {

		try {
			if (statementCache.getCurrentPreparedStatement().getMoreResults()) {
				return new DbResultSet(statementCache.getCurrentPreparedStatement().getResultSet());
			} else {
				return null;
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	// -------------------- execute update -------------------- //

	@Override
	public int executeUpdate(DbStatement statement) {

		return executeUpdate(statement, false);
	}

	private int executeUpdate(DbStatement statement, boolean generateKeys) {

		return statementExecutor.executeUpdate(statement, generateKeys);
	}

	// -------------------- execute insert -------------------- //

	@Override
	public int executeInsert(DbStatement statement) {

		if (executeUpdate(statement, true) == 1) {
			return getInsertId();
		} else {
			throw new SofticarException("Insert statement did not modify exactly one line.");
		}
	}

	@Override
	public List<Integer> executeMultiRowInsert(DbStatement statement) {

		executeUpdate(statement, true);
		return getGeneratedKeys();
	}

	@SuppressWarnings("resource")
	private List<Integer> getGeneratedKeys() {

		try (ResultSet resultSet = statementCache.getCurrentPreparedStatement().getGeneratedKeys()) {
			List<Integer> ids = new ArrayList<>();
			if (resultSet != null && hasIntegerFirstColumn(resultSet)) {
				// read all generated keys
				while (resultSet.next()) {
					ids.add(resultSet.getInt(1));
				}

				// ensure that all open result sets are closed
				if (connection.getMetaData().supportsMultipleOpenResults()) {
					statementCache.getCurrentPreparedStatement().getMoreResults(Statement.CLOSE_ALL_RESULTS);
				}
			}
			return ids;
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	private boolean hasIntegerFirstColumn(ResultSet resultSet) throws SQLException {

		var metaData = resultSet.getMetaData();
		if (metaData.getColumnCount() > 0) {
			switch (metaData.getColumnType(1)) {
			case Types.BIGINT:
			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.TINYINT:
				return true;
			}
		}
		return false;
	}

	private int getInsertId() {

		List<Integer> ids = getGeneratedKeys();
		if (ids.size() >= 1) {
			return ids.get(0);
		} else {
			throw new SofticarException("Failed to get insert id. You can call this method only once per INSERT.");
		}
	}

	// -------------------- transactions -------------------- //

	@Override
	public Optional<IDbTransaction> getCurrentTransaction() {

		return transactionHierarchy.getCurrentTransaction();
	}

	@Override
	public Optional<IDbTransaction> getRootTransaction() {

		return transactionHierarchy.getRootTransaction();
	}

	@Override
	public DbTransactionHierarchy getTransactionHierarchy() {

		return transactionHierarchy;
	}

	// -------------------- status -------------------- //

	@Override
	public void close() {

		if (connection != null) {
			try {
				connection.close();
				profiler.afterConnectionClosed();
				DbConnectionCounter.decrement();
				DbConnectionTracker.getSingleton().notifyConnectionClosed(connectionIndex);
			} catch (SQLException exception) {
				Log.error("Failed to close sql connection.");
				Log.error(exception.toString());
				exception.printStackTrace();
			}

			connection = null;
		}
	}

	@Override
	public boolean isClosed() {

		return connection == null;
	}

	@Override
	public boolean isValid() {

		if (isClosed()) {
			return false;
		}

		if (System.currentTimeMillis() < lastTimeValid + VALIDITY_CHECK_INTERVAL) {
			return true;
		}

		try {
			// NOTE: not using prepared statement because it would
			// overwrite the currently prepared statement
			Log.fverbose("Checking validity of database connection.");
			try (Statement statement = connection.createStatement()) {
				statement.executeQuery(" SELECT 1 ").close();
			}
			this.lastTimeValid = System.currentTimeMillis();
			return true;
		} catch (Exception exception) {
			Log.fwarning("Connection became invalid.");
			Log.fwarning(exception.toString());
			exception.printStackTrace();
			return false;
		}
	}

	// -------------------- miscellaneous -------------------- //

	@Override
	public IDbDatabase getDatabase() {

		return database;
	}

	@Override
	public IDbServerType getServerType() {

		return getDatabase().getServerType();
	}

	@Override
	public IDbServerQuirks getServerQuirks() {

		return getServerType().getQuirks();
	}

	@Override
	public DatabaseMetaData getMetaData() {

		try {
			return connection.getMetaData();
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}

	@Override
	public void setProfiler(IDbConnectionProfiler profiler) {

		this.profiler = profiler != null? profiler : getDefaultProfiler();
	}

	// -------------------- protected -------------------- //

	protected void notifyStatementStarted(DbStatement statement) {

		// start lazy transactions
		transactionHierarchy.startLazyTransactions();

		// notify database
		database.beforeExecution(statement);

		// notify profiler
		Log.fverbose("%s [%s]", statement.getText(), Imploder.implode(statement.getParameters(), ","));
		profiler.acceptStatementStarted(statement);

		// notify connection tracker
		DbConnectionTracker.getSingleton().notifyConnectionUsed(connectionIndex);
	}

	protected void notifyStatementFinished(DbStatement statement) {

		// notify profiler
		profiler.acceptStatementFinished(statement);

		// notify database
		database.afterExecution(statement);
	}

	// -------------------- private -------------------- //

	private static DbDummyConnectionProfiler getDefaultProfiler() {

		return new DbDummyConnectionProfiler();
	}
}
