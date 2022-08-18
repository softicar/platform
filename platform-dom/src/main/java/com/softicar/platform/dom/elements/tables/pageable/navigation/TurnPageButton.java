package com.softicar.platform.dom.elements.tables.pageable.navigation;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.navigation.DomPageableTableNavigation.PagingDirection;

class TurnPageButton extends DomButton {

	private final DomPageableTable table;
	private final PagingDirection pageTurnDirection;

	public TurnPageButton(DomPageableTable table, PagingDirection pageTurnDirection) {

		this.table = table;
		this.pageTurnDirection = pageTurnDirection;

		setClickCallback(this::handleClick);

		if (pageTurnDirection == PagingDirection.BACKWARD) {
			setIcon(DomElementsImages.PAGE_PREVIOUS.getResource());
			setTitle(DomI18n.PREVIOUS_PAGE);
			addMarker(DomTestMarker.PAGEABLE_TABLE_NAVIGATION_PAGE_PREV_BUTTON);
		} else if (pageTurnDirection == PagingDirection.FORWARD) {
			setIcon(DomElementsImages.PAGE_NEXT.getResource());
			setTitle(DomI18n.NEXT_PAGE);
			addMarker(DomTestMarker.PAGEABLE_TABLE_NAVIGATION_PAGE_NEXT_BUTTON);
		} else {
			throw new SofticarUnknownEnumConstantException(pageTurnDirection);
		}
	}

	private void handleClick() {

		int currentPage = table.getCurrentPage();

		if (pageTurnDirection == PagingDirection.BACKWARD) {
			table.setCurrentPage(currentPage - 1);
		} else if (pageTurnDirection == PagingDirection.FORWARD) {
			table.setCurrentPage(currentPage + 1);
		} else {
			throw new SofticarUnknownEnumConstantException(pageTurnDirection);
		}
	}
}
