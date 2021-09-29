package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.statement.DbStatement;
import org.junit.Test;

public class DbRecordResultSetReaderTest extends AbstractDbRecordTest {

	@Test
	public void testGetValue() {

		insertRecord("foo", 33, "bar");

		// load record
		try (DbResultSet resultSet = new DbStatement()//
			.addText("SELECT %s, %s, %s FROM %s", DbTestRecord.NAME, DbTestRecord.INDEX, DbTestRecord.ADDRESS, DbTestRecord.TABLE)
			.executeQuery()) {
			assertTrue(resultSet.next());

			// check record
			DbTestRecord record = DbTestRecord.TABLE.getValue(resultSet, 1);
			assertEquals("foo", record.getName());
			assertEquals(33, record.getIndex().intValue());
			assertEquals("bar", record.getAddress());
		}
	}
}
