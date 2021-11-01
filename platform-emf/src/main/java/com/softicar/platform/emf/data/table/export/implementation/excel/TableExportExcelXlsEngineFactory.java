package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.i18n.IDisplayString;

/**
 * Instantiates {@link TableExportExcelEngine}s to create Excel files in XLS
 * format.
 *
 * @author Alexander Schmidt
 */
public class TableExportExcelXlsEngineFactory extends AbstractTableExportExcelEngineFactory {

	public TableExportExcelXlsEngineFactory() {

		super(TableExportExcelExportFormat.XLS);

		engineConfiguration//
			.setEngineDisplayString(IDisplayString.create("Excel 97 - 2003"))
			.setFileNameExtension("xls")
			.setCompressed(false)
			.setAppendTimestamp(true);
	}
}
