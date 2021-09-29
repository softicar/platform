package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.database.IDbDatabase;

/**
 * Scope for {@link DbConnectionOverride}.
 *
 * @author Oliver Richers
 */
public class DbConnectionOverrideScope implements AutoCloseable {

	private final IDbConnection connection;
	private final boolean closeConnection;
	private final IDbConnection originalOverride;

	/**
	 * Creates a new connection to the default database server.
	 * <p>
	 * The new connection is closed when this scope is closed.
	 */
	public DbConnectionOverrideScope() {

		this(DbCurrentDatabase.get());
	}

	/**
	 * Creates a new connection scope with the given database connection.
	 * <p>
	 * The given connection is <b>not</b> closed when this scope is closed.
	 */
	public DbConnectionOverrideScope(IDbConnection connection) {

		this(connection, false);
	}

	/**
	 * Creates a new connection to the given database server.
	 * <p>
	 * The new connection is closed when this scope is closed.
	 */
	@SuppressWarnings("resource")
	public DbConnectionOverrideScope(IDbDatabase database) {

		this(new DbConnection(database), true);
	}

	private DbConnectionOverrideScope(IDbConnection connection, boolean closeConnection) {

		this.connection = connection;
		this.closeConnection = closeConnection;
		this.originalOverride = DbConnectionOverride.getOverride();

		// set override to use the new database connection
		DbConnectionOverride.setOverride(connection);
	}

	@Override
	public void close() {

		// restore original override
		DbConnectionOverride.setOverride(originalOverride);

		// close override connection if desired
		if (closeConnection) {
			connection.close();
		}
	}
}
