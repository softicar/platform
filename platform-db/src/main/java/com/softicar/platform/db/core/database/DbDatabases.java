package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.DbProperties;
import com.softicar.platform.db.core.connection.DbServerType;

/**
 * Enumerates common databases.
 *
 * @author Oliver Richers
 */
public class DbDatabases {

	/**
	 * This is the default database.
	 * <p>
	 * This is based on the value of {@link DbProperties}.
	 */
	public static final IDbDatabase DEFAULT = new DbDefaultDatabase();

	/**
	 * This is an unshared <i>H2</i> in-memory database.
	 * <p>
	 * Unshared means that each thread sees an isolated instance of the
	 * database.
	 */
	public static final IDbDatabase H2_MEMORY_UNSHARED = new DbDatabaseBuilder()//
		.setServerType(DbServerType.H2_MEMORY)
		.setHostname("")
		.setDatabaseName("")
		.setUsername("")
		.setPassword("")
		.build();
}
