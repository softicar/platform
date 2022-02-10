package com.softicar.platform.emf.data.table.empty;

import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;

class EmfDataTableEmptyTablePlaceholderCell extends DomCell {

	public EmfDataTableEmptyTablePlaceholderCell(int columnCount) {

		setMarker(EmfDataTableDivMarker.EMPTY_TABLE_PLACEHOLDER_CELL);
		setColSpan(columnCount);
		appendChild(new EmfDataTableEmptyTablePlaceholderDiv());
	}
}