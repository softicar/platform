package com.softicar.platform.db.core;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.PropertyFactory;
import com.softicar.platform.common.core.properties.SofticarConfiguration;
import com.softicar.platform.db.core.connection.DbServerType;

/**
 * Enumerates all core properties.
 *
 * @author Oliver Richers
 */
public class DbProperties {

	private static final PropertyFactory FACTORY = SofticarConfiguration.createPropertyFactory("com.softicar.db");

	public static final IProperty<DbServerType> SERVER_TYPE = FACTORY.createEnumProperty(DbServerType.class, "server.type", DbServerType.MARIA_DB);
	public static final IProperty<String> SERVER_ADDRESS = FACTORY.createStringProperty("server.address", "127.0.0.1");
	public static final IProperty<String> DATABASE = FACTORY.createStringProperty("database", "");

	public static final IProperty<String> USERNAME = FACTORY.createStringProperty("username", "softicar");
	public static final IProperty<String> PASSWORD = FACTORY.createStringProperty("password", "");

	public static final IProperty<Integer> CONNECTION_RETRY_COUNT = FACTORY.createIntegerProperty("connection.retry.count", 3);
	public static final IProperty<Long> CONNECTION_RETRY_DELAY = FACTORY.createLongProperty("connection.retry.delay", 2000L);

	public static final IProperty<Boolean> CONNECTION_POOL_ENABLED = FACTORY.createBooleanProperty("connection.pool.enabled", false);
	public static final IProperty<Integer> CONNECTION_POOL_MINIMUM = FACTORY.createIntegerProperty("connection.pool.minimum", 1);
	public static final IProperty<Integer> CONNECTION_POOL_MAXIMUM = FACTORY.createIntegerProperty("connection.pool.maximum", 8);
	public static final IProperty<Long> CONNECTION_POOL_IDLE_TIMEOUT = FACTORY.createLongProperty("connection.pool.idle.timeout", 600000L);
}
