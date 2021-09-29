package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Instantiates {@link TableExportExcelEngine}s to create Excel files in XLSX
 * format.
 *
 * @author Alexander Schmidt
 */
public class TableExportExcelXlsxEngineFactory extends AbstractTableExportExcelEngineFactory {

	public TableExportExcelXlsxEngineFactory() {

		super(TableExportExcelExportFormat.XLSX_STREAMING);

		engineConfiguration//
			.setEngineDisplayString(IDisplayString.create("Excel 2007 - 2013"))
			.setFileNameExtension("xlsx")
			.setCompressed(false)
			.setAppendTimestamp(true);
	}
}
