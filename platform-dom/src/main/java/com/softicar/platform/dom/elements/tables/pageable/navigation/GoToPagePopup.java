package com.softicar.platform.dom.elements.tables.pageable.navigation;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableMarker;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

/**
 * A {@link DomPopup} allowing for direct navigation to a specific page of a
 * {@link DomPageableTable}.
 *
 * @author Alexander Schmidt
 */
class GoToPagePopup extends DomPopup {

	private final DomPageableTable table;
	private PageNumberInput pageNumberInput;

	public GoToPagePopup(DomPageableTable table) {

		this.table = table;
		setCaption(DomI18n.GO_TO_PAGE);
		addCssClass(DomElementsCssClasses.DOM_PAGEABLE_TABLE_NAVIGATION_GO_TO_PAGE_POPUP);
	}

	@Override
	public void show() {

		removeChildren();

		final int totalPageCount = table.getPageCount();
		final int currentPageNumber = table.getCurrentPage() + 1;

		DomBar pageNumberBar = new DomBar();
		pageNumberBar.appendChild(pageNumberInput = new PageNumberInput());
		pageNumberBar.appendText(" / " + totalPageCount);

		pageNumberInput.setValue(Math.min(currentPageNumber, totalPageCount));
		appendChild(new DomLabelGrid().add(DomI18n.PAGE, pageNumberBar));
		appendActionNode(new OkayButton());
		appendCancelButton();

		super.show();
		pageNumberInput.focus();
		pageNumberInput.select();
	}

	private void applyCurrentPageToTableAndHide() {

		table.setCurrentPage(Math.max(Math.min(pageNumberInput.getValue().orElse(1), table.getPageCount()), 1) - 1);
		hide();
	}

	private class PageNumberInput extends DomIntegerInput implements IDomEnterKeyEventHandler {

		public PageNumberInput() {

			setMarker(DomPageableTableMarker.NAVIGATION_PAGE_GOTO_INPUT_ELEMENT);
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			applyCurrentPageToTableAndHide();
		}
	}

	private class OkayButton extends DomButton {

		public OkayButton() {

			setIcon(DomElementsImages.DIALOG_OKAY.getResource());
			setLabel(DomI18n.OK);
			setMarker(DomPageableTableMarker.NAVIGATION_PAGE_GOTO_EXECUTE_BUTTON);
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			applyCurrentPageToTableAndHide();
		}
	}
}
