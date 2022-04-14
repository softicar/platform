package com.softicar.platform.emf.data.table.util;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.data.table.TestDataTable;
import com.softicar.platform.emf.data.table.TestDataTableRow;
import org.junit.Test;

public class EmfDataTableRowKeyTest extends AbstractTest {

	private final TestDataTable table;

	public EmfDataTableRowKeyTest() {

		this.table = new TestDataTable();
	}

	@Test
	public void testWithNullRows() {

		TestDataTableRow a = new TestDataTableRow();
		TestDataTableRow b = new TestDataTableRow();

		assertKeysAreEqual(a, b);
	}

	@Test
	public void testWithNullRowAndNonNullRow() {

		TestDataTableRow a = new TestDataTableRow();
		TestDataTableRow b = new TestDataTableRow().setInteger(33);

		assertKeysAreNotEqual(a, b);
	}

	@Test
	public void testWithEquivalentRows() {

		TestDataTableRow a = new TestDataTableRow()//
			.setInteger(33)
			.setString("foo");
		TestDataTableRow b = new TestDataTableRow()//
			.setInteger(33)
			.setString("foo");

		assertKeysAreEqual(a, b);
	}

	@Test
	public void testWithNonEquivalentRows() {

		TestDataTableRow a = new TestDataTableRow()//
			.setInteger(34)
			.setString("foo");
		TestDataTableRow b = new TestDataTableRow()//
			.setInteger(33)
			.setString("foo");
		TestDataTableRow c = new TestDataTableRow()//
			.setInteger(33)
			.setString("bar");

		assertKeysAreNotEqual(a, b);
		assertKeysAreNotEqual(a, c);
		assertKeysAreNotEqual(b, c);
	}

	private void assertKeysAreEqual(TestDataTableRow a, TestDataTableRow b) {

		EmfDataTableRowKey<TestDataTableRow> keyA = createKey(a);
		EmfDataTableRowKey<TestDataTableRow> keyB = createKey(b);

		assertTrue(keyA.equals(keyB));
		assertEquals(keyA.hashCode(), keyB.hashCode());
	}

	private void assertKeysAreNotEqual(TestDataTableRow a, TestDataTableRow b) {

		EmfDataTableRowKey<TestDataTableRow> keyA = createKey(a);
		EmfDataTableRowKey<TestDataTableRow> keyB = createKey(b);

		assertFalse(keyA.equals(keyB));

		// Of course, hashCode() is not deterministic, still we want to check the following.
		// If the assertion fails, it is very likely that there is a defect.
		// Otherwise, try to change the example input values above.
		assertNotEquals(keyA.hashCode(), keyB.hashCode());
	}

	private EmfDataTableRowKey<TestDataTableRow> createKey(TestDataTableRow a) {

		return new EmfDataTableRowKey<>(table.getTableColumns(), a);
	}
}
