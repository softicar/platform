package com.softicar.platform.emf.data.table.empty;

import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.IEmfDataTableConfig;

class EmfDataTableEmptyTablePlaceholderCell extends DomCell {

	public EmfDataTableEmptyTablePlaceholderCell(IEmfDataTableConfig<?> configuration, int columnCount) {

		addMarker(EmfDataTableDivMarker.EMPTY_TABLE_PLACEHOLDER_CELL);
		setColSpan(columnCount);
		appendChild(configuration.getEmptyTablePlaceholderFactory().get());
	}
}
