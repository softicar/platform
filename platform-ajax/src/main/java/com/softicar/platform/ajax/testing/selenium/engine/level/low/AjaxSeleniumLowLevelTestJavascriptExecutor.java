package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.common.string.formatting.Formatting;
import java.util.function.Supplier;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

class AjaxSeleniumLowLevelTestJavascriptExecutor {

	private final Supplier<WebDriver> webDriverSupplier;

	public AjaxSeleniumLowLevelTestJavascriptExecutor(Supplier<WebDriver> webDriverSupplier) {

		this.webDriverSupplier = webDriverSupplier;
	}

	public Object execute(String javascript, Object...arguments) {

		return ((JavascriptExecutor) webDriverSupplier.get()).executeScript(Formatting.format(javascript, arguments));
	}
}
