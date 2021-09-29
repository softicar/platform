package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.connection.connector.IDbConnector;
import com.softicar.platform.db.core.database.DbDatabase;
import com.softicar.platform.db.core.database.DbDatabaseBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DbConnectionIsValidTest extends Assert {

	private final IDbServerType serverType;
	private final IDbConnector connector;
	private final Connection connection;
	private final DbDatabase database;

	@SuppressWarnings("resource")
	public DbConnectionIsValidTest() {

		this.serverType = Mockito.mock(IDbServerType.class);
		this.connector = Mockito.mock(IDbConnector.class);
		this.connection = Mockito.mock(Connection.class);
		this.database = new DbDatabaseBuilder()//
			.setServerType(serverType)
			.build();

		Mockito.when(serverType.getConnector()).thenReturn(connector);
		Mockito.when(connector.connect(Mockito.any())).thenReturn(connection);
	}

	@Test
	public void testIsValidWithNewConnection() throws SQLException {

		try (DbConnection connection = new DbConnection(database)) {
			assertTrue(connection.isValid());
		}

		// assert that no test is executed
		Mockito.verify(connection).close();
		Mockito.verifyNoMoreInteractions(connection);
	}

	@Test
	public void testIsValidWithClosedConnection() {

		try (DbConnection connection = new DbConnection(database)) {
			connection.close();
			assertFalse(connection.isValid());
		}
	}
}
