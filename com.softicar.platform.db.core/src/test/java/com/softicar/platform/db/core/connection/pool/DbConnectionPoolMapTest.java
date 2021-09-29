package com.softicar.platform.db.core.connection.pool;

import com.softicar.platform.db.core.connection.IDbServerType;
import com.softicar.platform.db.core.connection.connector.IDbConnector;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.sql.Connection;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.junit.Test;
import org.mockito.Mockito;

public class DbConnectionPoolMapTest extends AbstractDbCoreTest {

	private final DbConnectionPoolMap poolMap;
	private final TestConnector connector;
	private final TestServerType serverType;
	private Supplier<DataSource> connectionPoolFactory;

	public DbConnectionPoolMapTest() {

		this.connector = new TestConnector();
		this.serverType = new TestServerType();
		this.poolMap = new DbConnectionPoolMap();
	}

	@Test
	public void testGetConnectionPoolWithEqualDatabases() {

		this.connectionPoolFactory = () -> Mockito.mock(DataSource.class);

		DataSource pool1 = getPool(serverType, "host", "db");
		DataSource pool2 = getPool(serverType, "host", "db");
		assertNotNull(pool1);
		assertNotNull(pool2);
		assertSame(pool1, pool2);
	}

	@Test
	public void testGetConnectionPoolWithDifferentHostnames() {

		this.connectionPoolFactory = () -> Mockito.mock(DataSource.class);

		DataSource pool1 = getPool(serverType, "host1", "db");
		DataSource pool2 = getPool(serverType, "host2", "db");
		assertNotNull(pool1);
		assertNotNull(pool2);
		assertNotSame(pool1, pool2);
	}

	private DataSource getPool(IDbServerType serverType, String hostname, String databaseName) {

		return poolMap
			.getConnectionPool(
				new DbDatabaseBuilder()//
					.setServerType(serverType)
					.setHostname(hostname)
					.setDatabaseName(databaseName)
					.build());
	}

	private class TestConnector implements IDbConnector {

		@Override
		public Connection connect(IDbDatabase database) {

			throw new UnsupportedOperationException();
		}

		@Override
		public DataSource createConnectionPool(IDbDatabase database) {

			return connectionPoolFactory.get();
		}
	}

	private class TestServerType implements IDbServerType {

		@Override
		public IDbConnector getConnector() {

			return connector;
		}

		@Override
		public IDbServerQuirks getQuirks() {

			throw new UnsupportedOperationException();
		}
	}
}
