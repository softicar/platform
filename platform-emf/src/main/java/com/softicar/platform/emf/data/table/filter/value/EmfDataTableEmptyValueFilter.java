package com.softicar.platform.emf.data.table.filter.value;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

public class EmfDataTableEmptyValueFilter<R, T> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, T> column;
	private final INullaryVoidFunction resetter;

	public EmfDataTableEmptyValueFilter(IEmfDataTableColumn<R, T> column, INullaryVoidFunction resetter) {

		this(column.getDataColumn(), resetter);
	}

	public EmfDataTableEmptyValueFilter(IDataTableColumn<R, T> column, INullaryVoidFunction resetter) {

		this.column = column;
		this.resetter = resetter;
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		filters.addValueFilter(column, DataTableValueFilterOperator.EMPTY, null);
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	@Override
	public IDisplayString getDisplayString() {

		return EmfDataTableI18n.IS_EMPTY;
	}
}
