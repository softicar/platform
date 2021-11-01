package com.softicar.platform.ajax.testing.selenium.web.driver;

import org.openqa.selenium.WebDriver;

/**
 * This thread closes all open drivers in {@link AjaxSeleniumWebDriverPool}.
 * <p>
 * This thread is registered by {@link AjaxSeleniumWebDriverPool} as JVM
 * shutdown hook. It ensures that all {@link WebDriver} instances are closed on
 * JVM shutdown.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AjaxSeleniumWebDriverPoolShutdownThread extends Thread {

	@Override
	public void run() {

		AjaxSeleniumWebDriverPool.getInstance().closeAllWebDriversSafely();
	}
}
