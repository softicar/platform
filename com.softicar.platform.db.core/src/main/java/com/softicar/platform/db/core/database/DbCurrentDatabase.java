package com.softicar.platform.db.core.database;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.db.core.DbProperties;
import com.softicar.platform.db.core.connection.DbConnections;

/**
 * This class holds the thread local for the current {@link IDbDatabase}.
 * <p>
 * This database is used if {@link DbConnections#getConnection()} is called.
 *
 * @author Oliver Richers
 */
public class DbCurrentDatabase {

	private static final Singleton<IDbDatabase> DATABASE = new Singleton<>(() -> DbDatabases.DEFAULT).setInheritByIdentity();

	/**
	 * Specifies the current database used for the current thread.
	 * <p>
	 * This method should usually not be called directly. Instead, call
	 * {@link DbDatabaseScope} using a try-block.
	 *
	 * @param database
	 *            the database to use (may be null to reset to default)
	 */
	public static void set(IDbDatabase database) {

		DATABASE.set(database);
	}

	/**
	 * Returns the current database used for the current thread.
	 * <p>
	 * By default, the database used for the current thread will be
	 * {@link DbDatabases#DEFAULT} which is based on {@link DbProperties}. You
	 * can use {@link DbDatabaseScope} in a try-block to override this.
	 *
	 * @return the default database (never null)
	 */
	public static IDbDatabase get() {

		IDbDatabase database = DATABASE.get();
		if (database == null) {
			database = DbDatabases.DEFAULT;
		}
		return database;
	}
}
