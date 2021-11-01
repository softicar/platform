package com.softicar.platform.db.core.connection.connector;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.properties.SofticarConfiguration;
import com.softicar.platform.common.core.retry.RetryingSupplier;
import com.softicar.platform.common.core.utils.NullUtils;
import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.db.core.DbProperties;
import com.softicar.platform.db.core.connection.IDbConnectionProperties;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

public abstract class AbstractDbConnector implements IDbConnector {

	private static final int MAXIMUM_CONNECTION_ATTEMPTS = 25;
	private static final long MINIMUN_CONNECTION_ATTEMPT_DELAY = 100;

	@Override
	public Connection connect(IDbDatabase database) {

		loadDriverClass();

		return new RetryingSupplier<>(() -> doConnect(database))//
			.setTryCount(getConnectionAttempts())
			.setRetryDelayMillis(getConnectionAttemptDelay())
			.get();
	}

	@Override
	public DataSource createConnectionPool(IDbDatabase database) {

		loadDriverClass();

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(getUrl(database));

		IDbConnectionProperties connectionProperties = database.getConnectionProperties();
		config.setUsername(connectionProperties.getUsername());
		config.setPassword(connectionProperties.getPassword());
		NullUtils.consumeIfNotNull(connectionProperties.getConnectionPoolMinimum(), config::setMinimumIdle);
		NullUtils.consumeIfNotNull(connectionProperties.getConnectionPoolMaximum(), config::setMaximumPoolSize);
		NullUtils.consumeIfNotNull(connectionProperties.getConnectionPoolIdleTimeout(), config::setIdleTimeout);
		config.setDataSourceProperties(getCombinedProperties(database));

		return new HikariDataSource(config);
	}

	protected abstract String getUrl(IDbDatabase database);

	protected abstract String getDriverClassName();

	protected abstract Properties createDefaultProperties(IDbDatabase database);

	private void loadDriverClass() {

		try {
			Class.forName(getDriverClassName());
		} catch (ClassNotFoundException exception) {
			throw new SofticarException(exception);
		}
	}

	private Connection doConnect(IDbDatabase database) {

		String url = getUrl(database);
		Properties properties = getCombinedProperties(database);
		try {
			return DriverManager.getConnection(url, properties);
		} catch (SQLException exception) {
			logError(url, exception);
			reloadSofticarConfiguration();
			throw new DbConnectionFailureException(exception);
		}
	}

	private Properties getCombinedProperties(IDbDatabase database) {

		Properties properties = createDefaultProperties(database);
		database.getConnectionProperties().copyPropertiesTo(properties);
		return properties;
	}

	private int getConnectionAttempts() {

		int attempts = 1 + DbProperties.CONNECTION_RETRY_COUNT.getValue();
		return Clamping.clamp(1, MAXIMUM_CONNECTION_ATTEMPTS, attempts);
	}

	private long getConnectionAttemptDelay() {

		long delay = DbProperties.CONNECTION_RETRY_DELAY.getValue();
		return Math.max(MINIMUN_CONNECTION_ATTEMPT_DELAY, delay);
	}

	private void logError(String url, SQLException exception) {

		Log.ferror("Failed to connect to '%s': %s", url, exception.getMessage());
	}

	private void reloadSofticarConfiguration() {

		Log.finfo("Reloading: %s", SofticarConfiguration.class.getSimpleName());
		SofticarConfiguration.getSingleton().reload();
	}
}
