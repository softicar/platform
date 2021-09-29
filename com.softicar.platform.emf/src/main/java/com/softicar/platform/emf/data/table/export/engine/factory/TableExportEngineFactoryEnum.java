package com.softicar.platform.emf.data.table.export.engine.factory;

import com.softicar.platform.emf.data.table.export.implementation.csv.TableExportCsvEngineFactory;
import com.softicar.platform.emf.data.table.export.implementation.csv.TableExportCsvExcel2003EngineFactory;
import com.softicar.platform.emf.data.table.export.implementation.excel.TableExportExcelXlsEngineFactory;
import com.softicar.platform.emf.data.table.export.implementation.excel.TableExportExcelXlsxEngineFactory;
import com.softicar.platform.emf.data.table.export.implementation.html.TableExportHtmlEngineFactory;
import com.softicar.platform.emf.data.table.export.popup.TableExportPopupButton;

/**
 * Enumerates all {@link ITableExportEngineFactory} implementations (i.e. export
 * formats) that are available for selection in the pop-up created by
 * {@link TableExportPopupButton}.
 *
 * @author Alexander Schmidt
 */
public enum TableExportEngineFactoryEnum {

	CSV(new TableExportCsvEngineFactory()),
	CSV_EXCEL_2003(new TableExportCsvExcel2003EngineFactory()),
	EXCEL_POI_XLS(new TableExportExcelXlsEngineFactory()),
	EXCEL_POI_XLSX(new TableExportExcelXlsxEngineFactory()),
	HTML(new TableExportHtmlEngineFactory())
	//
	;

	private final ITableExportEngineFactory<?> factory;

	private TableExportEngineFactoryEnum(ITableExportEngineFactory<?> factory) {

		this.factory = factory;
	}

	public ITableExportEngineFactory<?> get() {

		return this.factory;
	}
}
