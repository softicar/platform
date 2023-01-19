package com.softicar.platform.emf.data.table.export.column.selection;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.model.TableExportColumnModel;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import java.util.ArrayList;
import java.util.List;

class TableExportColumnSelectionTable extends AbstractInMemoryDataTable<TableExportColumnSelectionTableRow> {

	private final TableExportTableModel tableModel;
	private final IDataTableColumn<TableExportColumnSelectionTableRow, Integer> positionColumn;
	private final IDataTableColumn<TableExportColumnSelectionTableRow, String> nameColumn;
	private final IDataTableColumn<TableExportColumnSelectionTableRow, TableExportColumnSelectionTableRow> conversionColumn;

	public TableExportColumnSelectionTable(TableExportTableModel tableModel) {

		this.tableModel = tableModel;
		this.positionColumn = newColumn(Integer.class)//
			.setGetter(TableExportColumnSelectionTableRow::getPosition)
			.setTitle(IDisplayString.create("#"))
			.addColumn();
		this.nameColumn = newColumn(String.class)//
			.setGetter(TableExportColumnSelectionTableRow::getColumnName)
			.setTitle(DomI18n.COLUMN_NAME)
			.addColumn();
		this.conversionColumn = newColumn(TableExportColumnSelectionTableRow.class)//
			.setGetter(it -> it)
			.setTitle(DomI18n.CONVERSION)
			.addColumn();
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return DataTableIdentifier.empty();
	}

	@Override
	protected Iterable<TableExportColumnSelectionTableRow> getTableRows() {

		List<TableExportColumnSelectionTableRow> rows = new ArrayList<>();
		List<TableExportColumnModel> columnModels = tableModel.getTableColumnModels();

		for (int index = 0; index < columnModels.size(); index++) {
			rows.add(new TableExportColumnSelectionTableRow(columnModels.get(index).getName(), index));
		}
		return rows;
	}

	public IDataTableColumn<TableExportColumnSelectionTableRow, Integer> getPositionColumn() {

		return positionColumn;
	}

	public IDataTableColumn<TableExportColumnSelectionTableRow, String> getNameColumn() {

		return nameColumn;
	}

	public IDataTableColumn<TableExportColumnSelectionTableRow, TableExportColumnSelectionTableRow> getConversionColumn() {

		return conversionColumn;
	}
}
