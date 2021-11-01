package com.softicar.platform.dom.elements.tables.pageable.navigation;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableMarker;

class GoToPageButton extends DomPopupButton {

	public GoToPageButton(DomPageableTable table) {

		setIcon(DomElementsImages.TABLE_PAGE_GO_TO.getResource());
		setTitle(DomI18n.GO_TO_PAGE);
		setMarker(DomPageableTableMarker.NAVIGATION_PAGE_GOTO_BUTTON);
		setPopupFactory(() -> new GoToPagePopup(table));
	}
}