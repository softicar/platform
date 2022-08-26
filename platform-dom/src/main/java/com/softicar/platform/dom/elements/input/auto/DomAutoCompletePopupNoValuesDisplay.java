package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomDiv;

class DomAutoCompletePopupNoValuesDisplay extends DomDiv {

	public DomAutoCompletePopupNoValuesDisplay(String pattern) {

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_NO_VALUES);
		appendChild(getText(pattern));
	}

	private IDisplayString getText(String pattern) {

		if (pattern.isEmpty()) {
			return DomI18n.NO_RECORDS_FOUND.encloseInParentheses();
		} else {
			return IDisplayString.create(pattern).concatColon().concatSentence(DomI18n.NO_RECORDS_FOUND);
		}
	}
}
