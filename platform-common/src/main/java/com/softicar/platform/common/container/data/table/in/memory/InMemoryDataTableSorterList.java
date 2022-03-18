package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * Combined implementation of {@link IInMemoryDataTableSorter} and
 * {@link IDataTableSorterList}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class InMemoryDataTableSorterList<R> implements IInMemoryDataTableSorter<R>, IDataTableSorterList<R> {

	private final INullaryVoidFunction callback;
	private final List<IInMemoryDataTableSorter<R>> sorters;

	public InMemoryDataTableSorterList(INullaryVoidFunction callback) {

		this.callback = callback;
		this.sorters = new ArrayList<>();
	}

	@Override
	public void clear() {

		sorters.clear();
		callback.apply();
	}

	@Override
	public boolean isEmpty() {

		return sorters.isEmpty();
	}

	@Override
	public void addSorter(IDataTableColumn<R, ?> column, OrderDirection orderDirection) {

		sorters.add(new InMemoryDataTableColumnSorter<>(column, orderDirection));
		callback.apply();
	}

	@Override
	public int compareRows(R leftRow, R rightRow) {

		for (IInMemoryDataTableSorter<R> sorter: sorters) {
			int result = sorter.compareRows(leftRow, rightRow);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
