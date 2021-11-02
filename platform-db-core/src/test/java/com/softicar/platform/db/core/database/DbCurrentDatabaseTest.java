package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.connection.DbServerType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class DbCurrentDatabaseTest extends Assert {

	private final DbDatabase database;

	public DbCurrentDatabaseTest() {

		this.database = new DbDatabaseBuilder()//
			.setServerType(DbServerType.H2_MEMORY)
			.build();
	}

	@After
	public void cleanup() {

		DbCurrentDatabase.set(null);
	}

	@Test
	public void testGetDatabase() {

		assertSame(DbDatabases.DEFAULT, DbCurrentDatabase.get());
	}

	@Test
	public void testGetDatabaseAfterSetDatabase() {

		DbCurrentDatabase.set(database);

		assertSame(database, DbCurrentDatabase.get());
	}

	@Test(timeout = 1000)
	public void testWithSubThread() throws InterruptedException {

		DbCurrentDatabase.set(database);

		TestRunnable runnable = new TestRunnable();
		Thread thread = new Thread(runnable);
		thread.start();
		thread.join();

		assertSame(database, runnable.getDatabase());
	}

	private static class TestRunnable implements Runnable {

		private IDbDatabase database;

		@Override
		public void run() {

			this.database = DbCurrentDatabase.get();
		}

		public IDbDatabase getDatabase() {

			return database;
		}
	}
}
