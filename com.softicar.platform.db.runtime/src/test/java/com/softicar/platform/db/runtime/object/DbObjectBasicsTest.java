package com.softicar.platform.db.runtime.object;

import org.junit.Test;

/**
 * Test for the common methods of {@link Object}.
 *
 * @author Oliver Richers
 */
public class DbObjectBasicsTest extends AbstractDbObjectTest {

	@Test
	public void testEqualsOnDifferentObjectWithSameId() {

		assertFalse(new DbTestObject(1).equals(new DbTestObject(1)));
	}

	@Test
	@SuppressWarnings("unlikely-arg-type")
	public void testEqualsToInteger() {

		assertTrue(new DbTestObject(1).equals(Integer.valueOf(1)));
		assertFalse(new DbTestObject(1).equals(Integer.valueOf(2)));
	}

	@Test
	public void testHashCodeOnDifferentObjectWithSameId() {

		assertNotEquals(new DbTestObject(1).hashCode(), new DbTestObject(1).hashCode());
	}

	@Test
	public void testEqualsOnStubs() {

		DbTestObject stub = DbTestObject.TABLE.getStub(1);
		assertTrue(stub.equals(stub));
		assertFalse(stub.equals(null));

		DbTestObject stub2 = DbTestObject.TABLE.getStub(2);
		assertFalse(stub.equals(stub2));
		assertFalse(stub2.equals(stub));
	}
}
