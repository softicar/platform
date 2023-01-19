package com.softicar.platform.emf.data.table.export.conversion.factory.selection;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.factory.ITableExportNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TableExportNodeConverterFactorySelectionModel {

	private final TableExportNodeConverterFactoryConfiguration nodeConverterFactoryConfiguration;
	private final Map<Integer, ITableExportNodeConverterFactory> selectedConverterFactoriesByColumn = new TreeMap<>();

	public TableExportNodeConverterFactorySelectionModel(TableExportNodeConverterFactoryConfiguration nodeConverterFactoryConfiguration) {

		this.nodeConverterFactoryConfiguration = nodeConverterFactoryConfiguration;
	}

	public void selectConverterFactory(int column, ITableExportNodeConverterFactory converterFactory) {

		if (converterFactory != null) {
			if (this.nodeConverterFactoryConfiguration.getAvailableFactories().contains(converterFactory)) {
				this.selectedConverterFactoriesByColumn.put(column, converterFactory);
			} else {
				throw new SofticarDeveloperException(
					"Cannot select unknown %s '%s'.",
					ITableExportNodeConverterFactory.class.getSimpleName(),
					converterFactory.getClass().getCanonicalName());
			}
		} else {
			this.selectedConverterFactoriesByColumn.remove(column);
		}
	}

	public void unselectConverterFactory(int column) {

		selectConverterFactory(column, null);
	}

	public Map<Integer, ITableExportNodeConverterFactory> getSelectedConverterFactoriesByColumn() {

		return selectedConverterFactoriesByColumn;
	}

	public Map<Integer, ITableExportNodeConverter> fetchNodeConvertersByColumn(Set<Integer> selectedColumnIndexes) {

		Map<Integer, ITableExportNodeConverter> nodeConvertersByColumn = new TreeMap<>();

		for (Integer columnIndex: selectedColumnIndexes) {
			ITableExportNodeConverterFactory selectedConverterFactory = this.selectedConverterFactoriesByColumn.get(columnIndex);
			ITableExportNodeConverter nodeConverter;

			if (selectedConverterFactory != null) {
				nodeConverter = selectedConverterFactory.create();
			} else {
				nodeConverter = this.nodeConverterFactoryConfiguration.getDefaultFactory().create();
			}

			nodeConvertersByColumn.put(columnIndex, nodeConverter);
		}

		return nodeConvertersByColumn;
	}
}
