package com.softicar.platform.emf.data.table.empty;

import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.IEmfDataTableConfig;

public class EmfDataTableEmptyTablePlaceholderRow extends DomRow {

	public EmfDataTableEmptyTablePlaceholderRow(IEmfDataTableConfig<?> configuration, int columnCount) {

		addMarker(EmfTestMarker.DATA_TABLE_EMPTY_TABLE_PLACEHOLDER_ROW);
		appendChild(new EmfDataTableEmptyTablePlaceholderCell(configuration, columnCount));
	}
}
