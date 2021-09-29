package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.transaction.DbTransaction;
import org.junit.Test;

public class DbObjectBackuperTest extends AbstractDbObjectTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";

	@Test
	public void restoresOnExplicitRollback() {

		DbTestObject object = new DbTestObject();
		object.setString(A);

		try (DbTransaction transaction = new DbTransaction()) {
			object.setString(B);
			assertEquals(B, object.getString());

			transaction.rollback();
			assertEquals(A, object.getString());
		}
	}

	@Test
	public void elevatesBackups() {

		DbTestObject object = new DbTestObject();
		object.setString(A);

		try (DbTransaction rootTransaction = new DbTransaction()) {
			try (DbTransaction childTransaction = new DbTransaction()) {
				object.setString(B);
				childTransaction.commit();
			}
			object.setString(C);
			rootTransaction.rollback();
		}

		assertEquals(A, object.getString());
	}

//	@Test
//	public void restoresOnlyOncePerTransaction() {
//
//		Callback callback = new Callback();
//		TestDbObject test = new TestDbObject();
//		test.register(callback, callback);
//
//		try (DbTransaction transaction = new DbTransaction()) {
//			test.setInteger1(1);
//			test.setInteger2(2);
//			test.setInteger3(3);
//
//			try (DbTransaction innerTransaction = new DbTransaction()) {
//				test.setString1("foo");
//				test.setString2("bar");
//				test.setString3("baz");
//			}
//			assertEquals(1, callback.getRestoreCount());
//		}
//
//		assertEquals(2, callback.getRestoreCount());
//	}

	@Test
	public void restoresValues() {

		// initialize
		DbTestObject object = new DbTestObject();
		object.setString(A);

		// start outermost transaction
		try (DbTransaction outerTransaction = new DbTransaction()) {
			object.setString(B);
			assertEquals(B, object.getString());

			// start inner transaction and rollback
			try (DbTransaction innerTransaction = new DbTransaction()) {
				object.setString(C);
				assertEquals(C, object.getString());

				// rollback inner transaction
				innerTransaction.rollback();
				assertEquals(B, object.getString());
			}

			// start inner transaction and commit
			try (DbTransaction innerTransaction = new DbTransaction()) {
				object.setString("boo");
				assertEquals("boo", object.getString());

				// commit inner transaction
				innerTransaction.commit();
				assertEquals("boo", object.getString());
			}

			// rollback outer transaction
			assertEquals("boo", object.getString());
			outerTransaction.rollback();
			assertEquals(A, object.getString());
		}

		// finish
		assertEquals(A, object.getString());
	}

	@Test
	public void restoresIdToNull() {

		DbTestObject object = new DbTestObject();
		assertNull(object.getId());

		try (DbTransaction transaction = new DbTransaction()) {
			object.save();
			assertNotNull(object.getId());
			throw new RuntimeException();
		} catch (RuntimeException exception) {
			DevNull.swallow(exception);
			assertNull(object.getId());
		}

		assertNull(object.getId());
	}

	@Test
	public void removesFromCacheOnRollback() {

		Integer id = null;
		DbTestObject object = new DbTestObject();

		try (DbTransaction transaction = new DbTransaction()) {
			object.save();
			id = object.getId();
			assertNotNull(id);
			assertSame(object, DbTestObject.TABLE.getCache().getSimple(id));
		}

		assertNull(DbTestObject.TABLE.getCache().getSimple(id));
	}

	@Test
	public void testImpermanentFlagAfterRevertedDelete() {

		DbTestObject object = new DbTestObject();
		object.save();
		try (DbTransaction transaction = new DbTransaction()) {
			object.delete();
			assertTrue(object.impermanent());
		}
		assertFalse(object.impermanent());
	}

	@Test
	public void restoresInNestedTransaction() {

		DbTestObject object = new DbTestObject();
		assertNull(object.getId());

		try (DbTransaction outerTransaction = new DbTransaction()) {
			try (DbTransaction innerTransaction = new DbTransaction()) {
				object.save();
				assertNotNull(object.getId());
				throw new RuntimeException();
			} catch (RuntimeException exception) {
				DevNull.swallow(exception);
				assertNull(object.getId());
			}
			assertNull(object.getId());
		}

		assertNull(object.getId());
	}
}
