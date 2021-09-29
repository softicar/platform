package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;

public interface IEmfDataTableCellBuilder<T> {

	/**
	 * Builds the given table cell using the given value.
	 * 
	 * @param cell
	 *            the table cell to build
	 * @param value
	 *            the value to display in the cell (may be null)
	 */
	void buildCell(IEmfDataTableCell<?, T> cell, T value);
}
