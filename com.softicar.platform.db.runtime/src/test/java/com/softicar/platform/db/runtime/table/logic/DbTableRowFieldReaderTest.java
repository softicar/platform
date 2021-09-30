package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.sub.DbTestBaseObject;
import com.softicar.platform.db.runtime.object.sub.DbTestSubObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbTableRowFieldReaderTest extends AbstractDbTest {

	private static final int ID = 33;

	public DbTableRowFieldReaderTest() {

		DbTestObject.TABLE//
			.createInsert()
			.set(DbTestObject.ID_FIELD, ID)
			.set(DbTestObject.STRING_FIELD, "foo")
			.executeWithoutIdGeneration();
	}

	@Test
	public void testReadWithPkFieldOfUninitializedObject() {

		DbTestSubObject subObject = new DbTestSubObject();
		assertNull(DbTestSubObject.BASE_OBJECT.getValueDirectly(subObject));

		DbTestBaseObject baseObject = new DbTableRowFieldReader<>(subObject, DbTestSubObject.BASE_OBJECT).read();

		assertNotNull(baseObject);
	}

	@Test
	public void testReadWithPkFieldOfStubObject() {

		DbTestObject object = DbTestObject.TABLE.getStub(ID);

		Integer id = new DbTableRowFieldReader<>(object, DbTestObject.ID_FIELD).read();

		assertEquals(ID, id);
		assertTrue(object.stub());
	}

	@Test
	public void testReadWithDataFieldOfStubObject() {

		DbTestObject object = DbTestObject.TABLE.getStub(ID);

		String string = new DbTableRowFieldReader<>(object, DbTestObject.STRING_FIELD).read();

		assertEquals("foo", string);
		assertFalse(object.stub());
	}

	@Test
	public void testReadWithPkFieldOfInvalidatedObject() {

		DbTestObject object = DbTestObject.TABLE.get(ID);
		updateStringField("bar");
		object.invalidate();

		Integer id = new DbTableRowFieldReader<>(object, DbTestObject.ID_FIELD).read();

		assertEquals(ID, id);
		assertTrue(object.invalidated());
	}

	@Test
	public void testReadWithDataFieldOfInvalidatedObject() {

		DbTestObject object = DbTestObject.TABLE.get(ID);
		updateStringField("bar");
		object.invalidate();

		String string = new DbTableRowFieldReader<>(object, DbTestObject.STRING_FIELD).read();

		assertEquals("bar", string);
		assertFalse(object.invalidated());
	}

	@Test
	public void testReadWithDataFieldOfInvalidatedAndImpermanentObject() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		object.setString("bar");
		object.invalidate();

		String string = new DbTableRowFieldReader<>(object, DbTestObject.STRING_FIELD).read();

		assertEquals("bar", string);
		assertTrue(object.impermanent());
		assertTrue(object.invalidated());
	}

	private void updateStringField(String string) {

		DbTestObject.TABLE//
			.createUpdate()
			.set(DbTestObject.STRING_FIELD, string)
			.where(DbTestObject.ID_FIELD.isEqual(ID))
			.execute();
	}
}
