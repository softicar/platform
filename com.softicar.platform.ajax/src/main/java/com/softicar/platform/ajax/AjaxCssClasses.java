package com.softicar.platform.ajax;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface AjaxCssClasses {

	ICssClass AJAX_SESSION_TIMEOUT_DIALOG = new CssClass("AjaxSessionTimeoutDialog", AjaxCssFiles.AJAX_STYLE);
	ICssClass AJAX_SESSION_TIMEOUT_DIALOG_MESSAGE = new CssClass("AjaxSessionTimeoutDialogMessage", AjaxCssFiles.AJAX_STYLE);
	ICssClass AJAX_WORKING_INDICATOR = new CssClass("AjaxWorkingIndicator", AjaxCssFiles.AJAX_STYLE);

	ICssClass HIDDEN = new CssClass("hidden", AjaxCssFiles.AJAX_STYLE);
}
