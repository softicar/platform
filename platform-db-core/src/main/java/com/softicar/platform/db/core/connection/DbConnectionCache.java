package com.softicar.platform.db.core.connection;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.db.core.database.DbDatabaseKey;
import java.util.HashMap;
import java.util.Map;

/**
 * A cache for {@link IDbConnection}.
 *
 * @author Oliver Richers
 */
public class DbConnectionCache {

	private static final Singleton<DbConnectionCache> INSTANCE = new Singleton<>(DbConnectionCache::new)//
		.setScopeCloseHandler(DbConnectionCache::closeAllConnection);
	private final Map<DbDatabaseKey, IDbConnection> connections;

	/**
	 * Returns the thread local instance of the cache.
	 * <p>
	 * If no instance was created for the current thread yet, this will create a
	 * new instance automatically.
	 *
	 * @return the thread local instance (never null)
	 */
	public static DbConnectionCache getInstance() {

		return INSTANCE.get();
	}

	public DbConnectionCache() {

		this.connections = new HashMap<>();
	}

	/**
	 * Returns the cached connection for the given {@link DbDatabaseKey}.
	 * <p>
	 * Before returning the connection, this method checks its validity by
	 * calling {@link IDbConnection#isValid()}. For example, if the connection
	 * timed out, the connection will be disposed and <i>null</i> will be
	 * returned.
	 *
	 * @param databaseKey
	 *            the database key
	 * @return the cached connection or null
	 */
	@SuppressWarnings("resource")
	public IDbConnection getConnection(DbDatabaseKey databaseKey) {

		IDbConnection connection = connections.get(databaseKey);
		if (connection != null) {
			if (connection.isValid()) {
				return connection;
			} else {
				connection.close();
				connections.remove(databaseKey);
				return null;
			}
		} else {
			return connection;
		}
	}

	/**
	 * Adds the given database connection to this cache.
	 *
	 * @param databaseKey
	 *            the database key
	 * @param connection
	 *            the connection to cache
	 */
	@SuppressWarnings("resource")
	public void addConnection(DbDatabaseKey databaseKey, IDbConnection connection) {

		connections.put(databaseKey, connection);
	}

	/**
	 * Closes all cached connections and clears the cache.
	 */
	public void closeAllConnection() {

		connections.values().stream().forEach(connection -> connection.close());
		connections.clear();
	}
}
