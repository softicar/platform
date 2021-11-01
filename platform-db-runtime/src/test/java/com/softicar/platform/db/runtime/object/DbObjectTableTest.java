package com.softicar.platform.db.runtime.object;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.field.IDbPrimitiveField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;
import org.junit.Test;

public class DbObjectTableTest extends AbstractDbTest {

	@Test
	public void testTableCreationStatement() {

		DbRuntimeTableStructure structure = new DbRuntimeTableStructure(DbTestObject.TABLE);
		String statement = new DbTableStructureSqlGenerator(structure).getCreateTableStatement();
		assertTrue(statement.contains("`id` INT NOT NULL AUTO_INCREMENT"));
		assertTrue(statement.contains("PRIMARY KEY (`id`)"));
	}

	@Test
	public void testPrimaryKey() {

		IDbTableKey<DbTestObject, Integer> primaryKey = DbTestObject.TABLE.getPrimaryKey();
		assertNotNull(primaryKey);
		assertSame(DbTestObject.ID_FIELD, primaryKey.getIdField());
	}

	@Test
	public void testAddingIdField() {

		// check without adding a field
		DbObjectTableBuilder<DbTestObject, DbTestObject> builder = new DbObjectTableBuilder<>("db", "foo", null, DbTestObject.class);
		DbObjectTable<DbTestObject> table = new DbObjectTable<>(builder);

		// check that table is empty
		assertException(IllegalStateException.class, () -> table.getIdField());
		assertEquals(0, table.getAllFields().size());

		// create a non-ID field
		builder.addIntegerField("id", null, null);
		assertException(IllegalStateException.class, () -> table.getIdField());
		assertEquals(1, table.getAllFields().size());

		// create the ID field
		IDbPrimitiveField<DbTestObject, Integer> idField = builder.addIdField("id", null, null);
		assertNotNull(table.getIdField());
		assertSame(idField, table.getIdField());
		assertEquals(2, table.getAllFields().size());

		// create additional ID field
		try {
			builder.addIdField("id2", null, null);
			fail("expected exception");
		} catch (IllegalStateException exception) {
			// expected exception
			DevNull.swallow(exception);
		}
	}

	private void assertException(Class<IllegalStateException> expectedExceptionClass, ISideEffect sideEffect) {

		try {
			sideEffect.execute();
			fail("exception expected");
		} catch (Exception exception) {
			assertSame(expectedExceptionClass, exception.getClass());
		}
	}

	private static interface ISideEffect {

		void execute();
	}
}
