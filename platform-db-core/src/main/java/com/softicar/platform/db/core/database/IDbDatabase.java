package com.softicar.platform.db.core.database;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.IDbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbServerType;
import com.softicar.platform.db.core.statement.IDbStatementExecutionListener;
import java.util.function.Supplier;

/**
 * Defines the database server and the name of the database.
 *
 * @author Oliver Richers
 */
public interface IDbDatabase extends IDbStatementExecutionListener {

	/**
	 * Returns the type of database management system.
	 *
	 * @return the DBMS system type (never null)
	 */
	IDbServerType getServerType();

	/**
	 * Returns the name of the host containing this database.
	 *
	 * @return the hostname (never null)
	 */
	String getHostname();

	/**
	 * Returns the name of the database.
	 *
	 * @return the database name (never null)
	 */
	String getDatabaseName();

	/**
	 * Returns additional connection properties for this database, e.g. username
	 * and password.
	 *
	 * @return the connection properties (never null)
	 */
	IDbConnectionProperties getConnectionProperties();

	/**
	 * Executes the given {@link INullaryVoidFunction} in the
	 * {@link DbDatabaseScope} of this {@link IDbDatabase}.
	 *
	 * @param function
	 *            the {@link INullaryVoidFunction} to execute (never
	 *            <i>null</i>)
	 */
	default void apply(INullaryVoidFunction function) {

		try (var databaseScope = new DbDatabaseScope(this)) {
			try (var connectionScope = new DbConnectionOverrideScope(this)) {
				function.apply();
			}
		}
	}

	/**
	 * Executes the given {@link Supplier} in the {@link DbDatabaseScope} of
	 * this {@link IDbDatabase}.
	 *
	 * @param supplier
	 *            the {@link Supplier} to execute (never <i>null</i>)
	 * @return the return value of the {@link Supplier}
	 */
	default <T> T apply(Supplier<T> supplier) {

		try (var databaseScope = new DbDatabaseScope(this)) {
			try (var connectionScope = new DbConnectionOverrideScope(this)) {
				return supplier.get();
			}
		}
	}
}
