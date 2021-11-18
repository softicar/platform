package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;

class EmfDataTableCell<R, T> extends DomCell implements IEmfDataTableCell<R, T> {

	private final IEmfDataTableRow<R> tableRow;
	private final IEmfDataTableColumn<R, T> column;

	public EmfDataTableCell(IEmfDataTableRow<R> tableRow, IEmfDataTableColumn<R, T> column) {

		this.tableRow = tableRow;
		this.column = column;
		setMarker(EmfDataTableDivMarker.BODY_CELL);
		column.getSettings().getMarkers().forEach(this::setMarker);
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
