package com.softicar.platform.emf.data.table.filter.list;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.DisplayStrings;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class EmfDataTableFilterList<R> implements IEmfDataTableFilter<R> {

	private final DataTableFilterListOperator operator;
	private final List<IEmfDataTableFilter<R>> filters;
	private BiFunction<DataTableFilterListOperator, List<IEmfDataTableFilter<R>>, IDisplayString> displayStringFunction;

	public EmfDataTableFilterList(DataTableFilterListOperator operator) {

		this.operator = operator;
		this.filters = new ArrayList<>();
		this.displayStringFunction = this::getDisplayString;
	}

	public void addFilter(IEmfDataTableFilter<R> filter) {

		Objects.requireNonNull(filter);
		this.filters.add(filter);
	}

	public boolean isEmpty() {

		return filters.isEmpty();
	}

	@Override
	public void reset() {

		for (IEmfDataTableFilter<R> filter: filters) {
			filter.reset();
		}
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		IDataTableFilterList<R> filterList = filters.addFilterList(operator);
		this.filters.stream().forEach(filter -> filter.addTo(filterList));
	}

	@Override
	public IDisplayString getDisplayString() {

		return displayStringFunction.apply(operator, filters);
	}

	public void setDisplayStringFunction(BiFunction<DataTableFilterListOperator, List<IEmfDataTableFilter<R>>, IDisplayString> displayStringFunction) {

		this.displayStringFunction = displayStringFunction;
	}

	private IDisplayString getDisplayString(DataTableFilterListOperator operator, List<IEmfDataTableFilter<R>> filters) {

		IDisplayString delimiter = operator.toDisplay().enclose(" ", "\n");
		return filters
			.stream()//
			.map(filter -> filter.getDisplayString())
			.collect(DisplayStrings.joining(delimiter));
	}
}
