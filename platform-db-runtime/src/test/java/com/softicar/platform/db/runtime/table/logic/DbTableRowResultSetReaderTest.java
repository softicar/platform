package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.DbTestObjects;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbTableRowResultSetReaderTest extends AbstractDbTest {

	private final int fooId;
	private final int barId;

	public DbTableRowResultSetReaderTest() {

		this.fooId = DbTestObjects.insertObject("foo");
		this.barId = DbTestObjects.insertObject("bar");
	}

	@Test
	public void testDefaultCacheRetention() {

		// load and cache test objects
		DbTestObject foo = DbTestObject.TABLE.get(fooId);
		DbTestObject bar = DbTestObject.TABLE.get(barId);

		// make 'bar' dirty and simulate concurrent updates
		bar.setString("baz");
		DbTestObjects.updateStringField(fooId, "foofoo");
		DbTestObjects.updateStringField(barId, "barbar");

		// trigger loading of all entries
		DbTestObject.TABLE//
			.createSelect()
			.list();

		// assert 'foo' is updated
		assertEquals("foofoo", foo.getString());
		new DbTableRowFlagsAsserter(foo).assertNone();

		// assert changes to 'bar' are retained
		assertEquals("baz", bar.getString());
		new DbTableRowFlagsAsserter(bar).assertOnlyDirtyAndDataChanged(DbTestObject.STRING_FIELD);
	}
}
