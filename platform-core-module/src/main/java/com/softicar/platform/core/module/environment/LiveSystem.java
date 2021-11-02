package com.softicar.platform.core.module.environment;

import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbCurrentDatabase;

/**
 * Static utility methods for the live system.
 *
 * @author Oliver Richers
 */
public class LiveSystem {

	/**
	 * Returns true if the currently running system is a proper live system
	 * release.
	 * <p>
	 * This method actually checks if {@link LiveSystemRevision} returns a valid
	 * system revision, which is the case if the system is running on a
	 * productive Tomcat or job server.
	 *
	 * @return whether this is the live system or not
	 */
	public static boolean isLiveSystem() {

		return isValidRevision() && !isH2MemoryDatabase() && isDatabaseServerConfigured();
	}

	/**
	 * Checks whether the current database is a in-memory database.
	 *
	 * @return true if the current database is an H2 in-memory database
	 */
	public static boolean isH2MemoryDatabase() {

		return DbCurrentDatabase.get().getServerType() == DbServerType.H2_MEMORY;
	}

	private static boolean isDatabaseServerConfigured() {

		return DbCurrentDatabase.get().getServerType() != DbServerType.NONE;
	}

	private static boolean isValidRevision() {

		return LiveSystemRevision.getCurrentRevision().getName().isPresent();
	}
}
