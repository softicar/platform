package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.AjaxCssClasses;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.common.AbstractAjaxSeleniumTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.high.AjaxSeleniumTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineEventSimulator;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineOutput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineViewport;
import com.softicar.platform.dom.DomProperties;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
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
 * This engine is <b>not</b> meant to be used beyond the AJAX framework itself.
 * It provides complex, low-level and error-prone features that should only be
 * relevant to test the basic UI elements and functionality which are provided
 * by the framework.
 * <p>
 * This engine can <b>not</b> be used as a drop-in replacement for
 * {@link AjaxSeleniumTestEngine}, since it does not implement
 * {@link IDomTestEngine}. In any project beyond the AJAX framework itself,
 * {@link AjaxSeleniumTestEngine} should be used instead of this engine.
 *
 * @author Alexander Schmidt
 * @see AjaxSeleniumTestEngine
 */
public class AjaxSeleniumLowLevelTestEngine extends AbstractAjaxSeleniumTestEngine implements IAjaxSeleniumLowLevelTestEngine {

	private final IAjaxSeleniumLowLevelTestEngineInput input;
	private final AjaxSeleniumLowLevelTestEngineOutput output;
	private final IAjaxSeleniumLowLevelTestEngineViewport viewport;
	private final IAjaxSeleniumLowLevelTestEngineEventSimulator eventSimulator;
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

	@Override
	public final <T extends IDomNode> T openTestNode(Supplier<T> factory) {

		return testEnvironment.openTestNode(factory);
	}

	@Override
	public final <T extends IDomNode> T openTestNode(Function<IAjaxDocument, T> factory) {

		return testEnvironment.openTestNode(factory);
	}

	@Override
	public void takeScreenshot(String fileName) {

		screenshotQueue.addNewScreenshot(fileName);
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngineInput getInput() {

		return input;
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngineOutput getOutput() {

		return output;
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngineViewport getViewport() {

		return viewport;
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngineEventSimulator getEventSimulator() {

		return eventSimulator;
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension getAutoCompleteExtension() {

		return autoCompleteExtension;
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
