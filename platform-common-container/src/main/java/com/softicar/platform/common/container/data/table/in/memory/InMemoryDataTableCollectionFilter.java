package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import java.util.Collection;

/**
 * Implementation of {@link IInMemoryDataTableFilter} for values.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class InMemoryDataTableCollectionFilter<R, V> implements IInMemoryDataTableFilter<R> {

	private final IDataTableColumn<R, V> column;
	private final DataTableCollectionFilterOperator operator;
	private final Collection<? extends V> values;

	public InMemoryDataTableCollectionFilter(IDataTableColumn<R, V> column, DataTableCollectionFilterOperator operator, Collection<? extends V> values) {

		this.column = column;
		this.operator = operator;
		this.values = values;
	}

	@Override
	public boolean applyFilter(R row) {

		switch (operator) {
		case IN:
			return values.contains(column.getValue(row));
		case NOT_IN:
			return !values.contains(column.getValue(row));
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}
}
