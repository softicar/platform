package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilter;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of {@link IInMemoryDataTableFilterList}.
 *
 * @author Alexander Schmidt
 */
class InMemoryDataTableFilterList<R> implements IInMemoryDataTableFilterList<R> {

	private final List<IInMemoryDataTableFilter<R>> filters;
	private final DataTableFilterListOperator operator;
	private final INullaryVoidFunction callback;

	public InMemoryDataTableFilterList(DataTableFilterListOperator operator, INullaryVoidFunction callback) {

		this.filters = new ArrayList<>();
		this.operator = operator;
		this.callback = callback;
	}

	@Override
	public Collection<? extends IDataTableFilter> getFilters() {

		return Collections.unmodifiableCollection(filters);
	}

	@Override
	public DataTableFilterListOperator getFilterOperator() {

		return operator;
	}

	@Override
	public void clear() {

		filters.clear();
		callback.apply();
	}

	@Override
	public boolean isEmpty() {

		return filters.isEmpty();
	}

	@Override
	public IDataTableFilterList<R> addFilterList(DataTableFilterListOperator operator) {

		InMemoryDataTableFilterList<R> filterList = new InMemoryDataTableFilterList<>(operator, callback);
		filters.add(filterList);
		callback.apply();
		return filterList;
	}

	@Override
	public <V> void addValueFilter(IDataTableColumn<R, V> column, DataTableValueFilterOperator operator, V value) {

		filters.add(new InMemoryDataTableValueFilter<>(column, operator, value));
		callback.apply();
	}

	@Override
	public <V> void addCollectionFilter(IDataTableColumn<R, V> column, DataTableCollectionFilterOperator operator, Collection<? extends V> values) {

		filters.add(new InMemoryDataTableCollectionFilter<>(column, operator, values));
		callback.apply();
	}

	@Override
	public void addStringFilter(IDataTableColumn<R, String> column, DataTableStringFilterOperator operator, String value) {

		filters.add(new InMemoryDataTableStringFilter<>(column, operator, value));
		callback.apply();
	}

	@Override
	public boolean applyFilter(R row) {

		switch (operator) {
		case AND:
			return evaluateConjunctiveFilters(filters, row);
		case OR:
			return evaluateDisjunctiveFilters(filters, row);
		default:
			throw new SofticarUnknownEnumConstantException(operator);
		}
	}

	private Boolean evaluateConjunctiveFilters(Collection<IInMemoryDataTableFilter<R>> filters, R row) {

		for (IInMemoryDataTableFilter<R> filter: filters) {
			if (!filter.applyFilter(row)) {
				return false;
			}
		}
		return true;
	}

	private Boolean evaluateDisjunctiveFilters(Collection<IInMemoryDataTableFilter<R>> filters, R row) {

		for (IInMemoryDataTableFilter<R> filter: filters) {
			if (filter.applyFilter(row)) {
				return true;
			}
		}
		return false;
	}
}
