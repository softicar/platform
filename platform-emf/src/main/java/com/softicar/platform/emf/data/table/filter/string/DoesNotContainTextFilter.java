package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

class DoesNotContainTextFilter<R> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, String> column;
	private final String text;
	private final INullaryVoidFunction resetter;

	public DoesNotContainTextFilter(IDataTableColumn<R, String> column, String text, INullaryVoidFunction resetter) {

		this.column = column;
		this.text = text;
		this.resetter = resetter;
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		filters.addStringFilter(column, DataTableStringFilterOperator.NOT_LIKE, "%" + text + "%");
	}

	@Override
	public IDisplayString getDisplayString() {

		return EmfDataTableI18n.DOES_NOT_CONTAIN.concat("('").concat(text).concat("')");
	}
}
