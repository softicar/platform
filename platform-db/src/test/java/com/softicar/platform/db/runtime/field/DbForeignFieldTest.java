package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.cache.CurrentDbTableRowCacheMap;
import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
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
	public void testPrefetchingLoadsInvalidatedObjects() {

		// setup: insert objects
		DbTestObject object = insertObject("invalidated");
		DbTestObject referencingObject = insertObject(object);

		// setup: invalidate the object
		object.invalidate();

		// setup: change the object in the database, circumventing the cache
		new DbStatement()//
			.addText("UPDATE " + DbTestObject.TABLE + " SET " + DbTestObject.STRING_FIELD + " = ?")
			.addParameter("updated")
			.executeUpdate();

		// setup: validate preconditions
		assertTrue(object.invalidated());
		assertFalse(object.stub());
		assertFalse(referencingObject.invalidated());
		assertFalse(referencingObject.stub());

		// execute: prefetch the referencing object
		DbTestObject.FOREIGN_FIELD.prefetch(List.of(referencingObject));

		// assert result
		assertEquals("updated", referencingObject.getForeign().getString());
	}

	@Test
	public void testPrefetchingLoadsStubObjects() {

		// setup: insert objects
		int objectId = new DbStatement()//
			.addText("INSERT INTO " + DbTestObject.TABLE + " (" + DbTestObject.STRING_FIELD + ") VALUES ('stub')")
			.executeInsert();
		int referencingObjectId = new DbStatement()//
			.addText("INSERT INTO " + DbTestObject.TABLE + " (" + DbTestObject.FOREIGN_FIELD + ") VALUES (" + objectId + ")")
			.executeInsert();

		// setup: create stubs
		DbTestObject object = DbTestObject.TABLE.getStub(objectId);
		DbTestObject referencingObject = DbTestObject.TABLE.getStub(referencingObjectId);

		// setup: change the object in the database, circumventing the cache
		new DbStatement()//
			.addText("UPDATE " + DbTestObject.TABLE + " SET " + DbTestObject.STRING_FIELD + " = ?")
			.addParameter("updated")
			.executeUpdate();

		// setup: validate preconditions
		assertFalse(object.invalidated());
		assertTrue(object.stub());
		assertFalse(referencingObject.invalidated());
		assertTrue(referencingObject.stub());

		// execute: prefetch the referencing object
		DbTestObject.FOREIGN_FIELD.prefetch(List.of(referencingObject));

		// assert result
		assertEquals("updated", referencingObject.getForeign().getString());
	}

	@Test
	public void testPrefetchingDoesNotLoadCachedObjects() {

		// setup: insert objects
		DbTestObject object = insertObject("cached");
		DbTestObject referencingObject = insertObject(object);

		// setup: change the object in the database, circumventing the cache
		new DbStatement()//
			.addText("UPDATE " + DbTestObject.TABLE + " SET " + DbTestObject.STRING_FIELD + " = ?")
			.addParameter("updated")
			.executeUpdate();

		// setup: validate preconditions
		assertFalse(object.invalidated());
		assertFalse(object.stub());
		assertFalse(referencingObject.invalidated());
		assertFalse(referencingObject.stub());

		// execute: prefetch the referencing object
		DbTestObject.FOREIGN_FIELD.prefetch(List.of(referencingObject));

		// assert result
		assertEquals("cached", referencingObject.getForeign().getString());
	}
}
