package com.softicar.platform.common.container.data.table.in.memory;

import java.util.List;

/**
 * Supplier interface for the rows of an {@link AbstractInMemoryDataTable}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
interface IInMemoryRowProvider<R> {

	/**
	 * Instructs the {@link IInMemoryRowProvider} to reset all internal caches.
	 */
	void invalidateCaches();

	/**
	 * Notifies the {@link IInMemoryRowProvider} about changed filtering.
	 */
	void onFilterChanged();

	/**
	 * Notifies the {@link IInMemoryRowProvider} about changed sorting.
	 */
	void onSorterChanged();

	/**
	 * Returns all rows in the given limits after filtering and sorting.
	 *
	 * @param offset
	 *            the 0-based index of the first row
	 * @param limit
	 *            the maximum number of rows to return
	 * @return the filtered and sorted rows in the given limits
	 */
	List<R> getFilteredAndSortedRows(int offset, int limit);

	/**
	 * Returns the total number of rows after filtering.
	 *
	 * @return the total row count
	 */
	int getRowCount();
}
