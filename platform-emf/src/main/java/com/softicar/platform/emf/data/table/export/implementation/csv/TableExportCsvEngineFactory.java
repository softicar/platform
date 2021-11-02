package com.softicar.platform.emf.data.table.export.implementation.csv;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.engine.factory.AbstractTableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import java.util.Collection;

/**
 * Instantiates {@link TableExportCsvEngine}s to create CSV files.
 *
 * @author Alexander Schmidt
 */
public class TableExportCsvEngineFactory extends AbstractTableExportEngineFactory<TableExportCsvEngine> {

	public TableExportCsvEngineFactory() {

		engineConfiguration//
			.setEngineDisplayString(DomI18n.COMMA_SEPARATED_VALUES)
			.setFileNameExtension("csv")
			.setCompressed(false)
			.setAppendTimestamp(true);
	}

	@Override
	public TableExportCsvEngine create() {

		return new TableExportCsvEngine(engineConfiguration, this, ';', false);
	}

	@Override
	public TableExportPreconditionResultContainer checkPreconditions(Collection<TableExportTableModel> tableModels) {

		return null;
	}
}
