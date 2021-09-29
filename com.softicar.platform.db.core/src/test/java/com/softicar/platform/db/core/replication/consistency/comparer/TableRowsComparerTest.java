package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.db.core.connection.DbConnections;
import org.junit.Test;

@SuppressWarnings("resource")
public class TableRowsComparerTest extends AbstractMasterSlaveComparatorTest {

	private final TableRowsComparer comparator;

	public TableRowsComparerTest() {

		this.comparator = new TableRowsComparer(tableDefinitionA, tableDefinitionB, this::addMismatch);
	}

	@Test
	public void testWithEqualRows() {

		insertInto(A, 1, "foo");
		insertInto(B, 1, "foo");

		comparator.compare(DbConnections.getConnection(), DbConnections.getConnection());

		assertEquals(0, mismatches.size());
	}

	@Test
	public void testWithDifferedPayload() {

		insertInto(A, 1, "foo");
		insertInto(B, 1, "bar");

		comparator.compare(DbConnections.getConnection(), DbConnections.getConnection());

		assertEquals(1, mismatches.size());
		assertRow(mismatches.get(0).getFirst(), "1", "foo");
		assertRow(mismatches.get(0).getSecond(), "1", "bar");
	}

	@Test
	public void testWithPrimaryKeyMismatch() {

		insertInto(A, 1, "foo");
		insertInto(A, 2, "bar");

		insertInto(B, 1, "foo");
		insertInto(B, 3, "bar");

		comparator.compare(DbConnections.getConnection(), DbConnections.getConnection());

		assertEquals(2, mismatches.size());
		assertRow(mismatches.get(0).getFirst(), "2", "bar");
		assertNull(mismatches.get(0).getSecond());
		assertNull(mismatches.get(1).getFirst());
		assertRow(mismatches.get(1).getSecond(), "3", "bar");
	}

	@Test
	public void testWithMissingRowsOnA() {

		insertInto(A, 1, "foo");
		insertInto(B, 1, "foo");
		insertInto(B, 2, "bar");

		comparator.compare(DbConnections.getConnection(), DbConnections.getConnection());

		assertEquals(1, mismatches.size());
		assertNull(mismatches.get(0).getFirst());
		assertRow(mismatches.get(0).getSecond(), "2", "bar");
	}

	@Test
	public void testWithMissingRowsOnB() {

		insertInto(A, 1, "foo");
		insertInto(A, 2, "bar");
		insertInto(B, 1, "foo");

		comparator.compare(DbConnections.getConnection(), DbConnections.getConnection());

		assertEquals(1, mismatches.size());
		assertRow(mismatches.get(0).getFirst(), "2", "bar");
		assertNull(mismatches.get(0).getSecond());
	}
}
