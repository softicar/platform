package com.softicar.platform.db.core.connection.pool;

import com.softicar.platform.db.core.database.DbDatabaseKey;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

/**
 * A global {@link Map} of current connection pools.
 *
 * @author Oliver Richers
 */
public class DbConnectionPoolMap {

	private static final DbConnectionPoolMap SINGLETON = new DbConnectionPoolMap();
	private final Map<DbDatabaseKey, DataSource> connectionPools = new ConcurrentHashMap<>();

	public static DbConnectionPoolMap getSingleton() {

		return SINGLETON;
	}

	public DataSource getConnectionPool(IDbDatabase database) {

		DbDatabaseKey databaseKey = new DbDatabaseKey(database);
		DataSource connectionPool = connectionPools.get(databaseKey);
		if (connectionPool != null) {
			return connectionPool;
		} else {
			return getConnectionPoolSynchronized(databaseKey, database);
		}
	}

	private synchronized DataSource getConnectionPoolSynchronized(DbDatabaseKey databaseKey, IDbDatabase database) {

		// Instead of synchronizing during pool creation, we could first create the
		// pool and only then check if the creation was necessary. Unfortunately,
		// this can lead to many unnecessary connection pool creations in specific
		// situations and should be avoided.
		DataSource connectionPool = connectionPools.get(databaseKey);
		if (connectionPool == null) {
			connectionPool = database.getServerType().getConnector().createConnectionPool(database);
			connectionPools.put(databaseKey, connectionPool);
		}
		return connectionPool;
	}

	/**
	 * Tries to close all connection pools and clears this map.
	 * <p>
	 * All connection pools that implement {@link AutoCloseable} will be closed.
	 */
	public void closeAllAndClear() {

		for (DataSource dataSource: connectionPools.values()) {
			if (dataSource instanceof AutoCloseable) {
				try {
					((AutoCloseable) dataSource).close();
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		}

		connectionPools.clear();
	}
}
