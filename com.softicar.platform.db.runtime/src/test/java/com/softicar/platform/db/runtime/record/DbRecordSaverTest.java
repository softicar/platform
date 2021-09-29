package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.db.sql.Sql;
import org.junit.Test;

public class DbRecordSaverTest extends AbstractDbRecordTest {

	@Test
	public void testSaveWithNewRecord() {

		// insert new record
		DbTestRecord record = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("foo", 18));
		record.setAddress("bar");
		record.save();

		// check data in database
		Tuple3<String, Integer, String> tuple = Sql//
			.from(DbTestRecord.TABLE)
			.select(DbTestRecord.NAME)
			.select(DbTestRecord.INDEX)
			.select(DbTestRecord.ADDRESS)
			.getOne();
		assertNotNull(tuple);
		assertEquals("foo", tuple.get0());
		assertEquals(18, tuple.get1());
		assertEquals("bar", tuple.get2());
	}

	@Test
	public void testSaveWithInvalidatedRecord() {

		// insert row into table
		insertRecord("foo", 18, "bar");

		// get record, change data field, enable invalidated flag and save
		DbTestRecord record = DbTestRecord.TABLE.getOrCreate(new Tuple2<>("foo", 18));
		record.setAddress("baz");
		record.invalidate();
		record.save();

		// check data in database
		String address = Sql//
			.from(DbTestRecord.TABLE)
			.select(DbTestRecord.ADDRESS)
			.where(DbTestRecord.NAME.isEqual("foo"))
			.where(DbTestRecord.INDEX.isEqual(18))
			.getOne();
		assertEquals("baz", address);
	}

	@Test
	public void testSaveWithDeletedRecord() {

		insertRecord("foo", 18, "bar");
		DbTestRecord record = DbTestRecord.TABLE.get(new Tuple2<>("foo", 18));
		record.delete();
		record.save();
	}

	@Test
	public void testSaveWithDegeneratedRecordTable() {

		DbDegeneratedTestRecord record = DbDegeneratedTestRecord.TABLE.getOrCreate(33);

		// this should execute an insert
		record.save();

		// check data in database
		Integer value = Sql//
			.from(DbDegeneratedTestRecord.TABLE)
			.select(DbDegeneratedTestRecord.ID)
			.getOne();
		assertEquals(33, value);

		// this should execute nothing (an update without payload cannot be executed)
		record.save();
	}
}
