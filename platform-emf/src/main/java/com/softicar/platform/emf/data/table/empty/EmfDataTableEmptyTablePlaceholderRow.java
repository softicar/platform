package com.softicar.platform.emf.data.table.empty;

import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.IEmfDataTableConfig;

public class EmfDataTableEmptyTablePlaceholderRow extends DomRow {

	public EmfDataTableEmptyTablePlaceholderRow(IEmfDataTableConfig<?> configuration, int columnCount) {

		setMarker(EmfDataTableDivMarker.EMPTY_TABLE_PLACEHOLDER_ROW);
		appendChild(new EmfDataTableEmptyTablePlaceholderCell(configuration, columnCount));
	}
}
