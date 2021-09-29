package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import org.junit.Assert;
import org.junit.Test;

public class DbObjectReloadTest extends AbstractDbObjectTest {

	@Test
	public void testReloadingSingleObject() {

		// insert object and modify table row
		DbTestObject object = new DbTestObject();
		object.setString("foo");
		object.save();
		updateObjectRow(object.getId(), "bar");

		// reload object
		assertEquals("foo", object.getString());
		boolean reloaded = object.reload();
		assertTrue(reloaded);
		assertEquals("bar", object.getString());
	}

	@Test
	public void testReloadingOfConcurentlyRemovedObject() {

		// insert object and delete table row
		DbTestObject object = new DbTestObject();
		object.save();
		deleteObjectRow(object.getId());

		// reload object
		boolean reloaded = object.reload();
		assertFalse(reloaded);
		assertTrue(object.impermanent());
	}

	@Test(expected = DbException.class)
	public void testLockingWithoutTransaction() {

		DbTestObject object = new DbTestObject();
		object.save();
		object.reload(SqlSelectLock.FOR_UPDATE);
	}

	@Test(expected = DbException.class)
	public void testReloadingWithoutPrimaryKey() {

		DbTestObject object = new DbTestObject();
		object.reload();
	}

	@Test
	public void testReloadingAfterRevertedSave() {

		try (DbTransaction transaction = new DbTransaction()) {
			DbTestObject object = new DbTestObject();
			object.setString("foo");
			object.save();
			transaction.rollback();
			try {
				object.reload();
				Assert.fail();
			} catch (DbException exception) {
				Assert.assertSame(exception.getRow(), object);
			}
		}
	}
}
