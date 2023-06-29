package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.exception.DbException;
import org.junit.Test;

public class DbRecordDeleterTest extends AbstractDbRecordTest {

	private static final Tuple2<String, Integer> KEY = new Tuple2<>("foo", 18);

	@Test(expected = DbException.class)
	public void testDeletionOfImpermanentRecord() {

		// create impermanent record and delete it
		DbTestRecord.TABLE.getOrCreate(KEY).delete();
	}

	@Test
	public void testDeletionOfLoadedRecord() {

		// insert row concurrently, get record and delete it
		insertRecord(KEY, "bar");
		DbTestRecord record = DbTestRecord.TABLE.get(KEY);
		record.delete();

		assertNull(DbTestRecord.TABLE.get(KEY));
		assertTrue(record.impermanent());
	}

	@Test
	public void testDeletionOfConcurrentlyInsertedRecord() {

		// create record, insert row concurrently and delete record
		DbTestRecord record = DbTestRecord.TABLE.getOrCreate(KEY);
		insertRecord(KEY, "bar");
		record.delete();

		assertNull(DbTestRecord.TABLE.get(KEY));
		assertTrue(record.impermanent());
		assertEquals(0, DbTestRecord.TABLE.createSelect().list().size());
	}

	@Test
	public void testDeletingDeletedButRecreatedRecord() {

		// insert row concurrently, get record and delete it
		insertRecord(KEY, "bar");
		DbTestRecord record = DbTestRecord.TABLE.get(KEY);
		record.delete();
		assertNull(DbTestRecord.TABLE.get(KEY));

		// insert row concurrently again and delete record again
		insertRecord(KEY, "bar");
		assertSame(record, DbTestRecord.TABLE.get(KEY));
		record.delete();
		assertNull(DbTestRecord.TABLE.get(KEY));
	}

	@Test
	public void testInvalidationOfDeletedRecord() {

		insertRecord(KEY, "bar");
		DbTestRecord record = DbTestRecord.TABLE.get(KEY);
		assertSame(record, DbTestRecord.TABLE.get(KEY));

		record.delete();
		assertTrue("Record is not invalidated.", record.invalidated());
		assertNull(DbTestRecord.TABLE.get(KEY));
	}
}
