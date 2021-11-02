package com.softicar.platform.emf.data.table.export.implementation.html;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.data.table.export.engine.factory.AbstractTableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import java.util.Collection;

public class TableExportHtmlEngineFactory extends AbstractTableExportEngineFactory<TableExportHtmlEngine> {

	public TableExportHtmlEngineFactory() {

		engineConfiguration//
			.setEngineDisplayString(IDisplayString.create("HTML"))
			.setFileNameExtension("html")
			.setCompressed(false)
			.setAppendTimestamp(true);
	}

	@Override
	public TableExportHtmlEngine create() {

		return new TableExportHtmlEngine(engineConfiguration, this);
	}

	@Override
	public TableExportPreconditionResultContainer checkPreconditions(Collection<TableExportTableModel> tableModels) {

		return null;
	}
}
