package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

public class NotEmptyTextFilter<R> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, String> column;
	private final INullaryVoidFunction resetter;

	public NotEmptyTextFilter(IDataTableColumn<R, String> column, INullaryVoidFunction resetter) {

		this.column = column;
		this.resetter = resetter;
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		filters.addStringFilter(column, DataTableStringFilterOperator.REGEXP, "^(?!\\s*$).+");
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	@Override
	public IDisplayString getDisplayString() {

		return EmfDataTableI18n.IS_NOT_EMPTY;
	}
}
