package com.softicar.platform.emf.data.table.export.column.selection;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.modal.DomDismissablePopup;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;

public class TableExportColumnSelectionPopup extends DomDismissablePopup {

	private final IRefreshable refreshable;
	private final TableExportTableModel tableModel;
	private final TableExportColumnSelectionTableDiv tableDiv;

	public TableExportColumnSelectionPopup(TableExportTableModel tableModel, IRefreshable refreshable, ITableExportEngine currentEngine) {

		this.tableModel = tableModel;
		this.refreshable = refreshable;
		this.tableDiv = appendChild(new TableExportColumnSelectionTableDiv(tableModel, currentEngine));
		setCaption(DomI18n.SELECT_COLUMNS);
		appendActionNode(new ApplyButton());
		appendCancelButton();
	}

	private class ApplyButton extends DomButton {

		public ApplyButton() {

			setIcon(DomImages.DIALOG_OKAY.getResource());
			setLabel(DomI18n.APPLY);
			setClickCallback(this::applySelectedColumnsToTableModel);
		}

		private void applySelectedColumnsToTableModel() {

			tableModel.setSelectedColumnIndexes(tableDiv.getSelectedColumnIndexesOrThrow());
			close();
			if (refreshable != null) {
				refreshable.refresh();
			}
		}
	}
}
