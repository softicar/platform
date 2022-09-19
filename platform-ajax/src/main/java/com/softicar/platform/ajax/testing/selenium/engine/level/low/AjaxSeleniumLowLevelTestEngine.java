package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.ajax.testing.selenium.engine.common.AbstractAjaxSeleniumTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.high.AjaxSeleniumTestExecutionEngine;
import com.softicar.platform.dom.DomProperties;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.Rule;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * A Selenium based UI test engine which can be used via a JUnit {@link Rule}
 * annotation in internal framework functionality tests.
 * <p>
 * Consists of distinct parts which provide functionality to interact with the
 * UI-under-test, and examine its output.
 * <p>
 * This engine is <b>not</b> meant to be used beyond the AJAX framework itself.
 * It provides complex, low-level and error-prone features that should only be
 * relevant to test the basic UI elements and functionality which are provided
 * by the framework.
 * <p>
 * This engine can <b>not</b> be used as a drop-in replacement for
 * {@link AjaxSeleniumTestExecutionEngine}, since it does not implement
 * {@link IDomTestExecutionEngine}. In any project beyond the AJAX framework
 * itself, {@link AjaxSeleniumTestExecutionEngine} should be used instead of
 * this engine.
 *
 * @author Alexander Schmidt
 * @see AjaxSeleniumTestExecutionEngine
 */
public class AjaxSeleniumLowLevelTestEngine extends AbstractAjaxSeleniumTestEngine {

	private final AjaxSeleniumLowLevelTestEngineInput input;
	private final AjaxSeleniumLowLevelTestEngineOutput output;
	private final AjaxSeleniumLowLevelTestEngineViewport viewport;
	private final AjaxSeleniumLowLevelTestEngineEventSimulator eventSimulator;
	private final AjaxSeleniumLowLevelTestEngineAutoCompleteExtension autoCompleteExtension;

	public AjaxSeleniumLowLevelTestEngine() {

		System.setProperty(DomProperties.TEST_MODE.getPropertyName().toString(), "true");

		var parameters = getEngineParameters();
		this.input = new AjaxSeleniumLowLevelTestEngineInput(parameters);
		this.output = new AjaxSeleniumLowLevelTestEngineOutput(parameters);
		this.viewport = new AjaxSeleniumLowLevelTestEngineViewport(parameters);
		this.eventSimulator = new AjaxSeleniumLowLevelTestEngineEventSimulator(parameters);
		this.autoCompleteExtension = new AjaxSeleniumLowLevelTestEngineAutoCompleteExtension(parameters);
	}

	/**
	 * Creates an {@link IDomNode} using the given factory, and initializes it
	 * as the UI-under-test.
	 *
	 * @param <T>
	 *            the exact type of the {@link IDomNode}
	 * @param factory
	 *            the factory that creates the {@link IDomNode}
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	public final <T extends IDomNode> T openTestNode(Supplier<T> factory) {

		T testNode = testEnvironment.openTestNode(factory);
		viewport
			.setViewportSize(//
				AjaxSeleniumTestProperties.DRIVER_WINDOW_RESOLUTION_X.getValue(),
				AjaxSeleniumTestProperties.DRIVER_WINDOW_RESOLUTION_Y.getValue());
		return testNode;
	}

	/**
	 * Creates an {@link IDomNode} using the given factory, and initializes it
	 * as the UI-under-test.
	 *
	 * @param <T>
	 *            the exact type of the {@link IDomNode}
	 * @param factory
	 *            the factory that creates the {@link IDomNode}
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	public final <T extends IDomNode> T openTestNode(Function<IAjaxDocument, T> factory) {

		return testEnvironment.openTestNode(factory);
	}

	/**
	 * Adds a new screenshot to the internal screenshot queue.
	 *
	 * @param fileName
	 *            the screenshot file name (never <i>null</i>)
	 */
	public void takeScreenshot(String fileName) {

		screenshotQueue.addNewScreenshot(fileName);
	}

	/**
	 * Returns the {@link AjaxSeleniumLowLevelTestEngineInput} of this
	 * {@link AjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link AjaxSeleniumLowLevelTestEngineInput} (never
	 *         <i>null</i>)
	 */
	public AjaxSeleniumLowLevelTestEngineInput getInput() {

		return input;
	}

	/**
	 * Returns the {@link AjaxSeleniumLowLevelTestEngineOutput} of this
	 * {@link AjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link AjaxSeleniumLowLevelTestEngineOutput} (never
	 *         <i>null</i>)
	 */
	public AjaxSeleniumLowLevelTestEngineOutput getOutput() {

		return output;
	}

	/**
	 * Returns the {@link AjaxSeleniumLowLevelTestEngineViewport} of this
	 * {@link AjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link AjaxSeleniumLowLevelTestEngineViewport} (never
	 *         <i>null</i>)
	 */
	public AjaxSeleniumLowLevelTestEngineViewport getViewport() {

		return viewport;
	}

	/**
	 * Returns the {@link AjaxSeleniumLowLevelTestEngineEventSimulator} of this
	 * {@link AjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link AjaxSeleniumLowLevelTestEngineEventSimulator} (never
	 *         <i>null</i>)
	 */
	public AjaxSeleniumLowLevelTestEngineEventSimulator getEventSimulator() {

		return eventSimulator;
	}

	/**
	 * Returns the {@link AjaxSeleniumLowLevelTestEngineAutoCompleteExtension}
	 * of this {@link AjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link AjaxSeleniumLowLevelTestEngineAutoCompleteExtension}
	 *         (never <i>null</i>)
	 */
	public AjaxSeleniumLowLevelTestEngineAutoCompleteExtension getAutoCompleteInput() {

		return autoCompleteExtension;
	}

	public void discardWebDriver() {

		webDriverController.discardWebDriver();
	}

	@Override
	protected void failed(Throwable throwable, Description description) {

		takeScreenshot("final-state");
		super.failed(throwable, description);
	}

	private AjaxSeleniumLowLevelTestEngineParameters getEngineParameters() {

		return new AjaxSeleniumLowLevelTestEngineParameters()
			.setEngine(this)
			.setWebDriverSupplier(this::getWebDriver)
			.setWebElementResolver(this::getWebElement)
			.setSessionTimeoutDivSupplier(this::getSessionTimeoutDiv);
	}

	private WebElement getSessionTimeoutDiv() {

		return getWebDriver().findElement(By.className(AjaxCssClasses.AJAX_SESSION_TIMEOUT_DIALOG.getName()));
	}
}
