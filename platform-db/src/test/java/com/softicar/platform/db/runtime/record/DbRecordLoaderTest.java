package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class DbRecordLoaderTest extends AbstractDbRecordTest {

	public DbRecordLoaderTest() {

		insertRecord("foo", 33, "bar");
	}

	@Test
	public void testLoadWithNullKey() {

		assertNull(DbTestRecord.TABLE.load(null, null));
	}

	@Test
	public void testLoadByPrimaryKey() {

		// load correct record
		DbTestRecord record = DbTestRecord.TABLE.load(new Tuple2<>("foo", 33), null);
		assertNotNull(record);
		assertEquals("foo", record.getName());
		assertEquals(33, record.getIndex().intValue());
		assertEquals("bar", record.getAddress());

		// load non-existing record
		assertSame(record, DbTestRecord.TABLE.load(new Tuple2<>("foo", 33), null));
		assertNull(DbTestRecord.TABLE.get(new Tuple2<>("moo", 33)));
		assertNull(DbTestRecord.TABLE.get(new Tuple2<>("foo", 17)));

		// update table row in database
		new DbStatement()//
			.addText("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", DbTestRecord.TABLE, DbTestRecord.ADDRESS, DbTestRecord.NAME, DbTestRecord.INDEX)
			.addParameters(Arrays.asList("baz", "foo", 33))
			.executeUpdate();

		// load record again
		DbTestRecord record2 = DbTestRecord.TABLE.load(new Tuple2<>("foo", 33), null);
		assertSame(record, record2);
		assertEquals("baz", record2.getAddress());
	}

	@Test
	public void testLoadingOfRecordMap() {

		List<Tuple2<String, Integer>> primaryKeys = Arrays.asList(new Tuple2<>("foo", 16), new Tuple2<>("foo", 33));
		Map<Tuple2<String, Integer>, DbTestRecord> map = DbTestRecord.TABLE.getAllAsMap(primaryKeys);

		assertEquals(1, map.size());
		DbTestRecord record = map.values().iterator().next();
		assertEquals("foo", record.getName());
		assertEquals(33, record.getIndex().intValue());
	}

	@Test
	public void testLoadingOfRecordList() {

		List<Tuple2<String, Integer>> primaryKeys = Arrays.asList(new Tuple2<>("foo", 16), new Tuple2<>("foo", 33));
		List<DbTestRecord> list = DbTestRecord.TABLE.getAll(primaryKeys);

		assertEquals(1, list.size());
		assertEquals("foo", list.get(0).getName());
		assertEquals(33, list.get(0).getIndex().intValue());
	}
}
