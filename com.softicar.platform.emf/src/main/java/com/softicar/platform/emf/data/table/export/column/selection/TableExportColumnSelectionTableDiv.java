package com.softicar.platform.emf.data.table.export.column.selection;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.IEmfDataTableRow;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableNonSortableNonFilterableColumnHandler;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import java.util.Set;
import java.util.stream.Collectors;

class TableExportColumnSelectionTableDiv extends DomDiv {

	private final ITableExportEngine<?> currentEngine;
	private final IEmfDataTableDiv<TableExportColumnSelectionTableRow> tableDiv;

	public TableExportColumnSelectionTableDiv(TableExportTableModel tableModel, ITableExportEngine<?> currentEngine) {

		this.currentEngine = currentEngine;
		TableExportColumnSelectionTable table = new TableExportColumnSelectionTable(tableModel);
		this.tableDiv = new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getPositionColumn(), new EmfDataTableNonSortableNonFilterableColumnHandler<>())
			.setColumnHandler(table.getNameColumn(), new EmfDataTableNonSortableNonFilterableColumnHandler<>())
			.setColumnHandler(table.getConversionColumn(), new ConversionColumnHandler())
			.setRowSelectionModeMulti()
			.setHideNavigation(true)
			.buildAndAppendTo(this);
		preselectRows(tableModel);
	}

	private void preselectRows(TableExportTableModel tableModel) {

		Set<Integer> preSelectedColumnIndexes = tableModel.getSelectedColumnModels().keySet();
		for (IEmfDataTableRow<TableExportColumnSelectionTableRow> selectionRow: tableDiv.getDisplayedTableRows()) {
			if (preSelectedColumnIndexes == null || preSelectedColumnIndexes.contains(selectionRow.getDataRow().getIndex())) {
				selectionRow.setSelected(true, true);
			}
		}
	}

	public Set<Integer> getSelectedColumnIndexesOrThrow() {

		Set<Integer> selectedColumnIndexes = tableDiv//
			.getSelectedRows()
			.stream()
			.map(TableExportColumnSelectionTableRow::getIndex)
			.collect(Collectors.toSet());

		if (selectedColumnIndexes != null && selectedColumnIndexes.isEmpty()) {
			throw new SofticarUserException(DomI18n.PLEASE_SELECT_AT_LEAST_ONE_COLUMN);
		}

		return selectedColumnIndexes;
	}

	private class ConversionColumnHandler
			extends EmfDataTableNonSortableNonFilterableColumnHandler<TableExportColumnSelectionTableRow, TableExportColumnSelectionTableRow> {

		@Override
		public void buildCell(IEmfDataTableCell<TableExportColumnSelectionTableRow, TableExportColumnSelectionTableRow> cell,
				TableExportColumnSelectionTableRow row) {

			if (currentEngine != null) {
				DomBar conversionSelectBar = new DomBar();
				DomDiv helperDiv = new DomDiv();
				conversionSelectBar
					.appendChild(
						currentEngine//
							.createConverterFactoryValueSelectBuiler(row.getPosition(), helperDiv)
							.build());
				conversionSelectBar.appendChild(helperDiv);
				cell.appendChild(conversionSelectBar);
			}
		}
	}
}
