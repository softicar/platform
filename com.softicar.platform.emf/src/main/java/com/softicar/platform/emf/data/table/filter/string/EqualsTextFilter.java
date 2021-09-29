package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

class EqualsTextFilter<R> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, String> column;
	private final String value;
	private final INullaryVoidFunction resetter;

	public EqualsTextFilter(IDataTableColumn<R, String> column, String value, INullaryVoidFunction resetter) {

		this.column = column;
		this.value = value;
		this.resetter = resetter;
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		filters.addValueFilter(column, DataTableValueFilterOperator.EQUAL, value);
	}

	@Override
	public IDisplayString getDisplayString() {

		return new DisplayString().append("='").append(value).append("'");
	}
}
