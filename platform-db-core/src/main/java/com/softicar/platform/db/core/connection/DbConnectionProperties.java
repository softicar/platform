package com.softicar.platform.db.core.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class DbConnectionProperties implements IDbConnectionProperties {

	private String username;
	private String password;
	private final Map<String, String> extraProperties;
	private boolean connectionPoolEnabled;
	private Integer connectionPoolMinimum;
	private Integer connectionPoolMaximum;
	private Long connectionPoolIdleTimeout;

	public DbConnectionProperties() {

		this.username = "";
		this.password = "";
		this.extraProperties = new HashMap<>();
		this.connectionPoolEnabled = false;
		this.connectionPoolMinimum = null;
		this.connectionPoolMaximum = null;
		this.connectionPoolIdleTimeout = null;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public void copyPropertiesTo(Properties properties) {

		properties.setProperty("user", username);
		properties.setProperty("password", password);
		for (Entry<String, String> entry: extraProperties.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean isConnectionPoolEnabled() {

		return connectionPoolEnabled;
	}

	@Override
	public Integer getConnectionPoolMinimum() {

		return connectionPoolMinimum;
	}

	@Override
	public Integer getConnectionPoolMaximum() {

		return connectionPoolMaximum;
	}

	@Override
	public Long getConnectionPoolIdleTimeout() {

		return connectionPoolIdleTimeout;
	}

	public DbConnectionProperties setUsername(String username) {

		this.username = username;
		return this;
	}

	public DbConnectionProperties setPassword(String password) {

		this.password = password;
		return this;
	}

	public DbConnectionProperties setProperty(String key, String value) {

		extraProperties.put(key, value);
		return this;
	}

	public DbConnectionProperties setConnectionPoolEnabled(boolean connectionPoolEnabled) {

		this.connectionPoolEnabled = connectionPoolEnabled;
		return this;
	}

	public DbConnectionProperties setConnectionPoolMinimum(Integer connectionPoolMinimum) {

		this.connectionPoolMinimum = connectionPoolMinimum;
		return this;
	}

	public DbConnectionProperties setConnectionPoolMaximum(Integer connectionPoolMaximum) {

		this.connectionPoolMaximum = connectionPoolMaximum;
		return this;
	}

	public DbConnectionProperties setConnectionPoolIdleTimeout(Long connectionPoolIdleTimeout) {

		this.connectionPoolIdleTimeout = connectionPoolIdleTimeout;
		return this;
	}
}
