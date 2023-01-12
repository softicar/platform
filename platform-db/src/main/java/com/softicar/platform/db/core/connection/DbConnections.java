package com.softicar.platform.db.core.connection;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.profiler.IDbConnectionProfiler;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.database.DbDatabaseKey;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import java.sql.DatabaseMetaData;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility methods for database connections.
 */
public class DbConnections {

	/**
	 * Returns a connection to the {@link DbCurrentDatabase} or
	 * {@link DbConnectionOverride} if defined.
	 * <p>
	 * Subsequent requests will return the same connection object, assuming
	 * {@link DbCurrentDatabase} and {@link DbConnectionOverride} did not
	 * change.
	 *
	 * @return a connection to the current database or the connection override
	 *         (never null)
	 */
	@SuppressWarnings("resource")
	public static IDbConnection getConnection() {

		IDbConnection override = DbConnectionOverride.getOverride();
		return override != null? override : getConnection(DbCurrentDatabase.get());
	}

	/**
	 * Returns a connection to the given database.
	 * <p>
	 * Subsequent requests with the same database will return the same
	 * connection for the current thread.
	 *
	 * @param database
	 *            the {@link IDbDatabase} definition to use
	 * @return connection to the given database (never null)
	 */
	public static IDbConnection getConnection(IDbDatabase database) {

		DbDatabaseKey databaseKey = new DbDatabaseKey(database);
		IDbConnection connection = DbConnectionCache.getInstance().getConnection(databaseKey);
		if (connection == null) {
			// establish a new connection
			Log
				.fverbose(//
					"Thread %s: Connecting %s database %s with %s@%s.",
					Thread.currentThread().getId(),
					database.getServerType(),
					database.getDatabaseName(),
					database.getConnectionProperties().getUsername(),
					database.getHostname());
			connection = new DbConnection(database);
			DbConnectionCache.getInstance().addConnection(databaseKey, connection);
		}
		return connection;
	}

	/**
	 * Closes all connections in the {@link DbConnectionCache}.
	 */
	public static void closeAll() {

		DbConnectionCache.getInstance().closeAllConnection();
	}

	/**
	 * Returns the {@link IDbServerQuirks} for the current database connection.
	 * <p>
	 * TODO: respect {@link DbConnectionOverride}
	 *
	 * @return the current server quirks (never null)
	 */
	public static IDbServerQuirks getServerQuirks() {

		// It is important that we do not use getConnection() here,
		// so that no unnecessary connection to the database server is created.
		return DbCurrentDatabase.get().getServerType().getQuirks();
	}

	// ------------------------------ auxiliary methods ------------------------------ //

	/**
	 * Applies the given {@link Function} on the {@link IDbConnection} returned
	 * by {@link #getCurrentTransaction()}.
	 *
	 * @param function
	 *            the {@link Function} to apply (never <i>null</i>)
	 * @return the result of the executed {@link Function#apply}
	 */
	@SuppressWarnings("resource")
	public static <R> R apply(Function<IDbConnection, R> function) {

		return function.apply(getConnection());
	}

	/**
	 * Executes the given {@link Consumer} on the {@link IDbConnection} returned
	 * by {@link #getCurrentTransaction()}.
	 *
	 * @param consumer
	 *            the {@link Consumer} to execute (never <i>null</i>)
	 */
	@SuppressWarnings("resource")
	public static void accept(Consumer<IDbConnection> consumer) {

		consumer.accept(getConnection());
	}

	public static DatabaseMetaData getMetaData() {

		return apply(IDbConnection::getMetaData);
	}

	public static DbResultSet nextResultSet() {

		return apply(IDbConnection::nextResultSet);
	}

	public static boolean isServerType(IDbServerType serverType) {

		return apply(connection -> connection.getServerType() == serverType);
	}

	public static void setProfiler(IDbConnectionProfiler profiler) {

		accept(connection -> connection.setProfiler(profiler));
	}

	// ------------------------------ transaction methods ------------------------------ //

	public static boolean isTransactionStarted() {

		return apply(connection -> connection.getRootTransaction().isPresent());
	}

	public static Optional<IDbTransaction> getCurrentTransaction() {

		return apply(IDbConnection::getCurrentTransaction);
	}

	public static <T> Optional<T> getOrPutTransactionData(Class<T> dataClass, Supplier<T> dataFactory) {

		return getCurrentTransaction()//
			.map(transaction -> transaction.getOrPutData(dataClass, dataFactory));
	}

	public static <T> Optional<T> getTransactionData(Class<T> dataClass) {

		return getCurrentTransaction()//
			.flatMap(transaction -> transaction.getData(dataClass));
	}

	public static <T> Optional<T> getTransactionDataIncludingParents(Class<T> dataClass) {

		return getCurrentTransaction()//
			.flatMap(transaction -> transaction.getDataIncludingParents(dataClass));
	}

	/**
	 * Returns the point in time at which the root {@link IDbTransaction} was
	 * started.
	 * <p>
	 * If there is no root transaction, the current point in time is returned.
	 *
	 * @return the transaction start time or the current time (never
	 *         <i>null</i>)
	 */
	@SuppressWarnings("resource")
	public static DayTime getBeginOfRootTransaction() {

		return getConnection()//
			.getRootTransaction()
			.map(IDbTransaction::getBegin)
			.orElse(DayTime.now());
	}
}
