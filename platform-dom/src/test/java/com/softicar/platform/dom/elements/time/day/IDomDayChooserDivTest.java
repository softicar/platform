package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.Year;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.testing.node.tester.IDomNodeTesterFindMethods;

public interface IDomDayChooserDivTest extends IDomNodeTesterFindMethods {

	default void clickDayPopupButton() {

		findNodes(DomPopup.class).assertNone();
		findNode(DomTestMarker.DAY_POPUP_BUTTON).click();
		findNodes(DomPopup.class).assertOne();
	}

	default void selectYear(String year) {

		findNodes(DomPopup.class).assertOne();

		findNode(DomPopup.class)//
			.findSelect(DomTestMarker.DAY_CHOOSER_YEAR_SELECT)
			.selectValue(Year.get(Integer.parseInt(year)));

		findNodes(DomPopup.class).assertOne();
	}

	default void selectMonth(String monthName) {

		findNodes(DomPopup.class).assertOne();

		findNode(DomPopup.class)//
			.findSelect(DomTestMarker.DAY_CHOOSER_MONTH_SELECT)
			.selectValueByName(monthName);

		findNodes(DomPopup.class).assertOne();
	}

	default void clickDayInMonth(String dayInMonth) {

		findNodes(DomPopup.class).assertOne();

		findNode(DomPopup.class)//
			.findNode(DomTestMarker.DAY_CHOOSER)
			.findNodes(DomCell.class)
			.withText(dayInMonth)
			.assertOne()
			.click();

		findNodes(DomPopup.class).assertNone();
	}
}
