package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.connection.DbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbServerType;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

public class DbDatabaseBuilder {

	private IDbServerType serverType;
	private String hostname;
	private String databaseName;
	private final DbConnectionProperties connectionProperties;

	public DbDatabaseBuilder() {

		this.hostname = "";
		this.databaseName = "";
		this.connectionProperties = new DbConnectionProperties();
	}

	public DbDatabaseBuilder setServerType(IDbServerType serverType) {

		this.serverType = Objects.requireNonNull(serverType);
		return this;
	}

	public DbDatabaseBuilder setHostname(String hostname) {

		this.hostname = Objects.requireNonNull(hostname);
		return this;
	}

	public DbDatabaseBuilder setDatabaseName(String databaseName) {

		this.databaseName = Objects.requireNonNull(databaseName);
		return this;
	}

	public DbDatabaseBuilder setUsername(String username) {

		connectionProperties.setUsername(username);
		return this;
	}

	public DbDatabaseBuilder setPassword(String password) {

		connectionProperties.setPassword(password);
		return this;
	}

	public DbDatabaseBuilder setConnectionPoolEnabled(boolean enabled) {

		connectionProperties.setConnectionPoolEnabled(enabled);
		return this;
	}

	public DbDatabaseBuilder setConnectionProperties(Properties properties) {

		for (Entry<Object, Object> entry: properties.entrySet()) {
			setConnectionProperty((String) entry.getKey(), (String) entry.getValue());
		}
		return this;
	}

	public DbDatabaseBuilder setConnectionProperty(String key, String value) {

		this.connectionProperties.setProperty(key, value);
		return this;
	}

	public DbDatabase build() {

		Objects.requireNonNull(serverType);
		return new DbDatabase(serverType, hostname, databaseName, connectionProperties);
	}
}
