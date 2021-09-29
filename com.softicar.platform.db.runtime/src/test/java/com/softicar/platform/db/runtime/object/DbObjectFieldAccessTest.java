package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

public class DbObjectFieldAccessTest extends AbstractDbObjectTest {

	private final DbTestObject initialObject;
	private final Integer objectId;

	public DbObjectFieldAccessTest() {

		this.initialObject = DbTestObject.TABLE.createObject();
		this.initialObject.setString("foo");
		this.initialObject.save();
		this.objectId = this.initialObject.getId();
	}

	// -------------------- data changed flag -------------------- //

	@Test
	public void testFlagsAfterChangingValue() {

		DbTestObject object = DbTestObject.TABLE.createObject().save();
		object.setInteger(333);
		new DbTableRowFlagsAsserter(object).assertOnlyDirtyAndDataChanged(DbTestObject.INTEGER_FIELD);
	}

	@Test
	public void testFlagsAfterSettingEqualValue() {

		DbTestObject object = DbTestObject.TABLE.createObject().save();
		object.setInteger(DbTestObject.DEFAULT_INTEGER_VALUE);
		new DbTableRowFlagsAsserter(object).assertOnlyDirty();
	}

	@Test
	public void testFlagsAfterRollback() {

		DbTestObject object = DbTestObject.TABLE.createObject().save();
		new DbTableRowFlagsAsserter(object).assertNone();

		try (DbTransaction transaction = new DbTransaction()) {
			object.setString("foo");
			new DbTableRowFlagsAsserter(object).assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
			// implicit roll-back
		}

		new DbTableRowFlagsAsserter(object).assertNone();
	}

	// -------------------- stub flag -------------------- //

	@Test
	public void testFlagsAfterWriteAccessOnStub() {

		// insert row
		int id = insertObjectRow("foo");

		// create and check flag
		DbTestObject stub = DbTestObject.TABLE.getStub(id);
		new DbTableRowFlagsAsserter(stub).assertOnlyStub();

		// access and check flag
		stub.setInteger(DbTestObject.INTEGER_FIELD.getDefault() + 1);
		new DbTableRowFlagsAsserter(stub).assertOnlyDirtyAndDataChanged(DbTestObject.INTEGER_FIELD);
	}

	// -------------------- un-stubbing -------------------- //

	@Test
	public void testReadingOfIdFieldDoesNotUnstub() {

		DbTestObject object = DbTestObject.TABLE.getStub(NON_EXISTING_ID);
		Integer id = object.getId();

		assertTrue(object.stub());
		assertEquals(NON_EXISTING_ID, id);
	}

	@Test
	public void testReadingOfDataFieldUnstubs() {

		DbTestObject object = DbTestObject.TABLE.getStub(objectId);
		String value = DbTestObject.STRING_FIELD.getValue(object);

		assertFalse(object.stub());
		assertEquals("foo", value);
	}

	@Test
	public void testWritingOfEqualValueUnstubs() {

		DbTestObject object = DbTestObject.TABLE.getStub(objectId);
		object.setString(DbTestObject.DEFAULT_STRING_VALUE);

		assertFalse(object.stub());
	}

	@Test
	public void testWritingOfDifferentValueUnstubs() {

		DbTestObject object = DbTestObject.TABLE.getStub(objectId);
		object.setString("bar");

		assertFalse(object.stub());
	}
}
