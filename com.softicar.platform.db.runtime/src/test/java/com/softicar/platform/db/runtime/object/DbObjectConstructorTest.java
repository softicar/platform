package com.softicar.platform.db.runtime.object;

import org.junit.Test;

public class DbObjectConstructorTest extends AbstractDbObjectTest {

	@Test
	public void testFieldValuesWithDefaultConstrutor() {

		DbTestObject object = new DbTestObject();
		assertNull(object.getId());
		assertEquals(DbTestObject.DEFAULT_INTEGER_VALUE, object.getInteger());
		assertEquals(DbTestObject.DEFAULT_STRING_VALUE, object.getString());
	}

	@Test
	public void testFieldValuesWithCopyConstructor() {

		DbTestObject sourceObject = new DbTestObject();
		sourceObject.setInteger(88);
		sourceObject.setString("bar");
		sourceObject.save();

		DbTestObject object = new DbTestObject(sourceObject);
		assertNull(object.getId());
		assertEquals(Integer.valueOf(88), object.getInteger());
		assertEquals("bar", object.getString());
	}
}
