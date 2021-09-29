package com.softicar.platform.db.core.connection.connector;

import com.softicar.platform.db.core.connection.IDbServerType;
import com.softicar.platform.db.core.database.DbDatabase;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import com.softicar.platform.db.core.dbms.h2.DbH2MemoryConnector;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Test;
import org.mockito.Mockito;

public class DbConnectorTest extends AbstractDbCoreTest {

	@Test
	public void testDefaultConnectionPropertiesWithPool() throws SQLException {

		IDbServerType serverWithToUpper = mockServerType(true);
		IDbServerType serverWithoutToUpper = mockServerType(false);

		assertEquals("FOO", createSchema(serverWithToUpper, "foo"));
		assertEquals("foo", createSchema(serverWithoutToUpper, "foo"));
	}

	private IDbServerType mockServerType(boolean databaseToUpper) {

		DbH2MemoryConnector connector = new DbH2MemoryConnector();
		connector.setDatabaseToUpper(databaseToUpper);

		IDbServerType serverType = Mockito.mock(IDbServerType.class);
		Mockito.when(serverType.getConnector()).thenReturn(connector);
		return serverType;
	}

	private Object createSchema(IDbServerType serverType, String schemaName) throws SQLException {

		DbDatabase database = new DbDatabaseBuilder()//
			.setServerType(serverType)
			.setConnectionPoolEnabled(true)
			.setConnectionProperty("INIT", "CREATE SCHEMA " + schemaName)
			.build();

		DataSource connectionPool = serverType.getConnector().createConnectionPool(database);

		try (Connection connection = connectionPool.getConnection()) {
			try (ResultSet resultSet = connection.getMetaData().getSchemas()) {
				while (resultSet.next()) {
					if (resultSet.getString(1).equalsIgnoreCase(schemaName)) {
						return resultSet.getString(1);
					}
				}
			}
		}
		throw new AssertionError("Created schema not found.");
	}
}
