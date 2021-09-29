package com.softicar.platform.db.core.connection;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * An override for the connection returned by
 * {@link DbConnections#getConnection()}.
 * <p>
 * This class should not be used directly but rather by using the class
 * {@link DbConnectionOverrideScope} to ensure that the override is reverted in
 * case of an exception.
 *
 * @author Oliver Richers
 */
public class DbConnectionOverride {

	private static final Singleton<IDbConnection> OVERRIDE = new Singleton<>();

	public static IDbConnection getOverride() {

		return OVERRIDE.get();
	}

	/**
	 * Sets the new override connection.
	 * <p>
	 * This method is protected because it shall only be called by
	 * {@link DbConnectionOverrideScope}.
	 *
	 * @param connection
	 *            the override connection
	 */
	protected static void setOverride(IDbConnection connection) {

		OVERRIDE.set(connection);
	}
}
