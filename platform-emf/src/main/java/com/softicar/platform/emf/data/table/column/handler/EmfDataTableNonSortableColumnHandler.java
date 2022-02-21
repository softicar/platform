package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;

public class EmfDataTableNonSortableColumnHandler<R, T> extends EmfDataTableRowBasedColumnHandler<R, T> {

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, T> column) {

		return false;
	}
}
