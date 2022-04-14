package com.softicar.platform.db.core;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbConnectionCache;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.database.DbDatabaseKey;
import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("resource")
public class DbConnectionCacheTest extends AbstractTest {

	private final DbConnectionCache connectionCache;
	private final DbDatabaseKey someDatabaseKey;
	private final IDbConnection someConnection;

	public DbConnectionCacheTest() {

		this.connectionCache = new DbConnectionCache();
		this.someDatabaseKey = new DbDatabaseKey(DbServerType.H2_MEMORY, "host", "database");
		this.someConnection = Mockito.mock(IDbConnection.class);

		// add a valid connection
		Mockito.when(someConnection.isValid()).thenReturn(true);
		this.connectionCache.addConnection(someDatabaseKey, someConnection);
	}

	@Test
	public void getConnectionOnEmptyCacheReturnsNull() {

		assertNull(new DbConnectionCache().getConnection(someDatabaseKey));
	}

	@Test
	public void getConnectionWithValidConnectionReturnsConnection() {

		assertSame(someConnection, connectionCache.getConnection(someDatabaseKey));
	}

	@Test
	public void getConnectionWithInvalidConnectionReturnsNull() {

		Mockito.when(someConnection.isValid()).thenReturn(false);
		assertNull(connectionCache.getConnection(someDatabaseKey));
	}

	@Test
	public void getConnectionWithDifferentHostReturnsNull() {

		assertNull(connectionCache.getConnection(new DbDatabaseKey(DbServerType.H2_MEMORY, "otherHost", "database")));
	}

	@Test
	public void getConnectionWithDifferentDatabaseReturnsNull() {

		assertNull(connectionCache.getConnection(new DbDatabaseKey(DbServerType.H2_MEMORY, "host", "otherDatabase")));
	}
}
