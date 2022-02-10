package com.softicar.platform.emf.data.table.empty;

import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;

public class EmfDataTableEmptyTablePlaceholderRow extends DomRow {

	public EmfDataTableEmptyTablePlaceholderRow(int columnCount) {

		setMarker(EmfDataTableDivMarker.EMPTY_TABLE_PLACEHOLDER_ROW);
		appendChild(new EmfDataTableEmptyTablePlaceholderCell(columnCount));
	}
}
