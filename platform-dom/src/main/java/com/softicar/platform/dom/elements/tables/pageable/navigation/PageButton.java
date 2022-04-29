package com.softicar.platform.dom.elements.tables.pageable.navigation;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableMarker;

/**
 * A {@link DomButton} to switch to a specific page of a
 * {@link DomPageableTable}.
 *
 * @author Oliver Richers
 */
class PageButton extends DomButton {

	public PageButton(DomPageableTable table, int pageIndex) {

		this(table, pageIndex, "" + (pageIndex + 1));
	}

	public PageButton(DomPageableTable table, int pageIndex, String label) {

		setLabel(label);
		addMarker(DomPageableTableMarker.NAVIGATION_PAGE_NUMBER_BUTTON);
		setClickCallback(() -> table.setCurrentPage(pageIndex));

		if (pageIndex == table.getCurrentPage()) {
			addCssClass(DomCssPseudoClasses.SELECTED);
		}
	}
}
