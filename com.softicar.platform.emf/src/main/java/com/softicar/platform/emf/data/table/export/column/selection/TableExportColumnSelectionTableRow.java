package com.softicar.platform.emf.data.table.export.column.selection;

class TableExportColumnSelectionTableRow {

	private final String columnName;
	private final int index;

	public TableExportColumnSelectionTableRow(String columnName, int index) {

		this.columnName = columnName;
		this.index = index;
	}

	public String getColumnName() {

		return columnName;
	}

	public Integer getIndex() {

		return index;
	}

	public Integer getPosition() {

		return index + 1;
	}
}
