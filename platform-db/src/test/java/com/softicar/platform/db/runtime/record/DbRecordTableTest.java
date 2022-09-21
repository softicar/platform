package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;
import org.junit.Test;

public class DbRecordTableTest extends AbstractDbRecordTest {

	@Test
	public void testTableCreationStatement() {

		DbRuntimeTableStructure structure = new DbRuntimeTableStructure(DbTestRecord.TABLE);
		String statement = new DbTableStructureSqlGenerator(structure).getCreateTableStatement();
		assertTrue(statement.contains("`name` VARCHAR"));
		assertTrue(statement.contains("`index` INT"));
		assertTrue(statement.contains("PRIMARY KEY (`name`, `index`)"));
	}

	@Test
	public void testGetOrCreate() {

		insertRecord("foo", 33, "bar");

		DbTestRecord record = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("foo", 33));

		assertNotNull(record);
		assertEquals("bar", record.getAddress());
		new DbTableRowFlagsAsserter(record).assertNone();
	}

	@Test
	public void testGetOrCreateWithNonExistingRecord() {

		Tuple2<String, Integer> key = new Tuple2<>("foo", 16);

		DbTestRecord record1 = DbTestRecord.TABLE.getOrCreate(key);
		DbTestRecord record2 = DbTestRecord.TABLE.getOrCreate(key);

		assertSame(record1, record2);
	}

	@Test(expected = DbException.class)
	public void testGetOrCreateWithInvalidPrimaryKey() {

		DbTestRecord.TABLE.getOrCreate(new Tuple2<>("foo", null));
	}

	@Test
	public void testGetOrCreateWithNonExistingInvalidatedRecord() {

		Tuple2<String, Integer> key = new Tuple2<>("foo", 16);

		DbTestRecord record1 = DbTestRecord.TABLE.getOrCreate(key);
		boolean reloadResult = record1.reload();
		DbTestRecord record2 = DbTestRecord.TABLE.getOrCreate(key);

		assertFalse(reloadResult);
		assertSame(record1, record2);
		new DbTableRowFlagsAsserter(record1).assertOnlyImpermanentAndInvalidated();
	}
}
