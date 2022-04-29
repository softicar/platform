package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.HashMap;
import java.util.Map;

class EmfDataTableHeaderRow<R> extends DomRow {

	private final IEmfDataTable<R> dataTable;
	private final Map<IDataTableColumn<?, ?>, EmfDataTableHeaderCell<?, ?>> headerCellMap;

	public EmfDataTableHeaderRow(IEmfDataTable<R> dataTable) {

		this.dataTable = dataTable;
		this.headerCellMap = new HashMap<>();
		addMarker(EmfDataTableDivMarker.HEADER_PRIMARY_ROW);
		refresh();
	}

	public void refresh() {

		removeChildren();

		if (dataTable.getConfig().isRowSelectionEnabled() || dataTable.getConfig().getActionColumnHandler().isPresent()) {
			appendChild(new ActionHeaderCell());
		}

		for (IEmfDataTableColumn<R, ?> column: dataTable.getColumnsOrderedByCustomIndex()) {
			if (!column.isConcealed() && !column.isHidden()) {
				EmfDataTableHeaderCell<?, ?> headerCell = headerCellMap.get(column.getDataColumn());
				if (headerCell == null) {
					headerCell = new EmfDataTableHeaderCell<>(column);
					headerCellMap.put(column.getDataColumn(), headerCell);
				} else {
					headerCell.refresh();
				}
				appendChild(headerCell);
			}
		}
	}

	int getColumnCount() {

		int actionColumn = dataTable.getConfig().isRowSelectionEnabled() || dataTable.getConfig().getActionColumnHandler().isPresent()? 1 : 0;
		return actionColumn + headerCellMap.values().size();
	}

	private static class ActionHeaderCell extends DomHeaderCell {

		public ActionHeaderCell() {

			addMarker(EmfDataTableDivMarker.ACTION_HEADER_CELL);
		}
	}
}
