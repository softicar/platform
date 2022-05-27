package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import java.util.Comparator;

/**
 * Implementation of {@link IInMemoryDataTableFilter} for values.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class InMemoryDataTableValueFilter<R, V> implements IInMemoryDataTableFilter<R> {

	private final IDataTableColumn<R, V> column;
	private final DataTableValueFilterOperator operator;
	private final V value;
	private final Comparator<V> comparator;

	public InMemoryDataTableValueFilter(IDataTableColumn<R, V> column, DataTableValueFilterOperator operator, V value) {

		this.column = column;
		this.operator = operator;
		this.value = value;
		this.comparator = new InMemoryDataTableColumnValueComparator<>(column, OrderDirection.ASCENDING);
	}

	@Override
	public boolean applyFilter(R row) {

		switch (operator) {
		case EQUAL:
			return compareValues(row) == 0;
		case NOT_EQUAL:
			return compareValues(row) != 0;
		case GREATER:
			return compareValues(row) > 0;
		case GREATER_EQUAL:
			return compareValues(row) >= 0;
		case LESS:
			return compareValues(row) < 0;
		case LESS_EQUAL:
			return compareValues(row) <= 0;
		case EMPTY:
			return column.getValue(row) == null;
		case NOT_EMPTY:
			return column.getValue(row) != null;
		default:
			throw new SofticarUnknownEnumConstantException(operator);
		}
	}

	private int compareValues(R row) {

		return comparator.compare(column.getValue(row), value);
	}
}
