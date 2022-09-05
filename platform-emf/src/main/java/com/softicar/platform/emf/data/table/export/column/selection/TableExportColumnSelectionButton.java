package com.softicar.platform.emf.data.table.export.column.selection;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;

public class TableExportColumnSelectionButton extends DomPopupButton implements IRefreshable {

	private final TableExportTableModel tableModel;
	private final ITableExportEngine<?> currentEngine;
	private TableExportColumnSelectionPopup columnSelectionPopup;

	public TableExportColumnSelectionButton(TableExportTableModel tableModel, ITableExportEngine<?> currentEngine) {

		this.tableModel = tableModel;
		this.currentEngine = currentEngine;
		setIcon(DomImages.TABLE_COLUMN_SELECTION.getResource());
		setPopupFactory(this::createPopup);
		refresh();
	}

	public TableExportColumnSelectionPopup getTableExportColumnSelectionPopup() {

		return columnSelectionPopup;
	}

	private DomPopup createPopup() {

		return this.columnSelectionPopup = new TableExportColumnSelectionPopup(tableModel, this, currentEngine);
	}

	@Override
	public void refresh() {

		setLabel(
			DomI18n.SELECT_COLUMNS_ARG1_ARG2
				.toDisplay(//
					this.tableModel.getSelectedColumnModels().size(),
					this.tableModel.getTableColumnModels().size()));
	}
}
