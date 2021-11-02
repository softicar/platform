package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.connection.IDbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbServerType;

/**
 * Default implementation of {@link IDbDatabase}.
 *
 * @author Oliver Richers
 */
public class DbDatabase implements IDbDatabase {

	private final IDbServerType serverType;
	private final String hostname;
	private final String databaseName;
	private final IDbConnectionProperties connectionProperties;

	public DbDatabase(IDbServerType serverType, String hostname, String databaseName, IDbConnectionProperties connectionProperties) {

		this.serverType = serverType;
		this.hostname = hostname;
		this.databaseName = databaseName;
		this.connectionProperties = connectionProperties;
	}

	public DbDatabase(IDbDatabase database, IDbConnectionProperties connectionProperties) {

		this(database.getServerType(), database.getHostname(), database.getDatabaseName(), connectionProperties);
	}

	@Override
	public IDbServerType getServerType() {

		return serverType;
	}

	@Override
	public String getHostname() {

		return hostname;
	}

	@Override
	public String getDatabaseName() {

		return databaseName;
	}

	@Override
	public IDbConnectionProperties getConnectionProperties() {

		return connectionProperties;
	}
}
