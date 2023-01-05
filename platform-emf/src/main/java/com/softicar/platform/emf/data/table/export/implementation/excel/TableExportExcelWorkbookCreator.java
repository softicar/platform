package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class TableExportExcelWorkbookCreator {

	private static final int XLSX_STREAMING_ROW_WINDOW_SIZE = 128;

	public static Workbook createWorkbook(TableExportExcelExportFormat format) {

		if (format == TableExportExcelExportFormat.XLS) {
			return new HSSFWorkbook();
		}

		else if (format == TableExportExcelExportFormat.XLSX) {
			// Use SXSSFWorkbook instead of XSSFWorkbook, for streaming support.
			return new SXSSFWorkbook(XLSX_STREAMING_ROW_WINDOW_SIZE);
		}

		else {
			throw new SofticarUnknownEnumConstantException(format);
		}
	}
}
