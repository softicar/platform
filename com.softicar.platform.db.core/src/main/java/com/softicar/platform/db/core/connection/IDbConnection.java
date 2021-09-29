package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.DbMultiResultSetIterable;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.profiler.IDbConnectionProfiler;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransactionHierarchy;
import java.io.Closeable;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Optional;

/**
 * Represents a JDBC database connection.
 *
 * @author Oliver Richers
 */
public interface IDbConnection extends Closeable {

	// -------------------- statement execution -------------------- //

	/**
	 * Executes the specified {@link DbStatement}.
	 *
	 * @param statement
	 *            the statement to execute (never null)
	 */
	void execute(DbStatement statement);

	/**
	 * Executes the specified {@link DbStatement} without using the prepared
	 * statement cache.
	 *
	 * @param statement
	 *            the statement to execute (never null)
	 */
	void executeUncached(DbStatement statement);

	/**
	 * Executes the specified query statement and returns the result set.
	 * <p>
	 * The caller is obliged to close the returned object.
	 *
	 * @param statement
	 *            the query statement to execute (never null)
	 * @return the result set of the query
	 */
	DbResultSet executeQuery(DbStatement statement);

	/**
	 * Executes the specified multi-query statement and returns the result sets.
	 * <p>
	 * A multi-query is simply a concatenated list of normal queries, separated
	 * by semicolons. The query parameters are also simply concatenated.
	 * <p>
	 * The caller is obliged to close the returned object.
	 *
	 * @param statement
	 *            the multi-query statement to execute (never null)
	 * @return the {@link DbMultiResultSetIterable} of the queries
	 */
	DbMultiResultSetIterable executeMultiQuery(DbStatement statement);

	/**
	 * Executes the specified UPDATE, INSERT or DELETE statement.
	 *
	 * @param statement
	 *            the statement to execute (never null)
	 * @return the number of affected table rows
	 */
	int executeUpdate(DbStatement statement);

	/**
	 * Executes the specified INSERT statement.
	 * <p>
	 * This assumes that the table's primary key is an auto increment key.
	 *
	 * @param statement
	 *            the statement to execute (never null)
	 * @return the generated auto-incremented id
	 */
	int executeInsert(DbStatement statement);

	/**
	 * Executes the specified multi-row INSERT statement.
	 *
	 * @param statement
	 *            the statement to execute (never null)
	 * @return the generated IDs
	 */
	List<Integer> executeMultiRowInsert(DbStatement statement);

	/**
	 * Queries the next result set for the previously executed query.
	 * <p>
	 * The caller is obliged to close the returned object.
	 *
	 * @return the next result set or null if no more result sets are available
	 */
	DbResultSet nextResultSet();

	// -------------------- transactions -------------------- //

	Optional<IDbTransaction> getCurrentTransaction();

	Optional<IDbTransaction> getRootTransaction();

	IDbTransactionHierarchy getTransactionHierarchy();

	// -------------------- status -------------------- //

	/**
	 * Closes this connection.
	 * <p>
	 * Note that we need to redeclare this method here, because we do not throw
	 * any checked exceptions.
	 */
	@Override
	void close();

	/**
	 * Returns whether this database connection was closed.
	 *
	 * @return <i>true</i> if this connection was closed; <i>false</i> otherwise
	 */
	boolean isClosed();

	/**
	 * Checks whether this database connection is still valid.
	 * <p>
	 * The connection is valid if it can be used to execute database statements.
	 * <p>
	 * The validity is checked by looking at the time since the last successful
	 * execution of a database statement. If this time is above a configured
	 * threshold, a simple test statement will be executed to validate the
	 * connection.
	 * <p>
	 * Closing a connection will make it invalid.
	 *
	 * @return <i>true</i> if this connection is valid; <i>false</i> otherwise
	 */
	boolean isValid();

	// -------------------- miscellaneous -------------------- //

	/**
	 * Returns the {@link IDbDatabase} that this connection is connected to.
	 *
	 * @return the connected database (never null)
	 */
	IDbDatabase getDatabase();

	/**
	 * Returns the connected database server type.
	 *
	 * @return the server type (never null)
	 */
	IDbServerType getServerType();

	/**
	 * Returns the {@link IDbServerQuirks} of the connected database server.
	 *
	 * @return the {@link IDbServerQuirks} (never null)
	 */
	IDbServerQuirks getServerQuirks();

	/**
	 * Queries and returns the JDBC {@link DatabaseMetaData}.
	 *
	 * @return the {@link DatabaseMetaData} (never null)
	 */
	DatabaseMetaData getMetaData();

	/**
	 * Sets the {@link IDbConnectionProfiler} to use.
	 * <p>
	 * To reset to the default profile, call this method with <i>null</i>
	 * parameter.
	 *
	 * @param profiler
	 *            the profiler instance or null
	 */
	void setProfiler(IDbConnectionProfiler profiler);
}
