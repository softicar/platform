package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

class MatchesRegexpFilter<R> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, String> column;
	private final String value;
	private final INullaryVoidFunction resetter;

	public MatchesRegexpFilter(IDataTableColumn<R, String> column, String value, INullaryVoidFunction resetter) {

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

		filters.addStringFilter(column, DataTableStringFilterOperator.REGEXP, value);
	}

	@Override
	public IDisplayString getDisplayString() {

		return IDisplayString.create("regexp").concat("(").concat(value).concat(")");
	}
}
