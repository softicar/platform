package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.cache.CurrentDbTableRowCacheMap;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

public class DbObjectLoaderTest extends AbstractDbObjectTest {

	@Test
	public void testLoadingNullId() {

		assertNull(DbTestObject.TABLE.load(null, null));
	}

	@Test
	public void testLoadingNonExistingObject() {

		assertNull(DbTestObject.TABLE.load(NON_EXISTING_ID, null));
	}

	@Test
	public void testLoadingDeletedObjectInCache() {

		DbTestObject object = insertObject("foo");
		object.delete();
		assertNull(DbTestObject.TABLE.load(object.getId(), null));
	}

	@Test
	public void testLoadingExistingObjectWithoutLock() {

		Integer id = insertObjectRow("foo");
		DbTestObject object = DbTestObject.TABLE.load(id, null);

		assertNotNull(object);
		assertEquals(id, object.getId());
		assertEquals("foo", object.getString());
		new DbTableRowFlagsAsserter(object).assertNone();
	}

	@Test
	public void testLoadingCachedObject() {

		// insert and cache initial object
		Integer id = insertObjectRow("foo");
		DbTestObject.TABLE.get(id);

		// update table row in database
		updateObjectRow(id, "bar");

		DbTestObject object = DbTestObject.TABLE.load(id, null);
		assertNotNull(object);
		assertEquals("bar", object.getString());
	}

	@Test(expected = DbException.class)
	public void testLoadingObjectFromDifferentCacheInstance() {

		DbTestObject object = insertObject("foo");

		CurrentDbTableRowCacheMap.set(null);

		object.reload();
	}
}
