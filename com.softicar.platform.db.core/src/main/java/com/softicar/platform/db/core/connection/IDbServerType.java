package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.connection.connector.IDbConnector;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;

public interface IDbServerType {

	/**
	 * Returns the {@link IDbConnector} instance use to connect to this server
	 * type.
	 *
	 * @return the connector (never null)
	 */
	IDbConnector getConnector();

	/**
	 * Returns the {@link IDbServerQuirks} of this server type.
	 *
	 * @return the server quirks (never null)
	 */
	IDbServerQuirks getQuirks();
}
