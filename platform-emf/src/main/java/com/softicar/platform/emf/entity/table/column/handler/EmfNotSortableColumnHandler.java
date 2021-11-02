package com.softicar.platform.emf.entity.table.column.handler;

import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

public class EmfNotSortableColumnHandler extends EmfDataTableValueBasedColumnHandler<Boolean> {

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, Boolean> column) {

		return false;
	}
}
