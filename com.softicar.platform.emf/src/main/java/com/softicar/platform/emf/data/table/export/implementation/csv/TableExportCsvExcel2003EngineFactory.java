package com.softicar.platform.emf.data.table.export.implementation.csv;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.engine.factory.AbstractTableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import java.util.Collection;

/**
 * Instantiates {@link TableExportCsvEngine}s to create CSV files that are
 * compatible to Excel 2003 (by prepending a Unicode "Byte Order Mark"
 * character).
 *
 * @author Alexander Schmidt
 */
public class TableExportCsvExcel2003EngineFactory extends AbstractTableExportEngineFactory<TableExportCsvEngine> {

	public TableExportCsvExcel2003EngineFactory() {

		IDisplayString displayString = DomI18n.COMMA_SEPARATED_VALUES//
			.concat(" ")
			.concat(DomI18n.EXCEL_2003_COMPATIBLE.encloseInParentheses());

		engineConfiguration//
			.setEngineDisplayString(displayString)
			.setFileNameExtension("csv")
			.setCompressed(false)
			.setAppendTimestamp(true);
	}

	@Override
	public TableExportCsvEngine create() {

		return new TableExportCsvEngine(engineConfiguration, this, ';', true);
	}

	@Override
	public TableExportPreconditionResultContainer checkPreconditions(Collection<TableExportTableModel> tableModels) {

		return null;
	}
}
