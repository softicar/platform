package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

public class EmfDataTableLazyRowCacheTest extends AbstractTest {

	private final TestRow row1;
	private final TestRow row2;

	public EmfDataTableLazyRowCacheTest() {

		this.row1 = new TestRow();
		this.row2 = new TestRow();
	}

	@Test
	public void testGetAllRows() {

		RowLoader loader = new RowLoader(row1, row2);
		EmfDataTableLazyRowCache<TestRow> cache = new EmfDataTableLazyRowCache<>(loader::loadRows);

		Collection<TestRow> rows = cache.getAllRows();

		assertTrue(rows.contains(row1));
		assertTrue(rows.contains(row2));
		assertEquals(1, loader.getCounter());
	}

	@Test
	public void testGetAllRowsAfterInvalidation() {

		RowLoader loader = new RowLoader(row1, row2);
		EmfDataTableLazyRowCache<TestRow> cache = new EmfDataTableLazyRowCache<>(loader::loadRows);

		cache.getAllRows();
		cache.getAllRows();
		cache.invalidate();
		cache.getAllRows();
		Collection<TestRow> rows = cache.getAllRows();

		assertTrue(rows.contains(row1));
		assertTrue(rows.contains(row2));
		assertEquals(2, loader.getCounter());
	}

	private class RowLoader {

		private final List<TestRow> rows;
		private int counter;

		public RowLoader(TestRow...rows) {

			this.rows = Arrays.asList(rows);
			this.counter = 0;
		}

		public List<TestRow> loadRows() {

			++counter;
			return rows;
		}

		public int getCounter() {

			return counter;
		}
	}

	private class TestRow {

		// nothing
	}
}
