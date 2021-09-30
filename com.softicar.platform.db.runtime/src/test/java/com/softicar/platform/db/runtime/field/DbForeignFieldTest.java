package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.cache.CurrentDbTableRowCacheMap;
import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class DbForeignFieldTest extends AbstractDbObjectTest {

	@Test
	public void testGetFieldType() {

		assertEquals(SqlFieldType.INTEGER, DbTestObject.FOREIGN_FIELD.getFieldType());
	}

	@Test
	public void testGetValueType() {

		ISqlValueType<DbTestObject> valueType = DbTestObject.FOREIGN_FIELD.getValueType();

		assertEquals(1, valueType.getColumnCount());
		assertSame(DbTestObject.class, valueType.getValueClass());
	}

	@Test
	public void testGetValueId() {

		DbTestObject foreignObject = new DbTestObject(42);
		DbTestObject object = new DbTestObject();
		object.setForeign(foreignObject);
		assertEquals(42, DbTestObject.FOREIGN_FIELD.getValueId(object).intValue());
	}

	@Test
	public void testGetValueIdDoesNotUnstubForeignObject() {

		// insert foreign object and initial object
		DbTestObject foreignObject = new DbTestObject();
		foreignObject.save();
		DbTestObject initialObject = new DbTestObject();
		initialObject.setForeign(foreignObject);
		initialObject.save();

		// clear caches
		CurrentDbTableRowCacheMap.set(null);

		// test getting value id
		DbTestObject object = DbTestObject.TABLE.get(initialObject.getId());
		assertTrue(object.getForeign().stub());
		assertNotNull(DbTestObject.FOREIGN_FIELD.getValueId(object));
		assertEquals(foreignObject.getId(), DbTestObject.FOREIGN_FIELD.getValueId(object));
		assertTrue(object.getForeign().stub());

		// ensure un-stubbing works
		object.getForeign().getString();
		assertFalse(object.getForeign().stub());
	}

	@Test
	public void testGetValueIdWithNullReference() {

		// create object and try to get foreign value ID
		DbTestObject object = new DbTestObject();
		assertNull(object.getForeign());
		assertNull(DbTestObject.FOREIGN_FIELD.getValueId(object));
	}

	@Test
	public void testPrefetching() {

		// insert foreign objects and referencing objects
		for (int i = 0; i < 5; ++i) {
			DbTestObject foreignObject = new DbTestObject();
			foreignObject.setString("foreign");
			foreignObject.save();
			for (int j = 0; j < 5; ++j) {
				DbTestObject referencingObject = new DbTestObject();
				referencingObject.setForeign(foreignObject);
				referencingObject.setString("referencing");
				referencingObject.save();
			}
		}

		// clear caches
		CurrentDbTableRowCacheMap.set(null);

		// load referencing objects
		List<DbTestObject> referencingObjects = DbTestObject.TABLE.createSelect().where(DbTestObject.STRING_FIELD.isEqual("referencing")).list();
		assertEquals(25, referencingObjects.size());

		// check that foreign objects are stubs
		for (DbTestObject object: referencingObjects) {
			assertTrue(object.getForeign().stub());
		}

		// prefetch foreign objects
		List<DbTestObject> foreignObjects = DbTestObject.FOREIGN_FIELD.prefetch(referencingObjects);
		assertEquals(5, foreignObjects.size());

		// check that foreign objects are un-stubbed
		for (DbTestObject object: referencingObjects) {
			assertFalse(object.getForeign().stub());
		}
	}

	@Test
	public void testPrefetchingNullReferences() {

		DbTestObject object = new DbTestObject();
		List<DbTestObject> prefetchedObjects = DbTestObject.FOREIGN_FIELD.prefetch(Collections.singleton(object));
		assertTrue(prefetchedObjects.isEmpty());
	}

	@Test
	public void testPrefetchingLoadsOnlyStubObjectsAndInvalidatedObjects() {

		// insert objects initially
		DbTestObject stubObject = insertObject("stub");
		DbTestObject stubReferencingObject = insertObject(stubObject);
		DbTestObject cachedObject = insertObject("cached");
		DbTestObject cachedReferencingObject = insertObject(cachedObject);
		DbTestObject invalidatedObject = insertObject("invalidated");
		DbTestObject invalidatedReferencingObject = insertObject(invalidatedObject);

		// clear caches
		CurrentDbTableRowCacheMap.set(null);

		// load cached and invalidated objects
		// we enabled the invalidated flag for the object by invalidating it in the cache
		DbTestObject.TABLE.get(cachedObject.getId());
		DbTestObject.TABLE.get(invalidatedObject.getId());
		DbTestObject.TABLE.getCache().invalidate(invalidatedObject.getId());

		// change all objects in database, circumventing the cache
		new DbStatement()//
			.addText("UPDATE " + DbTestObject.TABLE + " SET " + DbTestObject.STRING_FIELD + " = ?")
			.addParameter("xxx")
			.executeUpdate();

		// load all referencing objects
		stubReferencingObject = DbTestObject.TABLE.get(stubReferencingObject.getId());
		cachedReferencingObject = DbTestObject.TABLE.get(cachedReferencingObject.getId());
		invalidatedReferencingObject = DbTestObject.TABLE.get(invalidatedReferencingObject.getId());

		// prefetch foreign objects and verify values:
		// - the stub-object was not in the cache and so it will be loaded from the database
		// - the cached object will not be loaded from the database and retains its values
		// - the invalidated object will be reloaded from the database
		DbTestObject.FOREIGN_FIELD.prefetch(Arrays.asList(stubReferencingObject, cachedReferencingObject, invalidatedReferencingObject));
		assertEquals("xxx", stubReferencingObject.getForeign().getString());
		assertEquals("cached", cachedReferencingObject.getForeign().getString());
		assertEquals("xxx", invalidatedReferencingObject.getForeign().getString());
	}
}
