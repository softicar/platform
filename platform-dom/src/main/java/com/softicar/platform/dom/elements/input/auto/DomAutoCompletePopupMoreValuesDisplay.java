package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;

class DomAutoCompletePopupMoreValuesDisplay extends DomDiv {

	public DomAutoCompletePopupMoreValuesDisplay() {

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_MORE_VALUES);
		appendChild(DomI18n.FURTHER_ENTRIES_AVAILABLE);
	}
}