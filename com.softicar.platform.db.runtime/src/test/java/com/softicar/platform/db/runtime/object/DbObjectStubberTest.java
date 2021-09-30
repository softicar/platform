package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import org.junit.Test;

/**
 * Test cases for stub objects.
 *
 * @author Oliver Richers
 */
public class DbObjectStubberTest extends AbstractDbObjectTest {

	@Test
	public void testFlagsOfStubObject() {

		// warning: inlining the variable triggers a java compiler bug in JDK-8.25
		DbTestObject stub = DbTestObject.TABLE.getStub(1);
		new DbTableRowFlagsAsserter(stub).assertOnlyStub();
	}

	@Test
	public void returnsStubEvenIfNoRowExists() {

		DbTestObject stub = DbTestObject.TABLE.getStub(NON_EXISTING_ID);

		assertNotNull(stub);
		new DbTableRowFlagsAsserter(stub).assertOnlyStub();
		assertEquals(stub.getId(), NON_EXISTING_ID);
	}

	@Test
	public void returnsSameStubForSameId() {

		DbTestObject stub1 = DbTestObject.TABLE.getStub(NON_EXISTING_ID);
		DbTestObject stub2 = DbTestObject.TABLE.getStub(NON_EXISTING_ID);

		assertSame(stub1, stub2);
	}

	@Test
	public void testFieldAccessOnNonExistingStubReturnsNull() {

		DbTestObject stub = DbTestObject.TABLE.getStub(NON_EXISTING_ID);

		assertNotNull(DbTestObject.INTEGER_FIELD.getDefault());
		assertNull(stub.getInteger());
	}

	@Test
	public void testToStringOnStub() {

		Integer id = insertTinyObjectRow("foo");
		DbTinyTestObject stub = DbTinyTestObject.TABLE.getStub(id);

		assertNotNull(stub);
		new DbTableRowFlagsAsserter(stub).assertOnlyStub();
		assertEquals(String.format("[id: %d, string: '%s', foreign: null]", id, "foo"), stub.toString());
	}

	@Test(expected = DbException.class)
	public void testSavingOfStubObject() {

		Integer id = insertObjectRow("foo");
		DbTestObject stub = DbTestObject.TABLE.getStub(id);
		stub.save();
	}

	@Test
	public void testSavingOfStubOfExistingObjectAfterAccess() {

		Integer id = insertObjectRow("foo");
		DbTestObject stub = DbTestObject.TABLE.getStub(id);
		stub.setInteger(DbTestObject.INTEGER_FIELD.getDefault());
		stub.save();
	}

	@Test(expected = DbException.class)
	public void testSavingOfStubOfNonExistingObjectAfterAccess() {

		DbTestObject stub = DbTestObject.TABLE.getStub(NON_EXISTING_ID);
		stub.setInteger(12345);
		stub.save();
	}
}
