package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class InMemoryDataTableColumnValuesLoader<R, V> {

	private final IDataTableColumn<R, V> column;

	public InMemoryDataTableColumnValuesLoader(IDataTableColumn<R, V> column) {

		this.column = column;
	}

	public List<V> loadDistinctValues(Iterable<R> tableRows, int limit) {

		Set<V> distinctValueSet = new HashSet<>();
		List<V> orderedDistinctValueList = new ArrayList<>();

		for (R row: tableRows) {
			if (limit > 0 && distinctValueSet.size() >= limit) {
				break;
			}

			V value = column.getValue(row);
			if (value != null && distinctValueSet.add(value)) {
				orderedDistinctValueList.add(value);
			}
		}

		return orderedDistinctValueList;
	}
}
