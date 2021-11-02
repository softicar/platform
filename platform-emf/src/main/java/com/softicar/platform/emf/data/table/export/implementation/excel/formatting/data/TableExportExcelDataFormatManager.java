package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data;

import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Workbook;

public class TableExportExcelDataFormatManager {

	private final Workbook workbook;
	private final Map<String, Short> map = new TreeMap<>();

	public TableExportExcelDataFormatManager(Workbook workbook) {

		this.workbook = workbook;
	}

	public Short getDataFormat(String formatString) {

		Short dataFormat = this.map.get(formatString);
		if (dataFormat == null) {
			dataFormat = generateDataFormat(formatString);
			this.map.put(formatString, dataFormat);
		}

		return dataFormat;
	}

	private Short generateDataFormat(String formatString) {

		return this.workbook.createDataFormat().getFormat(formatString);
	}
}
