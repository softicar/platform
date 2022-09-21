package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.DbTestObjects;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbTableRowFieldWriterTest extends AbstractDbTest {

	private static final String FOO = "foo";
	private static final String BAR = "bar";
	private static final String BAZ = "baz";
	private final DbTestObject object;
	private final DbTableRowFlagsAsserter flagsAsserter;

	public DbTableRowFieldWriterTest() {

		this.object = new DbTestObject().save();
		this.flagsAsserter = new DbTableRowFlagsAsserter(object);

		// set value directly
		DbTestObject.STRING_FIELD.setValueDirectly(object, FOO);
	}

	/**
	 * Verifies that the value is actually changed and that the data-changed
	 * flags are enabled when a value is written.
	 */
	@Test
	public void testWrite() {

		new DbTableRowFieldWriter<>(object, DbTestObject.STRING_FIELD).write(BAR);

		assertEquals(BAR, object.getString());
		flagsAsserter.assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
	}

	/**
	 * Verifies that the object is initialized even though only the primary key
	 * field is written to.
	 */
	@Test
	public void testWriteWithPkFieldOfUninitializedObject() {

		DbTestObject uninitializedObject = new DbTestObject();

		new DbTableRowFieldWriter<>(uninitializedObject, DbTestObject.ID_FIELD).write(123);

		assertEquals(123, uninitializedObject.getId());
		flagsAsserter.assertNone();
	}

	/**
	 * Verifies that the data-changed flags are not enabled if an equal value is
	 * written.
	 */
	@Test
	public void testWriteWithEqualValue() {

		new DbTableRowFieldWriter<>(object, DbTestObject.STRING_FIELD).write(FOO);

		assertEquals(FOO, object.getString());
		flagsAsserter.assertOnlyDirty();
	}

	/**
	 * Verifies that a backup of the modified object is created.
	 */
	@Test
	public void testWriteWithRollback() {

		try (DbTransaction transaction = new DbTransaction()) {
			new DbTableRowFieldWriter<>(object, DbTestObject.STRING_FIELD).write(BAR);

			assertEquals(BAR, object.getString());
			new DbTableRowFlagsAsserter(object).assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
			transaction.rollback();
		}

		assertEquals(FOO, object.getString());
		new DbTableRowFlagsAsserter(object).assertNone();
	}

	/**
	 * Verifies that a stub object is un-stubbed before the field is changed.
	 */
	@Test
	public void testWriteWithStubObject() {

		// this test ensures that the writer un-stubs a stub object
		DbTestObject stubObject = createStubObject();

		new DbTableRowFieldWriter<>(stubObject, DbTestObject.STRING_FIELD).write(BAR);

		assertEquals(BAR, stubObject.getString());
		new DbTableRowFlagsAsserter(stubObject).assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
	}

	/**
	 * Verifies that an invalidated object is reloaded before the field is
	 * changed.
	 */
	@Test
	public void testWriteWithInvalidatedObject() {

		// this test ensures that the writer reloads an invalidated object
		DbTestObject object = createInvalidatedObject();
		DbTestObjects.updateStringField(object.getId(), BAR);

		new DbTableRowFieldWriter<>(object, DbTestObject.STRING_FIELD).write(BAZ);

		assertEquals(BAZ, object.getString());
		new DbTableRowFlagsAsserter(object).assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
	}

	/**
	 * Verifies that an invalidated object is reloaded before the field is
	 * changed and the new value is compared to the reloaded value.
	 */
	@Test
	public void testWriteWithInvalidatedObjectWithEqualValue() {

		// this test ensures that the writer reloads an invalidated object
		DbTestObject object = createInvalidatedObject();
		DbTestObjects.updateStringField(object.getId(), BAR);

		new DbTableRowFieldWriter<>(object, DbTestObject.STRING_FIELD).write(BAR);

		assertEquals(BAR, object.getString());
		new DbTableRowFlagsAsserter(object).assertOnlyDirty();
	}

	@Test
	public void testWriteWithDataFieldOfInvalidatedAndImpermanentObject() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		object.invalidate();

		new DbTableRowFieldWriter<>(object, DbTestObject.STRING_FIELD).write("bar");

		assertEquals("bar", object.getString());
		assertTrue(object.impermanent());
		assertTrue(object.invalidated());
	}

	// ------------------------------ private ------------------------------ //

	/**
	 * Creates a valid stub object, that is, the object really exists but was
	 * not loaded yet.
	 *
	 * @return a valid stub object
	 */
	private DbTestObject createStubObject() {

		DbTestObject stubObject = DbTestObject.TABLE.getStub(DbTestObjects.insertObject(FOO));
		new DbTableRowFlagsAsserter(stubObject).assertOnlyStub();
		return stubObject;
	}

	/**
	 * Creates a valid object with the invalidated flag enabled.
	 *
	 * @return the invalidated object
	 */
	private DbTestObject createInvalidatedObject() {

		DbTestObject object = DbTestObject.TABLE.get(DbTestObjects.insertObject(FOO));
		object.invalidate();
		new DbTableRowFlagsAsserter(object).assertOnlyInvalidated();
		return object;
	}
}
