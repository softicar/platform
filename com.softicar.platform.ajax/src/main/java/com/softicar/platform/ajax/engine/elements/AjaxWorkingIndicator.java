package com.softicar.platform.ajax.engine.elements;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.dom.elements.DomDiv;

public class AjaxWorkingIndicator extends DomDiv {

	public AjaxWorkingIndicator() {

		addCssClass(AjaxCssClasses.AJAX_WORKING_INDICATOR);
		addCssClass(AjaxCssClasses.HIDDEN);
		appendText(AjaxI18n.APPLICATION_IS_WORKING);
	}
}
