package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.exception.DbException;
import org.junit.Test;

public class DbObjectDeleterTest extends AbstractDbObjectTest {

	@Test
	public void testPersistentFlagAfterDeletion() {

		// insert object
		DbTestObject object = DbTestObject.TABLE.createObject();
		object.setString("foo");
		object.save();
		assertFalse(object.impermanent());

		// delete object
		object.delete();
		assertTrue(object.impermanent());
	}

	@Test
	public void testDeletingObject() {

		DbTestObject object = insertObject("foo");
		object.delete();
		assertEquals(0, DbTestObject.TABLE.countAll());
	}

	@Test(expected = DbException.class)
	public void testDeletingDeletedObject() {

		DbTestObject object = insertObject("foo");
		object.delete();
		object.delete();
	}
}
