package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import org.junit.Test;

public class DbConnectionOverrideScopeTest extends AbstractDbCoreTest {

	@Test
	@SuppressWarnings("resource")
	public void testDefaultConstructor() {

		// create normal connection
		IDbConnection normalConnection = DbConnections.getConnection();
		assertNotNull(normalConnection);
		assertTrue(normalConnection.isValid());

		// create override scope
		IDbConnection overrideConnection = null;
		try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope()) {
			overrideConnection = DbConnections.getConnection();
			assertNotSame(normalConnection, overrideConnection);
			assertNotNull(overrideConnection);
			assertTrue(overrideConnection.isValid());
		}

		// check override connection is closed
		assertTrue(overrideConnection.isClosed());

		// check normal connection is untouched
		assertSame(normalConnection, DbConnections.getConnection());
		assertTrue(normalConnection.isValid());
	}

	@Test
	@SuppressWarnings("resource")
	public void testConnectionTakingConstructor() {

		DbConnection overrideConnection = new DbConnection(DbCurrentDatabase.get());
		try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope(overrideConnection)) {
			assertSame(overrideConnection, DbConnections.getConnection());
		}

		// check override connection is _not_ closed
		assertFalse(overrideConnection.isClosed());

		// check override connection is _not_ returned anymore
		IDbConnection normalConnection = DbConnections.getConnection();
		assertNotSame(overrideConnection, normalConnection);
	}
}
