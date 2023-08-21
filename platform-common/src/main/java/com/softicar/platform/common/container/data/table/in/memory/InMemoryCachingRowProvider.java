package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.cache.Cache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementation of {@link IInMemoryRowProvider} which tries to hold all rows
 * in memory.
 * <p>
 * If the number of rows exceeds a specific limit, this
 * {@link IInMemoryRowProvider} will skip caching.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class InMemoryCachingRowProvider<R> implements IInMemoryRowProvider<R> {

	private static final int DEFAULT_MAX_CACHE_SIZE = 10000;

	private final Supplier<? extends Iterable<R>> rowSupplier;
	private final IInMemoryDataTableFilterList<R> filterList;
	private final IInMemoryDataTableSorter<R> sorterList;
	private final int maxCacheSize;

	private Integer filteredRowCount = null;
	private Cache<Integer, R> rowCache = null;

	public InMemoryCachingRowProvider(Supplier<? extends Iterable<R>> rowSupplier, IInMemoryDataTableFilterList<R> filterList,
			IInMemoryDataTableSorter<R> sorterList) {

		this.rowSupplier = rowSupplier;
		this.filterList = filterList;
		this.sorterList = sorterList;
		this.maxCacheSize = DEFAULT_MAX_CACHE_SIZE;
		resetCache();
	}

	@Override
	public void invalidateCaches() {

		filteredRowCount = null;
		resetCache();
	}

	@Override
	public void onFilterChanged() {

		filteredRowCount = null;
		resetCache();
	}

	@Override
	public void onSorterChanged() {

		resetCache();
	}

	@Override
	public int getRowCount() {

		if (filteredRowCount == null) {
			List<R> filteredAndSortedRows = filterAndSortAllRows();
			putAllToCache(filteredAndSortedRows, 0);
		}
		return filteredRowCount;
	}

	@Override
	public List<R> getFilteredAndSortedRows(int offset, int limit) {

		// handle special values for offset and limit
		offset = offset > 0? offset : 0;
		limit = limit > 0? limit : getRowCount() - offset;

		// try to get rows from cache
		List<R> rowsFromCache = getRowsFromCacheOrNull(offset, limit);
		if (rowsFromCache != null) {
			return rowsFromCache;
		} else {
			List<R> filteredAndSortedRows = filterAndSortAllRows();
			List<R> subList = filteredAndSortedRows.subList(offset, Math.min(offset + limit, filteredAndSortedRows.size()));
			ArrayList<R> rows = new ArrayList<>(subList);
			if (limit <= maxCacheSize) {
				putAllToCache(rows, offset);
			} else {
				// The rows won't fit into the cache anyway -> Give up on caching as long as the max cache size is
				// exceeded.
				resetCache();
			}
			return rows;
		}
	}

	private void putAllToCache(List<R> rows, int offset) {

		int n = Math.min(rows.size(), maxCacheSize);
		for (int i = 0; i < n; i++) {
			rowCache.put(offset + i, rows.get(i));
		}
	}

	private void resetCache() {

		rowCache = new Cache<>(maxCacheSize, InMemoryCachingRowProvider.class.getSimpleName() + " cache");
	}

	private List<R> getRowsFromCacheOrNull(int offset, int limit) {

		List<R> rowsFromCache = new ArrayList<>();
		for (int i = offset; i < offset + limit; i++) {
			R row = rowCache.get(i);
			if (row != null) {
				rowsFromCache.add(row);
			} else {
				return null;
			}
		}
		return rowsFromCache;
	}

	/**
	 * Note: A filter change invalidates the sorting, so stuff must be re-sorted
	 * as well.
	 */
	private List<R> filterAndSortAllRows() {

		List<R> filteredRows = filterRows();
		sortFilteredRows(filteredRows);
		return filteredRows;
	}

	private List<R> filterRows() {

		List<R> filteredRows = new ArrayList<>();
		Iterable<R> rowIterable = rowSupplier.get();
		Objects.requireNonNull(rowIterable);
		if (filterList != null && !filterList.isEmpty()) {
			for (R row: rowIterable) {
				if (row != null && filterList.applyFilter(row)) {
					filteredRows.add(row);
				}
			}
		} else {
			for (R row: rowIterable) {
				if (row != null) {
					filteredRows.add(row);
				}
			}
		}
		filteredRowCount = filteredRows.size();
		return filteredRows;
	}

	private void sortFilteredRows(List<R> filteredRows) {

		if (sorterList != null) {
			Collections.sort(filteredRows, sorterList::compareRows);
		}
	}
}
