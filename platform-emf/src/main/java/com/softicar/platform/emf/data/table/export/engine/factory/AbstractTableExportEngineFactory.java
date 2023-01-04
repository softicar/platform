package com.softicar.platform.emf.data.table.export.engine.factory;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;

public abstract class AbstractTableExportEngineFactory<ET extends ITableExportEngine> implements ITableExportEngineFactory<ET> {

	protected final TableExportEngineConfiguration engineConfiguration;

	public AbstractTableExportEngineFactory() {

		this.engineConfiguration = new TableExportEngineConfiguration();
	}

	@Override
	public TableExportEngineConfiguration getEngineConfiguration() {

		return engineConfiguration;
	}

	@Override
	public IDisplayString toDisplay() {

		IDisplayString name = engineConfiguration.getEngineDisplayString();
		String extension = engineConfiguration.getFileNameExtension();

		if (extension != null) {
			name = name.concat(" (.").concat(extension).concat(")");
		}

		return name;
	}
}
