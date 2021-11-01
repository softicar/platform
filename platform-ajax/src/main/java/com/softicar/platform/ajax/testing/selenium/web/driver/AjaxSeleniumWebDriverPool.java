package com.softicar.platform.ajax.testing.selenium.web.driver;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.openqa.selenium.WebDriver;

public class AjaxSeleniumWebDriverPool {

	private static final int MAXIMUM_DRIVER_AGE = 30 * 1000;
	private final boolean reuseWebDrivers;
	private final Map<WebDriver, Long> freeDrivers;
	private volatile AjaxSeleniumWebDriverPoolCleanupThread cleanupThread;

	private AjaxSeleniumWebDriverPool() {

		this.reuseWebDrivers = AjaxSeleniumTestProperties.DRIVER_REUSE.getValue();
		this.freeDrivers = new IdentityHashMap<>();
		this.cleanupThread = null;

		Runtime.getRuntime().addShutdownHook(new AjaxSeleniumWebDriverPoolShutdownThread());
	}

	public static AjaxSeleniumWebDriverPool getInstance() {

		return LazyHolder.INSTANCE;
	}

	// -------------------- allocation -------------------- //

	public synchronized WebDriver allocateWebDriver() {

		WebDriver driver = getFreeDriverOrNull();
		if (driver != null) {
			Log.fverbose("reusing driver");
			return driver;
		} else {
			Log.fverbose("creating fresh driver");
			return createDriver();
		}
	}

	private WebDriver getFreeDriverOrNull() {

		Iterator<WebDriver> iterator = freeDrivers.keySet().iterator();
		if (iterator.hasNext()) {
			WebDriver driver = iterator.next();
			iterator.remove();
			return driver;
		} else {
			return null;
		}
	}

	private WebDriver createDriver() {

		return new AjaxSeleniumWebDriverFactory().createDriver();
	}

	// -------------------- release -------------------- //

	public synchronized void releaseWebDriver(WebDriver driver) {

		if (reuseWebDrivers) {
			freeDrivers.put(driver, System.currentTimeMillis());
			startCleanupThreadIfNecessary();
		} else {
			driver.quit();
		}
	}

	// -------------------- cleanup -------------------- //

	/**
	 * This method is called by {@link AjaxSeleniumWebDriverPoolCleanupThread}.
	 */
	protected synchronized void closeOldWebDrivers() {

		Iterator<Entry<WebDriver, Long>> iterator = freeDrivers.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<WebDriver, Long> entry = iterator.next();
			long age = System.currentTimeMillis() - entry.getValue();
			if (age > MAXIMUM_DRIVER_AGE) {
				closeWebDriverSafely(entry.getKey());
				iterator.remove();
			}
		}
		stopCleanupThreadIfNecessary();
	}

	private void startCleanupThreadIfNecessary() {

		if (cleanupThread == null) {
			this.cleanupThread = new AjaxSeleniumWebDriverPoolCleanupThread();
			cleanupThread.start();
		}
	}

	private void stopCleanupThreadIfNecessary() {

		if (cleanupThread != null && freeDrivers.isEmpty()) {
			cleanupThread.quit();
			this.cleanupThread = null;
		}
	}

	// -------------------- shutdown -------------------- //

	/**
	 * This method is called by {@link AjaxSeleniumWebDriverPoolShutdownThread}.
	 */
	protected synchronized void closeAllWebDriversSafely() {

		freeDrivers.keySet().forEach(this::closeWebDriverSafely);
		freeDrivers.clear();
	}

	private void closeWebDriverSafely(WebDriver driver) {

		try {
			driver.quit();
		} catch (Exception exception) {
			DevNull.swallow(exception);
		}
	}

	// -------------------- private -------------------- //

	/**
	 * Initialization-on-demand holder to create the instance only when
	 * {@link AjaxSeleniumWebDriverPool#getInstance()} is called.
	 */
	private static class LazyHolder {

		public static final AjaxSeleniumWebDriverPool INSTANCE = new AjaxSeleniumWebDriverPool();
	}
}
