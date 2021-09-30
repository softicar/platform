package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.Arrays;
import org.junit.Test;

public class DbRecordGetterTest extends AbstractDbRecordTest {

	public DbRecordGetterTest() {

		insertRecord("foo", 33, "bar");
	}

	@Test
	public void testGetWithNullKey() {

		assertNull(DbTestRecord.TABLE.get(null));
	}

	@Test
	public void testGetByPrimaryKey() {

		// get correct record
		DbTestRecord record = DbTestRecord.TABLE.get(new Tuple2<>("foo", 33));
		assertNotNull(record);
		assertEquals("foo", record.getName());
		assertEquals(33, record.getIndex().intValue());
		assertEquals("bar", record.getAddress());

		// get non-existing record
		assertSame(record, DbTestRecord.TABLE.get(new Tuple2<>("foo", 33)));
		assertNull(DbTestRecord.TABLE.get(new Tuple2<>("moo", 33)));
		assertNull(DbTestRecord.TABLE.get(new Tuple2<>("foo", 17)));

		// update table row in database
		new DbStatement()//
			.addText("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", DbTestRecord.TABLE, DbTestRecord.ADDRESS, DbTestRecord.NAME, DbTestRecord.INDEX)
			.addParameters(Arrays.asList("baz", "foo", 33))
			.executeUpdate();

		// get record again
		assertEquals("bar", DbTestRecord.TABLE.get(new Tuple2<>("foo", 33)).getAddress());
		record.invalidate();
		assertEquals("baz", DbTestRecord.TABLE.get(new Tuple2<>("foo", 33)).getAddress());
	}

	@Test
	public void testGettingDeletedRecord() {

		insertRecord("foo", 18, "bar");

		DbTestRecord record = DbTestRecord.TABLE.get(new Tuple2<>("foo", 18));
		record.delete();

		assertTrue(record.impermanent());
		assertSame(record, DbTestRecord.TABLE.get(new Tuple2<>("foo", 18)));
	}
}
