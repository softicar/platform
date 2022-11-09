package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.object.DbTestObjectTable;
import com.softicar.platform.db.runtime.table.logic.save.DbTableRowSaver;
import com.softicar.platform.db.runtime.table.row.IDbBasicTableRow;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.Test;

public class DbTableRowCacheRefresherTest extends AbstractDbTest {

	private static final DbTestObjectTable TABLE = DbTestObject.TABLE;
	private static final int ROW_A_VALUE = 10;
	private static final int ROW_B_VALUE = 20;
	private static final int ROW_C_VALUE = 30;
	private static final int ROW_X_VALUE = 40;
	private static final int ROW_Y_VALUE = 50;
	private static final int ROW_Z_VALUE = 60;

	private final CacheAsserter cacheAsserter;
	private final RowAsserter rowAsserter;
	private final DbTestObject rowA;
	private final DbTestObject rowB;
	private final DbTestObject rowC;
	private final DbTestObject rowX;
	private final DbTestObject rowY;
	private final DbTestObject rowZ;

	public DbTableRowCacheRefresherTest() {

		this.cacheAsserter = new CacheAsserter();
		this.rowAsserter = new RowAsserter();

		// setup: insert and invalidate rows
		this.rowA = createRow(ROW_A_VALUE);
		this.rowB = createRow(ROW_B_VALUE);
		this.rowC = createRow(ROW_C_VALUE);
		saveAll(rowA, rowB, rowC);
		invalidateCache();

		// setup: insert further rows, evict them from the cache, and create stubs
		var rowX = createRow(ROW_X_VALUE);
		var rowY = createRow(ROW_Y_VALUE);
		var rowZ = createRow(ROW_Z_VALUE);
		saveAll(rowX, rowY, rowZ);

		var rowXid = rowX.getId();
		var rowYid = rowY.getId();
		var rowZid = rowZ.getId();
		removeAllFromCache(rowXid, rowYid, rowZid);

		this.rowX = TABLE.getStub(rowXid);
		this.rowY = TABLE.getStub(rowYid);
		this.rowZ = TABLE.getStub(rowZid);
	}

	@Test
	public void testRefreshWithInvalidatedRow() {

		var refresher = new DbTableRowCacheRefresher<>(TABLE);

		// validate setup
		cacheAsserter//
			.assertRowCount(6)
			.assertRefreshedRowCount(0);
		rowAsserter//
			.assertInvalidated(rowA, rowB, rowC)
			.assertStub(rowX, rowY, rowZ);

		// execute
		refresher.refresh(Set.of(rowB));

		// assert results
		cacheAsserter//
			.assertRowCount(6)
			.assertNoInvalidatedRows()
			.assertNoStubRows();
		rowAsserter.assertRefreshed(rowB);
	}

	@Test
	public void testRefreshWithInvalidatedRowAndCustomBurstSizeCalculator() {

		final int burstSize = 4;
		var calculator = new DbTableRowCacheBurstSizeCalculator().setMinBurstSize(burstSize);
		var refresher = new DbTableRowCacheRefresher<>(TABLE, calculator);

		// validate setup
		cacheAsserter//
			.assertRowCount(6)
			.assertRefreshedRowCount(0);
		rowAsserter//
			.assertInvalidated(rowA, rowB, rowC)
			.assertStub(rowX, rowY, rowZ);

		// execute
		refresher.refresh(Set.of(rowB));

		// assert results
		cacheAsserter//
			.assertRowCount(6)
			.assertRefreshedRowCount(burstSize);
		rowAsserter.assertRefreshed(rowB);
	}

	@Test
	public void testRefreshWithStubRow() {

		var refresher = new DbTableRowCacheRefresher<>(TABLE);

		// validate setup
		cacheAsserter//
			.assertRowCount(6)
			.assertRefreshedRowCount(0);
		rowAsserter//
			.assertInvalidated(rowA, rowB, rowC)
			.assertStub(rowX, rowY, rowZ);

		// execute
		refresher.refresh(Set.of(rowY));

		// assert results
		cacheAsserter//
			.assertRowCount(6)
			.assertNoInvalidatedRows()
			.assertNoStubRows();
		rowAsserter.assertRefreshed(rowY);
	}

	@Test
	public void testRefreshWithStubRowAndCustomBurstSizeCalculator() {

		final int burstSize = 4;
		var calculator = new DbTableRowCacheBurstSizeCalculator().setMinBurstSize(burstSize);
		var refresher = new DbTableRowCacheRefresher<>(TABLE, calculator);

		// validate setup
		cacheAsserter//
			.assertRowCount(6)
			.assertRefreshedRowCount(0);
		rowAsserter//
			.assertInvalidated(rowA, rowB, rowC)
			.assertStub(rowX, rowY, rowZ);

		// execute
		refresher.refresh(Set.of(rowY));

		// assert results
		cacheAsserter//
			.assertRowCount(6)
			.assertRefreshedRowCount(burstSize);
		rowAsserter.assertRefreshed(rowY);
	}

	// -------------------------------- private -------------------------------- //

	private DbTestObject createRow(int value) {

		var object = new DbTestObject();
		object.setInteger(value);
		return object;
	}

	private void saveAll(DbTestObject...rows) {

		new DbTableRowSaver<>(TABLE).addRows(List.of(rows)).save();
	}

	private void invalidateCache() {

		TABLE.getCache().invalidateAll();
	}

	private void removeAllFromCache(Integer...ids) {

		int cacheSizeBefore = TABLE.getCache().getAllValues().size();
		List.of(ids).forEach(TABLE.getCache()::remove);
		cacheAsserter.assertRowCount(cacheSizeBefore - ids.length);
	}

	private class RowAsserter {

		public RowAsserter assertInvalidated(IDbBasicTableRow<?>...rows) {

			List.of(rows).stream().filter(row -> !row.invalidated()).findAny().ifPresent(row -> {
				throw new AssertionError("Expected an invalidated row but encountered a refreshed row: %s".formatted(row));
			});
			return this;
		}

		public RowAsserter assertStub(IDbBasicTableRow<?>...rows) {

			List.of(rows).stream().filter(row -> !row.stub()).findAny().ifPresent(row -> {
				throw new AssertionError("Expected a stub row but encountered a refreshed row: %s".formatted(row));
			});
			return this;
		}

		public RowAsserter assertRefreshed(IDbBasicTableRow<?>...rows) {

			List.of(rows).stream().filter(row -> row.invalidated() || row.stub()).findAny().ifPresent(row -> {
				throw new AssertionError("Expected a refreshed row but encountered an invalidated or stub row: %s".formatted(row));
			});
			return this;
		}
	}

	private class CacheAsserter {

		public CacheAsserter assertRowCount(int count) {

			assertEquals(count, TABLE.getCache().getAllValues().size());
			return this;
		}

		public CacheAsserter assertRefreshedRowCount(int count) {

			assertEquals(count, streamRefreshedRows().count());
			return this;
		}

		public CacheAsserter assertNoInvalidatedRows() {

			streamInvalidatedRows().findAny().ifPresent(row -> {
				throw new AssertionError("Expected a refreshed row but encountered an invalidated row: %s".formatted(row));
			});
			return this;
		}

		public CacheAsserter assertNoStubRows() {

			streamStubRows().findAny().ifPresent(row -> {
				throw new AssertionError("Expected a refreshed row but encountered a stub row: %s".formatted(row));
			});
			return this;
		}

		private Stream<DbTestObject> streamInvalidatedRows() {

			return TABLE.getCache().getAllValues().stream().filter(row -> row.invalidated());
		}

		private Stream<DbTestObject> streamStubRows() {

			return TABLE.getCache().getAllValues().stream().filter(row -> row.stub());
		}

		private Stream<DbTestObject> streamRefreshedRows() {

			return TABLE.getCache().getAllValues().stream().filter(row -> !row.invalidated() && !row.stub());
		}
	}
}
