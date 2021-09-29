package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.styles.CssAlignItems;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.dom.styles.CssJustifyContent;

public class AutoCompleteInPopupTestCase extends PopupTestCase {

	public AutoCompleteInPopupTestCase() {

		setPopupSetup(popup -> {
			popup.appendChild(new AutoCompleteTestCaseInput(this));
			popup.setStyle(CssDisplay.FLEX);
			popup.setStyle(CssJustifyContent.CENTER);
			popup.setStyle(CssAlignItems.CENTER);
		});
	}
}
