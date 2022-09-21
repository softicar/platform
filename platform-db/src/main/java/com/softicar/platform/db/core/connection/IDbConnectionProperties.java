package com.softicar.platform.db.core.connection;

import java.util.Properties;

public interface IDbConnectionProperties {

	String getUsername();

	String getPassword();

	void copyPropertiesTo(Properties properties);

	boolean isConnectionPoolEnabled();

	Integer getConnectionPoolMinimum();

	Integer getConnectionPoolMaximum();

	Long getConnectionPoolIdleTimeout();
}
