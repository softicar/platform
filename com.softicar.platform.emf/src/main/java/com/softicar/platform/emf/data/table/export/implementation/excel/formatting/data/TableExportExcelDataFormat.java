package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data;

import java.util.Comparator;

public class TableExportExcelDataFormat implements Comparable<TableExportExcelDataFormat> {

	private final String formatString;

	@Override
	public int compareTo(TableExportExcelDataFormat o) {

		return Comparator//
			.comparing(TableExportExcelDataFormat::getFormatString)
			.compare(this, o);
	}

	public TableExportExcelDataFormat(String formatString) {

		this.formatString = formatString;
	}

	public String getFormatString() {

		return formatString;
	}
}
