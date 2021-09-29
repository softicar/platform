package com.softicar.platform.ajax.engine.elements;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.dom.elements.DomDiv;

public class AjaxSessionTimeoutDialogMessage extends DomDiv {

	public AjaxSessionTimeoutDialogMessage() {

		addCssClass(AjaxCssClasses.AJAX_SESSION_TIMEOUT_DIALOG_MESSAGE);
		appendText(AjaxI18n.YOUR_SESSION_TIMED_OUT);
		getDomEngine().setReloadPageOnClick(this);
	}
}
