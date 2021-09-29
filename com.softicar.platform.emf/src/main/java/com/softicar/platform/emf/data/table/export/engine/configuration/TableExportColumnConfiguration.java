package com.softicar.platform.emf.data.table.export.engine.configuration;

import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.model.TableExportColumnModel;
import java.util.Map;

public class TableExportColumnConfiguration<CT> {

	private final Map<Integer, TableExportColumnModel> selectedColumnModels;
	private final Map<Integer, ITableExportNodeConverter<CT>> nodeConvertersByColumn;
	private final ITableExportNodeConverter<CT> headerConverter;

	public TableExportColumnConfiguration(Map<Integer, TableExportColumnModel> selectedColumnModels, Map<Integer, ITableExportNodeConverter<CT>> nodeConvertersByColumn,
			ITableExportNodeConverter<CT> headerConverter) {

		this.selectedColumnModels = selectedColumnModels;
		this.nodeConvertersByColumn = nodeConvertersByColumn;
		this.headerConverter = headerConverter;
	}

	public Map<Integer, TableExportColumnModel> getSelectedColumnModels() {

		return selectedColumnModels;
	}

	public Map<Integer, ITableExportNodeConverter<CT>> getNodeConvertersByColumn() {

		return nodeConvertersByColumn;
	}

	public ITableExportNodeConverter<CT> getHeaderConverter() {

		return headerConverter;
	}
}
