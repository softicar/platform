package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.DbProperties;
import com.softicar.platform.db.core.connection.DbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbServerType;

public class DbDefaultDatabase implements IDbDatabase {

	@Override
	public IDbServerType getServerType() {

		return DbProperties.SERVER_TYPE.getValue();
	}

	@Override
	public String getHostname() {

		return DbProperties.SERVER_ADDRESS.getValue();
	}

	@Override
	public String getDatabaseName() {

		return DbProperties.DATABASE.getValue();
	}

	@Override
	public IDbConnectionProperties getConnectionProperties() {

		DbConnectionProperties connectionProperties = new DbConnectionProperties();
		connectionProperties.setUsername(DbProperties.USERNAME.getValue());
		connectionProperties.setPassword(DbProperties.PASSWORD.getValue());
		connectionProperties.setConnectionPoolEnabled(DbProperties.CONNECTION_POOL_ENABLED.getValue());
		connectionProperties.setConnectionPoolMinimum(DbProperties.CONNECTION_POOL_MINIMUM.getValue());
		connectionProperties.setConnectionPoolMaximum(DbProperties.CONNECTION_POOL_MAXIMUM.getValue());
		connectionProperties.setConnectionPoolIdleTimeout(DbProperties.CONNECTION_POOL_IDLE_TIMEOUT.getValue());
		return connectionProperties;
	}
}
