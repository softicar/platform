package com.softicar.platform.ajax.testing.selenium.engine.common;

import org.openqa.selenium.Alert;

public class AjaxSeleniumTestNativeDialog implements IAjaxSeleniumTestNativeDialog {

	private final Alert alert;

	public AjaxSeleniumTestNativeDialog(Alert alert) {

		this.alert = alert;
	}

	@Override
	public String getText() {

		return alert.getText();
	}

	@Override
	public void accept() {

		alert.accept();
	}

	@Override
	public void dismiss() {

		alert.dismiss();
	}

	@Override
	public void sendKeys(String text) {

		alert.sendKeys(text);
	}
}
