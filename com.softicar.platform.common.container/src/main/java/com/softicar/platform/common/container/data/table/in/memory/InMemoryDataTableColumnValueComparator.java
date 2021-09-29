package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Comparator;

public class InMemoryDataTableColumnValueComparator<V> implements Comparator<V> {

	private final OrderDirection direction;
	private final Comparator<V> comparator;

	public InMemoryDataTableColumnValueComparator(IDataTableColumn<?, V> column, OrderDirection direction) {

		this.direction = direction;
		this.comparator = column//
			.getValueComparator()
			.map(this::applyNullsFirst)
			.map(this::applyDirection)
			.orElse((a, b) -> 0);
	}

	@Override
	public int compare(V left, V right) {

		return comparator.compare(left, right);
	}

	private Comparator<V> applyNullsFirst(Comparator<V> comparator) {

		return Comparator.nullsFirst(comparator);
	}

	private Comparator<V> applyDirection(Comparator<V> comparator) {

		return direction == OrderDirection.ASCENDING? comparator : comparator.reversed();
	}
}
