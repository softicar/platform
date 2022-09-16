package com.softicar.platform.ajax.testing.selenium.web.driver;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.common.core.logging.Log;
import java.util.Collection;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

/**
 * Handles the allocation, release and disposal of a single {@link WebDriver}
 * instance.
 *
 * @author Oliver Richers
 */
public class AjaxSeleniumWebDriverController {

	private static final AjaxSeleniumWebDriverHistory WEB_DRIVER_HISTORY = new AjaxSeleniumWebDriverHistory();
	private WebDriver webDriver;

	public AjaxSeleniumWebDriverController() {

		this.webDriver = null;
	}

	public WebDriver getWebDriver() {

		if (webDriver == null) {
			this.webDriver = AjaxSeleniumWebDriverPool.getInstance().allocateWebDriver();
			this.webDriver.manage().window().setSize(getResolution());
		}
		return webDriver;
	}

	public void releaseWebDriver(Description description) {

		if (webDriver != null) {
			WEB_DRIVER_HISTORY.registerUsage(webDriver, description);
			AjaxSeleniumWebDriverPool.getInstance().releaseWebDriver(webDriver);
			this.webDriver = null;
		}
	}

	public void discardWebDriver() {

		logWebDriverHistory();

		if (webDriver != null) {
			webDriver.quit();
			this.webDriver = null;
		}
	}

	private void logWebDriverHistory() {

		Log.fwarning("Discarding web driver.");
		Collection<Description> descriptions = WEB_DRIVER_HISTORY.getUsageList(webDriver);
		if (descriptions.isEmpty()) {
			Log.fwarning("Web driver was not previously used by any test.");
		} else {
			Log.fwarning("Web driver was previously used by:");
			descriptions.forEach(description -> Log.fwarning("\t- %s", description.getDisplayName()));
		}
	}

	private Dimension getResolution() {

		return new Dimension(
			AjaxSeleniumTestProperties.DRIVER_WINDOW_RESOLUTION_X.getValue(),
			AjaxSeleniumTestProperties.DRIVER_WINDOW_RESOLUTION_Y.getValue());
	}
}
