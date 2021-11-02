package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.common.container.data.table.IDataTableColumn;

class EmfDataTableConfigurationTableRow<R> {

	private final IDataTableColumn<R, ?> column;
	private final int index;

	public EmfDataTableConfigurationTableRow(IDataTableColumn<R, ?> column, int index) {

		this.column = column;
		this.index = index;
	}

	public IDataTableColumn<R, ?> getColumn() {

		return column;
	}

	public Integer getIndex() {

		return index;
	}
}
