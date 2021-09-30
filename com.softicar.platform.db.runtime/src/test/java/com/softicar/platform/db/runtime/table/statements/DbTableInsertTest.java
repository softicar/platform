package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.runtime.object.DbIdGeneratorTestObject;
import com.softicar.platform.db.runtime.object.DbTinyTestObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.List;
import org.junit.Test;

public class DbTableInsertTest extends AbstractDbTest {

	@Test
	public void testExecuteMultiInsertWithH2() {

		List<Integer> ids = DbTinyTestObject.TABLE//
			.createInsert()
			.set(DbTinyTestObject.STRING_FIELD, "foo")
			.nextRow()
			.set(DbTinyTestObject.STRING_FIELD, "bar")
			.nextRow()
			.set(DbTinyTestObject.STRING_FIELD, "baz")
			.executeMultiInsert();

		assertEquals(3, ids.size());

		List<DbTinyTestObject> objects = DbTinyTestObject.TABLE.createSelect().list();
		assertEquals(objects.get(0).getId(), ids.get(0));
		assertEquals(objects.get(1).getId(), ids.get(1));
		assertEquals(objects.get(2).getId(), ids.get(2));
	}

	@Test
	public void testExecuteMultiInsertWithH2WithoutIdGeneration() {

		List<Integer> ids = DbTinyTestObject.TABLE//
			.createInsert()
			.set(DbTinyTestObject.ID_FIELD, 1)
			.set(DbTinyTestObject.STRING_FIELD, "foo")
			.nextRow()
			.set(DbTinyTestObject.ID_FIELD, 2)
			.set(DbTinyTestObject.STRING_FIELD, "bar")
			.executeMultiInsert();

		assertEquals(0, ids.size());

		List<DbTinyTestObject> objects = DbTinyTestObject.TABLE.createSelect().list();
		assertEquals(2, objects.size());
		assertEquals(1, objects.get(0).getId());
		assertEquals(2, objects.get(1).getId());
	}

	@Test
	public void testExecuteInsertWithIdGeneratorTable() {

		setAutoIncrementSupplier(() -> 123);

		int generatedId = DbIdGeneratorTestObject.TABLE.createInsert().execute();

		assertEquals(123, generatedId);
	}

	@Test
	public void testExecuteInsertWithIdGeneratorTableAndConsecutiveInsertions() {

		setAutoIncrementSupplier(() -> 123);

		DbIdGeneratorTestObject.TABLE.createInsert().execute();
		DbIdGeneratorTestObject.TABLE.createInsert().execute();
		int generatedId = DbIdGeneratorTestObject.TABLE.createInsert().execute();

		assertEquals(125, generatedId);
	}
}
