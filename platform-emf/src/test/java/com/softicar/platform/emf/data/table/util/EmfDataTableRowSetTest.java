package com.softicar.platform.emf.data.table.util;

import com.softicar.platform.emf.data.table.TestDataTable;
import com.softicar.platform.emf.data.table.TestDataTableRow;
import org.junit.Assert;
import org.junit.Test;

public class EmfDataTableRowSetTest extends Assert {

	private final TestDataTable table;
	private final TestDataTableRow nullRow;
	private final TestDataTableRow rowA;
	private final TestDataTableRow rowB;
	private final EmfDataTableRowSet<TestDataTableRow> rowSet;

	public EmfDataTableRowSetTest() {

		this.table = new TestDataTable();
		this.rowSet = new EmfDataTableRowSet<>(table.getTableColumns());

		this.nullRow = new TestDataTableRow();
		this.rowA = new TestDataTableRow().setString("A");
		this.rowB = new TestDataTableRow().setString("B");
	}

	@Test
	public void testContainsRow() {

		rowSet.addRow(rowA);

		assertFalse(rowSet.containsRow(nullRow));
		assertTrue(rowSet.containsRow(rowA));
		assertFalse(rowSet.containsRow(rowB));
	}

	@Test
	public void testContainsRowWithEquivalentRows() {

		rowSet.addRow(rowA);

		TestDataTableRow rowA2 = new TestDataTableRow().setString("A");
		assertTrue(rowSet.containsRow(rowA));
		assertTrue(rowSet.containsRow(rowA2));
		assertFalse(rowSet.containsRow(rowB));
	}

	@Test
	public void testContainsRowWithEmptySet() {

		assertFalse(rowSet.containsRow(nullRow));
		assertFalse(rowSet.containsRow(rowA));
		assertFalse(rowSet.containsRow(rowB));
	}
}
