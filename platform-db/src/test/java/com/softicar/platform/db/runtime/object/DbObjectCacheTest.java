package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import org.junit.Test;

public class DbObjectCacheTest extends AbstractDbObjectTest {

	@Test
	public void testInvalidatedFlagAfterCacheInvalidation() {

		DbTestObject stub = DbTestObject.TABLE.getStub(1);
		DbTableRowCaches.invalidateAll();
		assertTrue(stub.invalidated());
	}

	@Test
	public void testInvalidatedFlagAfterCacheReloading() {

		Integer id = insertObjectRow("foo");
		DbTestObject object = DbTestObject.TABLE.get(id);
		DbTableRowCaches.reloadAll();
		assertFalse(object.invalidated());
	}
}
