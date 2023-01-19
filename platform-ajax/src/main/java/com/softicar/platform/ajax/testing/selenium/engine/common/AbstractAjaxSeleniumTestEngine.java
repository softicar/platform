package com.softicar.platform.ajax.testing.selenium.engine.common;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestEnvironment;
import com.softicar.platform.ajax.testing.selenium.grid.AjaxSeleniumGridController;
import com.softicar.platform.ajax.testing.selenium.screenshot.AjaxSeleniumScreenshotQueue;
import com.softicar.platform.ajax.testing.selenium.web.driver.AjaxSeleniumWebDriverController;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.dom.node.IDomNode;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Base class of Selenium based UI test engines which can be used in a JUnit
 * {@link Rule} annotation.
 * <p>
 * Manages common aspects of Selenium based UI test engines, including:
 * <ul>
 * <li>the life cycle of the a temporary web server</li>
 * <li>the connection of a Selenium {@link WebDriver} to that server</li>
 * <li>the creation of screenshots during test execution</li>
 * </ul>
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractAjaxSeleniumTestEngine extends TestWatcher implements IAjaxSeleniumTestEngineWaitMethods {

	protected final AjaxSeleniumTestEnvironment testEnvironment;
	protected final AjaxSeleniumWebDriverController webDriverController;
	protected final AjaxSeleniumScreenshotQueue screenshotQueue;
	private final AjaxSeleniumTestServerWaiter serverWaiter;

	public AbstractAjaxSeleniumTestEngine() {

		this.testEnvironment = new AjaxSeleniumTestEnvironment(this::navigateTo);
		this.webDriverController = new AjaxSeleniumWebDriverController();
		this.screenshotQueue = new AjaxSeleniumScreenshotQueue(this::getWebDriver);
		this.serverWaiter = new AjaxSeleniumTestServerWaiter(this::getWebDriver);

		// "defaultLogLevel" refers to a system property of the employed logging library.
		System.setProperty("defaultLogLevel", "ERROR");

		// Be less verbose when allocating web drivers.
		Logger.getLogger("org.openqa.selenium").setLevel(Level.WARNING);

		AjaxSeleniumGridController.getInstance().startup();
		AjaxSeleniumGridController.getInstance().registerRuntimeShutdownHook();
	}

	public void setUrlParameter(String name, String value) {

		testEnvironment.setUrlParameter(name, value);
	}

	@Override
	public void waitForServer(Duration timeout) {

		serverWaiter.waitForServer(timeout);
	}

	@Override
	public void waitForDocumentReady() {

		waitUntil(this::isDocumentReady);
	}

	@Override
	protected void finished(Description description) {

		screenshotQueue.writeAll(description);
		webDriverController.releaseWebDriver(description);
		testEnvironment.executeAfterTest();
	}

	@Override
	protected void failed(Throwable throwable, Description description) {

		Log.fwarning("Failed: %s", description.getDisplayName());

		screenshotQueue.writeAll(description);

		// driver is probably bad, so drop it
		webDriverController.discardWebDriver();
	}

	public String getCurrentUrl() {

		return getWebDriver().getCurrentUrl();
	}

	public boolean isDisplayed(IDomNode node) {

		return getWebElement(node).isDisplayed();
	}

	protected WebElement getWebElement(IDomNode node) {

		return getWebDriver().findElement(By.id(node.getNodeIdString()));
	}

	protected WebDriver getWebDriver() {

		return webDriverController.getWebDriver();
	}

	private void navigateTo(String url) {

		getWebDriver().get(url);
	}

	private boolean isDocumentReady() {

		String readyState = (String) getJavascriptExecutor().executeScript("return document.readyState;");
		return "complete".equals(readyState);
	}

	private JavascriptExecutor getJavascriptExecutor() {

		return (JavascriptExecutor) webDriverController.getWebDriver();
	}
}
