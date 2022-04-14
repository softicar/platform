package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.element.TableExportChildElementFetcher;
import com.softicar.platform.emf.data.table.export.engine.factory.AbstractTableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult.Level;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import java.util.Collection;

/**
 * Base of all POI based export engines that are configured via
 * {@link TableExportExcelExportFormat}.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportExcelEngineFactory extends AbstractTableExportEngineFactory<TableExportExcelEngine> {

	protected final TableExportExcelExportConfiguration poiExportConfiguration;

	public AbstractTableExportExcelEngineFactory(TableExportExcelExportFormat format) {

		this.poiExportConfiguration = new TableExportExcelExportConfiguration();
		this.poiExportConfiguration.setFormat(format);
	}

	@Override
	public TableExportPreconditionResultContainer checkPreconditions(Collection<TableExportTableModel> tableModels) {

		TableExportPreconditionResultContainer resultContainer = new TableExportPreconditionResultContainer();

		int totalNumRows = 0;
		for (TableExportTableModel tableModel: tableModels) {
			totalNumRows += TableExportChildElementFetcher.getTotalNumberOfRows(tableModel.getTable());
		}
		totalNumRows += (tableModels.size() - 1) * TableExportExcelEngine.NUM_TABLE_SPACER_ROWS;

		int maxRows = this.poiExportConfiguration.getFormat().getMaxRows();

		if (totalNumRows > maxRows) {
			resultContainer
				.add(
					new TableExportPreconditionResult(
						Level.ERROR,
						DomI18n.FOR_AN_EXPORT_IN_THE_SELECTED_FORMAT_THE_MAXIMUM_NUMBER_OF_TABLE_ROWS_MUST_NOT_EXCEED_ARG1//
							.toDisplay(maxRows)
							.concat(" ")
							.concat(DomI18n.THE_TABLE_TO_BE_EXPORTED_COMPRISES_ARG1_ROWS_INCLUDING_HEADER_ROWS.toDisplay(totalNumRows))));
		}

		return resultContainer;
	}

	@Override
	public TableExportExcelEngine create() {

		return new TableExportExcelEngine(getEngineConfiguration(), poiExportConfiguration, this);
	}

	public AbstractTableExportExcelEngineFactory setDefaultSheetTitle(IDisplayString title) {

		this.poiExportConfiguration.setDefaultSheetTitle(title);
		return this;
	}

	public AbstractTableExportExcelEngineFactory setSheetPerTable(boolean enabled) {

		this.poiExportConfiguration.setSheetPerTable(enabled);
		return this;
	}
}
