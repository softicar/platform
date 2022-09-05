package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomDiv;

class DomAutoCompletePopupMoreValuesDisplay extends DomDiv {

	public DomAutoCompletePopupMoreValuesDisplay() {

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_MORE_VALUES);
		appendChild(DomI18n.FURTHER_ENTRIES_AVAILABLE);
	}
}
