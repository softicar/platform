package com.softicar.platform.ajax.testing.selenium.web.driver;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import org.openqa.selenium.WebDriver;

/**
 * This thread closes all old drivers in {@link AjaxSeleniumWebDriverPool}.
 * <p>
 * This thread is registered by {@link AjaxSeleniumWebDriverPool} as JVM
 * shutdown hook. It ensures that all {@link WebDriver} instances are closed on
 * JVM shutdown.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AjaxSeleniumWebDriverPoolCleanupThread extends Thread {

	private static final long CLEANUP_INTERVAL = 500;
	private volatile boolean done;

	public AjaxSeleniumWebDriverPoolCleanupThread() {

		this.done = false;
		setDaemon(true);
	}

	@Override
	public void run() {

		while (!done) {
			AjaxSeleniumWebDriverPool.getInstance().closeOldWebDrivers();
			Sleep.sleep(CLEANUP_INTERVAL);
		}
	}

	public void quit() {

		this.done = true;
	}
}
