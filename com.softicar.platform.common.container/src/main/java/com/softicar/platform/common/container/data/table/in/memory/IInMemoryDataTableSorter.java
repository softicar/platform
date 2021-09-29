package com.softicar.platform.common.container.data.table.in.memory;

/**
 * Sorter interface for in-memory data tables.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
interface IInMemoryDataTableSorter<R> {

	int compareRows(R leftRow, R rightRow);
}
