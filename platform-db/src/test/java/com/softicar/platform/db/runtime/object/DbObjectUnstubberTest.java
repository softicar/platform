package com.softicar.platform.db.runtime.object;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class DbObjectUnstubberTest extends AbstractDbObjectTest {

	@Test
	public void testAfterFailedUnstubbing() {

		DbTestObject object = DbTestObject.TABLE.getStub(1234);
		assertTrue(object.stub());
		assertFalse(object.impermanent());

		DbTestObject.TABLE.refreshAll(Collections.singleton(object));
		assertFalse(object.stub());
		assertTrue(object.impermanent());
	}

	@Test
	public void testAfterSuccessfulUnstubbing() {

		Integer id = insertObjectRow("foo");

		DbTestObject object = DbTestObject.TABLE.getStub(id);
		assertTrue(object.stub());
		assertFalse(object.impermanent());

		DbTestObject.TABLE.refreshAll(Collections.singleton(object));
		assertFalse(object.stub());
		assertFalse(object.impermanent());
	}

	@Test
	public void testUnstubbingWithNullObjects() {

		Integer id = insertObjectRow("foo");
		List<DbTestObject> stubs = Arrays.asList(DbTestObject.TABLE.getStub(id), null);
		DbTestObject.TABLE.refreshAll(stubs);
		assertFalse(DbTestObject.TABLE.getStub(id).stub());
	}
}
