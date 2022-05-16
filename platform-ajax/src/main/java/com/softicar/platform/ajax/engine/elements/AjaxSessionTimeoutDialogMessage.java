package com.softicar.platform.ajax.engine.elements;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;

public class AjaxSessionTimeoutDialogMessage extends DomDiv {

	public AjaxSessionTimeoutDialogMessage() {

		addCssClass(AjaxCssClasses.AJAX_SESSION_TIMEOUT_DIALOG_MESSAGE);
		appendText(AjaxI18n.YOUR_SESSION_TIMED_OUT);
		DomButton button = new DomButton().setLabel(CommonCoreI18n.RETURN_TO_LOGIN);
		appendChild(new DomActionBar(button));
		getDomEngine().setReloadPageOnClick(button);
	}
}
