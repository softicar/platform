package com.softicar.platform.ajax.testing.selenium.web.driver;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.common.core.retry.RetryingSupplier;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AjaxSeleniumWebDriverFactory {

	protected WebDriver createDriver() {

		WebDriver driver = createRemoteDriver();
		configureTimeouts(driver);
		return driver;
	}

	private WebDriver createRemoteDriver() {

		return new RetryingSupplier<>(this::tryToCreateRemoteDriver)//
			.setRetryCount(AjaxSeleniumTestProperties.DRIVER_CREATION_RETRY_COUNT.getValue())
			.setRetryDelayMillis(AjaxSeleniumTestProperties.DRIVER_CREATION_RETRY_DELAY.getValue())
			.get();
	}

	private WebDriver tryToCreateRemoteDriver() {

		var capabilities = getBrowserCapabilities();
		RemoteWebDriver driver = new RemoteWebDriver(getRemoteDriverUrl(), capabilities);
		driver.setFileDetector(new LocalFileDetector());
		return driver;
	}

	private static void configureTimeouts(WebDriver driver) {

		Timeouts timeouts = driver.manage().timeouts();
		Optional//
			.ofNullable(AjaxSeleniumTestProperties.DRIVER_IMPLICIT_WAIT_TIMEOUT.getValue())
			.ifPresent(timeout -> timeouts.implicitlyWait(Duration.ofMillis(timeout)));
		Optional//
			.ofNullable(AjaxSeleniumTestProperties.DRIVER_PAGE_LOAD_TIMEOUT.getValue())
			.ifPresent(timeout -> timeouts.pageLoadTimeout(Duration.ofMillis(timeout)));
		Optional//
			.ofNullable(AjaxSeleniumTestProperties.DRIVER_SCRIPT_TIMEOUT.getValue())
			.ifPresent(timeout -> timeouts.scriptTimeout(Duration.ofMillis(timeout)));
	}

	private static Capabilities getBrowserCapabilities() {

		return new ChromeOptions()//
			.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT)
			// Avoid a Chrome memory issue, as per https://stackoverflow.com/a/53970825
			// TODO determine whether this has a measurable impact on test execution time
			.addArguments("--no-sandbox", "--disable-dev-shm-usage");
	}

	private static URL getRemoteDriverUrl() {

		try {
			return new URL(
				String
					.format(//
						"http://%s:%s/wd/hub",
						AjaxSeleniumTestProperties.HUB_IP.getValue(),
						AjaxSeleniumTestProperties.HUB_PORT_EXTERNAL.getValue()));
		} catch (MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
