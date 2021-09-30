package com.softicar.platform.ajax.engine.elements;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

public class AjaxSessionTimeoutDialog extends DomDiv {

	public AjaxSessionTimeoutDialog() {

		addCssClass(AjaxCssClasses.AJAX_SESSION_TIMEOUT_DIALOG);
		addCssClass(AjaxCssClasses.HIDDEN);
		appendChild(new AjaxSessionTimeoutDialogMessage());
		getDomEngine().setReloadPageOnClick(this);
	}
}
