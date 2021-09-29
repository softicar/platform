package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

public class DbObjectSaverTest extends AbstractDbObjectTest {

	// -------------------- id -------------------- //

	@Test
	public void testIdAfterSave() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		object.save();
		assertNotNull(object.getId());
	}

	@Test
	public void testIdAfterRollback() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		try (DbTransaction transaction = new DbTransaction()) {
			object.save();
		}
		assertNull(object.getId());
	}

	// -------------------- cache -------------------- //

	@Test
	public void testCacheAfterSave() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		object.save();
		assertSame(object, DbTestObject.TABLE.getCache().getSimple(object.getId()));
	}

	@Test
	public void testCacheAfterRollback() {

		Integer id = null;
		DbTestObject object = DbTestObject.TABLE.createObject();
		try (DbTransaction transaction = new DbTransaction()) {
			object.save();
			id = object.getId();
		}
		assertNull(DbTestObject.TABLE.getCache().getSimple(id));
	}

	// -------------------- field values -------------------- //

	@Test
	public void testInsertAndUpdateOfValues() {

		DbTestObject foreign1 = insertObject("foreign1");
		DbTestObject foreign2 = insertObject("foreign2");

		// insert
		DbTestObject object = DbTestObject.TABLE.createObject();
		object.setBytes(new byte[] { 13, 33 });
		object.setEnum(DbTestObjectFlag.MEDIUM);
		object.setInteger(33);
		object.setString("foo");
		object.setTime(new Time(7, 13, 17));
		object.setForeign(foreign1);
		object.save();

		// test values
		Integer id = object.getId();
		assertArrayEquals(new byte[] { 13, 33 }, selectValue(DbTestObject.BYTES_FIELD, id));
		assertEquals(DbTestObjectFlag.MEDIUM, selectValue(DbTestObject.ENUM_FIELD, id));
		assertEquals(Integer.valueOf(33), selectValue(DbTestObject.INTEGER_FIELD, id));
		assertEquals("foo", selectValue(DbTestObject.STRING_FIELD, id));
		assertEquals(new Time(7, 13, 17), selectValue(DbTestObject.TIME_FIELD, id));
		assertSame(foreign1, selectValue(DbTestObject.FOREIGN_FIELD, id));

		// update
		object.setBytes(new byte[] { 22, 34 });
		object.setEnum(DbTestObjectFlag.BIG);
		object.setInteger(18);
		object.setString("bar");
		object.setTime(new Time(18, 33, 59));
		object.setForeign(foreign2);
		object.save();

		// test values
		assertArrayEquals(new byte[] { 22, 34 }, selectValue(DbTestObject.BYTES_FIELD, id));
		assertEquals(DbTestObjectFlag.BIG, selectValue(DbTestObject.ENUM_FIELD, id));
		assertEquals(Integer.valueOf(18), selectValue(DbTestObject.INTEGER_FIELD, id));
		assertEquals("bar", selectValue(DbTestObject.STRING_FIELD, id));
		assertEquals(new Time(18, 33, 59), selectValue(DbTestObject.TIME_FIELD, id));
		assertSame(foreign2, selectValue(DbTestObject.FOREIGN_FIELD, id));

		// update to NULL
		object.setBytes(null);
		object.setInteger(null);
		object.setString(null);
		object.setTime(null);
		object.setForeign(null);
		object.save();

		// test values
		assertNull(selectValue(DbTestObject.BYTES_FIELD, id));
		assertNull(selectValue(DbTestObject.INTEGER_FIELD, id));
		assertNull(selectValue(DbTestObject.STRING_FIELD, id));
		assertNull(selectValue(DbTestObject.TIME_FIELD, id));
		assertNull(selectValue(DbTestObject.FOREIGN_FIELD, id));
	}

	// -------------------- foreign references -------------------- //

	@Test(expected = IllegalArgumentException.class)
	public void testInsertingWithReferenceToImpermanentObject() {

		DbTestObject foreign = new DbTestObject();
		DbTestObject object = new DbTestObject();
		object.setForeign(foreign);
		object.save();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdatingWithReferenceToImpermanentObject() {

		DbTestObject foreign = new DbTestObject();
		DbTestObject object = insertObject("foo");
		object.setForeign(foreign);
		object.save();
	}

	// -------------------- flags -------------------- //

	@Test
	public void testFlagsAfterSave() {

		// test after insert
		DbTestObject object = DbTestObject.TABLE.createObject();
		object.setInteger(33);
		object.save();
		new DbTableRowFlagsAsserter(object).assertNone();

		// test after update
		object.setInteger(44);
		object.save();
		new DbTableRowFlagsAsserter(object).assertNone();
	}

	@Test
	public void testFlagsAfterSaveIfNecessary() {

		DbTestObject object = insertObject("foo");
		object.setString("foo");
		new DbTableRowFlagsAsserter(object).assertOnlyDirty();

		object.saveIfNecessary();
		new DbTableRowFlagsAsserter(object).assertNone();
	}

	@Test
	public void testFlagsAfterRollbackOfSave() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		object.setString("foo");
		try (DbTransaction transaction = new DbTransaction()) {
			object.save();
			transaction.rollback();
		}
		new DbTableRowFlagsAsserter(object).assertOnlyImpermanentAndDirtyAndDataChanged(DbTestObject.STRING_FIELD);
	}

	@Test
	public void testFlagsAfterChangingAndSavingInNestedTransaction() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		try (DbTransaction transaction1 = new DbTransaction()) {
			object.setString("foo");
			new DbTableRowFlagsAsserter(object).assertOnlyImpermanentAndDirtyAndDataChanged(DbTestObject.STRING_FIELD);
			try (DbTransaction transaction2 = new DbTransaction()) {
				object.save();
				new DbTableRowFlagsAsserter(object).assertNone();
			}
			new DbTableRowFlagsAsserter(object).assertOnlyImpermanentAndDirtyAndDataChanged(DbTestObject.STRING_FIELD);
		}
		new DbTableRowFlagsAsserter(object).assertOnlyImpermanent();
	}

	// -------------------- saving deleted object -------------------- //

	@Test(expected = DbException.class)
	public void testSavingDeletedObject() {

		DbTestObject object = insertObject("foo");
		object.delete();
		object.save();
	}
}
