package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;

class EmfDataTableCell<R, T> extends DomCell implements IEmfDataTableCell<R, T> {

	private final IEmfDataTableRow<R> tableRow;
	private final IEmfDataTableColumn<R, T> column;

	public EmfDataTableCell(IEmfDataTableRow<R> tableRow, IEmfDataTableColumn<R, T> column) {

		this.tableRow = tableRow;
		this.column = column;
		getDomEngine().setPreventDoubleClickSelection(this, true);
		addMarker(EmfTestMarker.DATA_TABLE_BODY_CELL);
		column.getSettings().getMarkers().forEach(this::addMarker);
	}

	@Override
	public IEmfDataTableRow<R> getTableRow() {

		return tableRow;
	}

	@Override
	public IEmfDataTableColumn<R, T> getColumn() {

		return column;
	}
}
