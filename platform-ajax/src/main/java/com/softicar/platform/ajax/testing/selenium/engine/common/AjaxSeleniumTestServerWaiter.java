package com.softicar.platform.ajax.testing.selenium.engine.common;

import java.util.function.Supplier;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;

/**
 * Ensures that the server finished executing all AJAX requests.
 *
 * @author Oliver Richers
 */
class AjaxSeleniumTestServerWaiter {

	private static final String FINISHED_VARIABLE = "document.documentElement.dataset.finished";
	private static final String WAIT_SCRIPT = createWaitScript();
	private final Supplier<WebDriver> webDriverSupplier;
	private ScriptKey waitScriptKey;

	public AjaxSeleniumTestServerWaiter(Supplier<WebDriver> webDriverSupplier) {

		this.webDriverSupplier = webDriverSupplier;
		this.waitScriptKey = null;
	}

	public void waitForServer() {

		getJavascriptExecutor().executeScript(getWaitScriptKey());

		while (true) {
			String finished = (String) getJavascriptExecutor().executeScript("return %s;".formatted(FINISHED_VARIABLE));
			if (Boolean.valueOf(finished)) {
				break;
			}
		}
	}

	private JavascriptExecutor getJavascriptExecutor() {

		return (JavascriptExecutor) webDriverSupplier.get();
	}

	private ScriptKey getWaitScriptKey() {

		if (waitScriptKey == null) {
			this.waitScriptKey = getJavascriptExecutor().pin(WAIT_SCRIPT);
		}
		return waitScriptKey;
	}

	private static String createWaitScript() {

		return new StringBuilder()//
			.append("%s = false;".formatted(FINISHED_VARIABLE))
			.append("function waitForServer() {;")
			.append("	if(AJAX_REQUEST_QUEUE.hasRequests()) {")
			.append("		setTimeout(waitForServer, 0);")
			.append("	} else {")
			.append("		%s = true;".formatted(FINISHED_VARIABLE))
			.append("	}")
			.append("};")
			.append("setTimeout(waitForServer, 0);")
			.toString();
	}
}
