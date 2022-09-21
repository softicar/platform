package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbObjectRecursiveStubCreationTest extends AbstractDbTest {

	private static final Integer DEFAULT_STUB_ID = 31;

	@Test
	public void test() {

		// create initialized object
		StubTestObject testObject = new StubTestObject();

		// check that foreign field is set correctly
		StubTestObject foreignObject = StubTestObject.FOREIGN_FIELD.getValue(testObject);
		assertNotNull(foreignObject);
		assertTrue(foreignObject.stub());
		assertEquals(DEFAULT_STUB_ID, foreignObject.getId());

		// check that foreign field of foreign field is null
		assertNull(StubTestObject.FOREIGN_FIELD.getValue(foreignObject));
	}

	private static class StubTestObject extends AbstractDbObject<StubTestObject> {

		public static final DbObjectTableBuilder<StubTestObject, StubTestObject> BUILDER =
				new DbObjectTableBuilder<>("db", "table", StubTestObject::new, StubTestObject.class);
		public static final IDbIdField<StubTestObject> ID_FIELD = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
		public static final IDbForeignField<StubTestObject, StubTestObject> FOREIGN_FIELD =
				BUILDER.addForeignField("fk", o -> o.fk, (o, v) -> o.fk = v, StubTestObject.ID_FIELD).setDefault(DEFAULT_STUB_ID);
		public static final DbObjectTable<StubTestObject> TABLE = new DbObjectTable<>(BUILDER);
		private Integer id;
		private StubTestObject fk;

		@Override
		public DbObjectTable<StubTestObject> table() {

			return TABLE;
		}
	}
}
