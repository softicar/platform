package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

public class DbObjectCopyTest extends AbstractDbObjectTest {

	@Test
	public void testCopyingNonPersistentObject() {

		DbTestObject source = DbTestObject.TABLE.createObject();
		source.setString("foo");
		source.setInteger(23);
		DbTestObject copy = source.copy();

		assertNull(copy.getId());
		assertEquals("foo", copy.getString());
		assertEquals(23, copy.getInteger().intValue());
		new DbTableRowFlagsAsserter(copy).assertOnlyImpermanent();
	}

	@Test
	public void testCopyingPersistentObject() {

		Integer id = insertObjectRow("foo");
		DbTestObject source = DbTestObject.TABLE.get(id);
		source.setInteger(23);
		DbTestObject copy = source.copy();

		assertNull(copy.getId());
		assertEquals("foo", copy.getString());
		assertEquals(23, copy.getInteger().intValue());
		new DbTableRowFlagsAsserter(copy).assertOnlyImpermanent();
	}

	@Test
	public void testCopyingNonExistingStub() {

		DbTestObject source = DbTestObject.TABLE.getStub(NON_EXISTING_ID);

		assertTrue(source.stub());
		assertFalse(source.impermanent());

		DbTestObject copy = source.copy();

		assertFalse(source.stub());
		assertTrue(source.impermanent());
		assertFalse(copy.stub());
		assertTrue(copy.impermanent());

		assertNull(copy.getId());
		assertNull(copy.getString());
		new DbTableRowFlagsAsserter(copy).assertOnlyImpermanent();
	}

	@Test
	public void testCopyingPersistentStub() {

		Integer id = insertObjectRow("foo");
		DbTestObject source = DbTestObject.TABLE.getStub(id);
		DbTestObject copy = source.copy();

		assertFalse(source.stub());
		assertFalse(copy.stub());

		assertNull(copy.getId());
		assertEquals("foo", copy.getString());
		new DbTableRowFlagsAsserter(copy).assertOnlyImpermanent();
	}

	@Test
	public void testCopyingOfByteArrays() {

		DbTestObject source = new DbTestObject();
		source.setBytes(new byte[] { 17 });
		assertEquals(17, source.getBytes()[0]);

		DbTestObject copy = source.copy();
		assertEquals(17, copy.getBytes()[0]);
		assertNotSame(source.getBytes(), copy.getBytes());
		new DbTableRowFlagsAsserter(copy).assertOnlyImpermanent();
	}
}
