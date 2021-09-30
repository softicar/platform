package com.softicar.platform.emf.data.table.filter.value;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

public class EmfDataTableValueFilter<R, T> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, T> column;
	private final DataTableValueFilterOperator operator;
	private final T value;
	private final INullaryVoidFunction resetter;

	public EmfDataTableValueFilter(IEmfDataTableColumn<R, T> column, DataTableValueFilterOperator operator, T value, INullaryVoidFunction resetter) {

		this(column.getDataColumn(), operator, value, resetter);
	}

	public EmfDataTableValueFilter(IDataTableColumn<R, T> column, DataTableValueFilterOperator operator, T value, INullaryVoidFunction resetter) {

		this.column = column;
		this.operator = operator;
		this.value = value;
		this.resetter = resetter;
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		filters.addValueFilter(column, operator, value);
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	@Override
	public IDisplayString getDisplayString() {

		return new DisplayString()//
			.append(operator.getOperatorSymbol())
			.append(" ")
			.append(getValueDisplayString());
	}

	private IDisplayString getValueDisplayString() {

		if (value instanceof IDisplayable) {
			return ((IDisplayable) value).toDisplay();
		} else {
			return IDisplayString.create(" " + value);
		}
	}
}
