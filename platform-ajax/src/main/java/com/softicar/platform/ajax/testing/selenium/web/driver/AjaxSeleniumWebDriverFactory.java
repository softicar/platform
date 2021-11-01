package com.softicar.platform.ajax.testing.selenium.web.driver;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.common.core.retry.RetryingSupplier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
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

		Capabilities capabilities = getFirefoxCapabilities();
		RemoteWebDriver driver = new RemoteWebDriver(getRemoteDriverUrl(), capabilities);
		driver.setFileDetector(new LocalFileDetector());
		return driver;
	}

	private static void configureTimeouts(WebDriver driver) {

		Timeouts timeouts = driver.manage().timeouts();
		Optional//
			.ofNullable(AjaxSeleniumTestProperties.DRIVER_IMPLICIT_WAIT_TIMEOUT.getValue())
			.ifPresent(timeout -> timeouts.implicitlyWait(timeout, TimeUnit.MILLISECONDS));
		Optional//
			.ofNullable(AjaxSeleniumTestProperties.DRIVER_PAGE_LOAD_TIMEOUT.getValue())
			.ifPresent(timeout -> timeouts.pageLoadTimeout(timeout, TimeUnit.MILLISECONDS));
		Optional//
			.ofNullable(AjaxSeleniumTestProperties.DRIVER_SCRIPT_TIMEOUT.getValue())
			.ifPresent(timeout -> timeouts.setScriptTimeout(timeout, TimeUnit.MILLISECONDS));
	}

	private static Capabilities getFirefoxCapabilities() {

		FirefoxOptions capabilities = new FirefoxOptions();
		capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		return capabilities;
	}

	private static URL getRemoteDriverUrl() {

		try {
			return new URL(
				String
					.format(//
						"http://%s:%s/wd/hub",
						AjaxSeleniumTestProperties.HUB_IP.getValue(),
						AjaxSeleniumTestProperties.HUB_PORT.getValue()));
		} catch (MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
