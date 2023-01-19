package com.softicar.platform.emf.data.table.export.implementation.excel;

public enum TableExportExcelExportFormat {

	XLS(65536),
	XLSX(1048576);

	private final int maxRows;

	private TableExportExcelExportFormat(int maxRows) {

		this.maxRows = maxRows;
	}

	public int getMaxRows() {

		return maxRows;
	}
}
