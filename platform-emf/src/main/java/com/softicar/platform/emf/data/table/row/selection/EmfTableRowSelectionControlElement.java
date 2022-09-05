package com.softicar.platform.emf.data.table.row.selection;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.style.CssColorEnum;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;

/**
 * A UI element to control the selection of rows in a {@link DomTable}.
 *
 * @author Alexander Schmidt
 */
public class EmfTableRowSelectionControlElement extends DomBar implements IRefreshable {

	private final IEmfTableRowSelectionProxy rowSelectionProxy;
	private final IRefreshable statusTextSpan;
	private final DomPageableTable table;

	public <T extends DomPageableTable> EmfTableRowSelectionControlElement(IEmfTableRowSelectionProxy rowSelectionProxy, T table) {

		this.rowSelectionProxy = rowSelectionProxy;
		this.table = table;
		this.statusTextSpan = new StatusTextSpan();
		addMarker(EmfTestMarker.DATA_TABLE_ROW_SELECTION_CONTROL_ELEMENT);
		addCssClass(DomCssClasses.DOM_PAGEABLE_TABLE_NAVIGATION);
		addCssClass(EmfCssClasses.EMF_DATA_TABLE_ROW_SELECTION_CONTROL_ELEMENT);
		refresh();
	}

	@Override
	public void refresh() {

		removeChildren();
		if (rowSelectionProxy.isMultiRowSelection()) {
			appendChild(new SelectAllOnCurrentPageButton());
			appendChild(new SelectInvertCurrentPageButton());
			appendChild(new UnselectAllOnCurrentPageButton());
			if (table.getPageCount() > 1) {
				appendChild(new UnselectAllPagesButton());
			}
		}
		appendChild(this.statusTextSpan);
		this.statusTextSpan.refresh();
	}

	private class SelectAllOnCurrentPageButton extends DomButton {

		public SelectAllOnCurrentPageButton() {

			setIcon(DomImages.TABLE_ROW_SELECTION_SELECT_CURRENT_PAGE.getResource());
			setTitle(EmfDataTableI18n.ALL);
			addMarker(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_SELECT_ALL_ON_CURRENT_PAGE);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			rowSelectionProxy.selectAllRowsOnPage();
		}
	}

	private class SelectInvertCurrentPageButton extends DomButton {

		public SelectInvertCurrentPageButton() {

			setIcon(DomImages.TABLE_ROW_SELECTION_INVERT_CURRENT_PAGE.getResource());
			setTitle(EmfDataTableI18n.INVERT);
			addMarker(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_SELECT_INVERT_CURRENT_PAGE);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			rowSelectionProxy.invertSelectionOfRowsOnPage();
		}
	}

	private class UnselectAllOnCurrentPageButton extends DomButton {

		public UnselectAllOnCurrentPageButton() {

			setIcon(DomImages.TABLE_ROW_SELECTION_UNSELECT_CURRENT_PAGE.getResource());
			setTitle(EmfDataTableI18n.NONE);
			addMarker(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_UNSELECT_ALL_ON_CURRENT_PAGE);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			rowSelectionProxy.unselectAllRowsOnPage();
		}
	}

	private class UnselectAllPagesButton extends DomButton {

		public UnselectAllPagesButton() {

			setIcon(DomImages.TABLE_ROW_SELECTION_UNSELECT_ALL_PAGES.getResource());
			setTitle(EmfDataTableI18n.NONE_ALL_PAGES);
			addMarker(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_UNSELECT_ALL_PAGES);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			rowSelectionProxy.unselectAllRowsOfTable();
		}
	}

	private class StatusTextSpan extends DomDiv implements IRefreshable {

		public StatusTextSpan() {

			addMarker(EmfTestMarker.DATA_TABLE_ROW_SELECTION_STATUS_TEXT_ELEMENT);
		}

		@Override
		public void refresh() {

			removeChildren();

			long numSelectedRows = rowSelectionProxy.getNumSelectedRows();
			long numSelectedRowsOnCurrentPage = rowSelectionProxy.getNumSelectedRowsOnCurrentPage();
			long numSelectedRowsOnOtherPages = numSelectedRows - numSelectedRowsOnCurrentPage;

			IDisplayString text;
			boolean highlight = false;
			if (numSelectedRowsOnOtherPages <= 0) {
				if (numSelectedRows == 1) {
					text = EmfDataTableI18n._1_ROW_SELECTED;
				} else {
					text = EmfDataTableI18n.ARG1_ROWS_SELECTED.toDisplay(numSelectedRows);
				}
			} else {
				highlight = true;
				if (numSelectedRows == 1) {
					text = EmfDataTableI18n._1_ROW_SELECTED_ON_ANOTHER_PAGE;
				} else {
					text = EmfDataTableI18n.ARG1_OF_ARG2_ROWS_SELECTED_ON_OTHER_PAGES.toDisplay(numSelectedRowsOnOtherPages, numSelectedRows);
				}
			}

			if (highlight) {
				setColor(CssColorEnum.RED);
			} else {
				unsetStyle(CssStyle.COLOR);
			}

			appendText(text);
		}
	}
}
