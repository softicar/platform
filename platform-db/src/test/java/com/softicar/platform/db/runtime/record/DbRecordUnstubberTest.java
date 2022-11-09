package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DbRecordUnstubberTest extends AbstractDbRecordTest {

	@Test
	public void testUnstubbing() {

		insertRecord("foo", 33, "bar");

		List<DbTestRecord> records = new ArrayList<>();
		records.add(DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33)));
		records.add(DbTestRecord.TABLE.getStub(new Tuple2<>("moo", 33)));

		assertTrue(records.get(0).stub());
		assertTrue(records.get(1).stub());

		DbTestRecord.TABLE.refreshAll(records);

		// check first record
		assertFalse(records.get(0).stub());
		assertFalse(records.get(0).impermanent());
		assertEquals("bar", records.get(0).getAddress());

		// check second record
		assertFalse(records.get(1).stub());
		assertTrue(records.get(1).impermanent());
	}
}
