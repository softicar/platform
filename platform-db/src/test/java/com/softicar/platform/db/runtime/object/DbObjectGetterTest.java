package com.softicar.platform.db.runtime.object;

import org.junit.Test;

public class DbObjectGetterTest extends AbstractDbObjectTest {

	@Test
	public void testGettingNull() {

		assertNull(DbTestObject.TABLE.get(null));
	}

	@Test
	public void testGettingObjectById() {

		Integer id = insertObjectRow("foo");
		DbTestObject object = DbTestObject.TABLE.get(id);
		assertNotNull(object);
		assertEquals("foo", object.getString());
	}

	@Test
	public void testGettingDeletedObjectInCache() {

		DbTestObject object = insertObject("foo");
		object.delete();
		assertNull(DbTestObject.TABLE.get(object.getId()));
	}

	@Test
	public void testFlagsAfterSuccessfulLoading() {

		Integer id = insertObjectRow("foo");

		DbTestObject stub = DbTestObject.TABLE.getStub(id);
		assertTrue(stub.stub());
		assertFalse(stub.impermanent());

		DbTestObject object = DbTestObject.TABLE.get(id);
		assertNotNull(object);
		assertFalse(stub.stub());
		assertFalse(stub.impermanent());
	}

	@Test
	public void testFlagsAfterFailedLoading() {

		DbTestObject stub = DbTestObject.TABLE.getStub(NON_EXISTING_ID);
		assertTrue(stub.stub());
		assertFalse(stub.impermanent());

		DbTestObject object = DbTestObject.TABLE.get(NON_EXISTING_ID);
		assertNull(object);
		assertFalse(stub.stub());
		assertTrue(stub.impermanent());
	}
}
