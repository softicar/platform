package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.core.thread.collection.ThreadCollection;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.db.core.transaction.DbTransaction;
import org.junit.Test;

public class DbObjectLockingTest extends AbstractDbObjectTest {

	protected int testObjectId;

	@Test
	public void test() {

		// create test object
		DbTestObject testObject = new DbTestObject();
		testObject.setInteger(33);
		testObject.save();
		this.testObjectId = testObject.getId();

		// create increaser instances
		Increaser increaser1 = new Increaser();
		Increaser increaser2 = new Increaser();

		// create and run threads
		ThreadCollection<Thread> threads = new ThreadCollection<>();
		threads.add(new Thread(increaser1));
		threads.add(new Thread(increaser2));
		boolean success = threads.runAll(1000);

		// validate results
		assertTrue(success);
		if (increaser1.value == 33) {
			assertEquals(33, increaser1.value);
			assertEquals(34, increaser2.value);
		} else {
			assertEquals(34, increaser1.value);
			assertEquals(33, increaser2.value);
		}
		testObject.reload();
		assertEquals(35, testObject.getInteger().intValue());
	}

	protected class Increaser implements Runnable {

		protected int value;

		@Override
		public void run() {

			try (IDbDatabaseScope scope = new DbDatabaseScope(testDatabase)) {
				try (DbTransaction transaction = new DbTransaction()) {
					// loading object and locking it
					DbTestObject testObject = DbTestObject.TABLE.get(testObjectId);
					testObject.reloadForUpdate();

					// give other thread some time to do the same
					Thread.yield();

					// increase value
					this.value = testObject.getInteger();
					testObject.setInteger(value + 1);
					testObject.save();
					transaction.commit();
				}
			}
		}
	}
}
