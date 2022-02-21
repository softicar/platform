package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableRowBasedColumnFilterNodeFactory;

public class EmfDataTableNonSortableNonFilterableColumnHandler<R, T> extends EmfDataTableNonSortableColumnHandler<R, T> {

	@Override
	public IEmfDataTableRowBasedColumnFilterNodeFactory<R> getFilterNodeFactory(IEmfDataTableColumn<R, T> column) {

		return null;
	}
}
