package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

public class DbRecordStubberTest extends AbstractDbRecordTest {

	public DbRecordStubberTest() {

		insertRecord("foo", 33, "bar");
	}

	@Test
	public void testCreationOfStubOfExistingRecord() {

		DbTestRecord stub = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33));
		assertNotNull(stub);
		assertEquals("foo", stub.getName());
		assertEquals(33, stub.getIndex().intValue());
		assertEquals("bar", stub.getAddress());
	}

	@Test
	public void testCreationOfStubOfNonExistingRecord() {

		DbTestRecord stub = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 16));
		assertNotNull(stub);
		assertEquals("foo", stub.getName());
		assertEquals(16, stub.getIndex().intValue());
		assertNull(stub.getAddress());
	}

	@Test
	public void testLoadingWithNullKey() {

		assertNull(DbTestRecord.TABLE.getStub(null));
	}

	@Test
	public void testIdentityOfStubRecords() {

		DbTestRecord stubRecord1 = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33));
		DbTestRecord stubRecord2 = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33));
		assertSame(stubRecord1, stubRecord2);

		DbTestRecord stubRecord3 = DbTestRecord.TABLE.getStub(new Tuple2<>("moo", 33));
		assertNotSame(stubRecord1, stubRecord3);
	}

	@Test
	public void testImplicitLoadingOfStubWhenReadingField() {

		DbTestRecord stub = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33));
		assertTrue(stub.stub());
		assertEquals("bar", stub.getAddress());
		assertFalse(stub.stub());
	}

	@Test
	public void testImplicitLoadingOfStubWhenWritingFieldWithEqualValue() {

		DbTestRecord stub = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33));
		assertTrue(stub.stub());
		stub.setAddress("bar");
		new DbTableRowFlagsAsserter(stub).assertOnlyDirty();
	}

	@Test
	public void testImplicitLoadingOfStubWhenWritingFieldWithDifferentValue() {

		DbTestRecord stub = DbTestRecord.TABLE.getStub(new Tuple2<>("foo", 33));
		assertTrue(stub.stub());
		stub.setAddress("baz");
		new DbTableRowFlagsAsserter(stub).assertOnlyDirtyAndDataChanged(DbTestRecord.ADDRESS);
	}
}
