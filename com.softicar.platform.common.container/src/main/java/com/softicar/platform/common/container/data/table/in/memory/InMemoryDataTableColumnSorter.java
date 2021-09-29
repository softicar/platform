package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Comparator;

/**
 * Implementation of {@link IInMemoryDataTableSorter} for column-based sorting.
 *
 * @author Alexander Schmidt
 */
class InMemoryDataTableColumnSorter<R, V> implements IInMemoryDataTableSorter<R> {

	private final IDataTableColumn<R, V> column;
	private final Comparator<V> comparator;

	public InMemoryDataTableColumnSorter(IDataTableColumn<R, V> column, OrderDirection direction) {

		this.column = column;
		this.comparator = new InMemoryDataTableColumnValueComparator<>(column, direction);
	}

	@Override
	public int compareRows(R leftRow, R rightRow) {

		return comparator.compare(column.getValue(leftRow), column.getValue(rightRow));
	}
}
