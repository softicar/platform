package com.softicar.platform.ajax.testing.selenium.engine.level.high;

import com.softicar.platform.ajax.testing.selenium.engine.common.AbstractAjaxSeleniumTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineLazySetup;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.input.IDomStringInputNode;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Supplier;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * An {@link IDomTestEngine} based upon the Selenium framework. Allows for the
 * definition and execution of high-level UI tests which mimic the interactions
 * and observations of a human user.
 * <p>
 * Uses a web server, and remote-controlled web browsers, which are started on
 * the machine that runs the tests. The web server is created on the fly, and
 * provides a servlet which handles incoming requests in a separate
 * {@link Thread}. The web browsers are started in Docker containers (more
 * precisely, in Selenium Node containers which are part of a Selenium Grid).
 * <p>
 * <b>Important:</b> In order to run tests with this engine, <b>docker</b> and
 * <b>docker-compose</b> must be installed on the executing machine. Those are
 * required for this engine to manage the life cycle of the Selenium Grid.
 * <p>
 * The engine extends {@link TestRule}, so that it can be added to a test class
 * as a JUnit4 {@link Rule}.
 * <p>
 * {@link DomDocumentTestEngine} and {@link AjaxSeleniumTestEngine} have
 * individual advantages and disadvantages, when compared with one another:
 *
 * <pre>
 * +------------------------+-------+-----------+
 * | Engine                 | Speed | Precision |
 * +------------------------+-------+-----------+
 * | DomDocumentTestEngine  |  fast |    low    |
 * +------------------------+-------+-----------+
 * | AjaxSeleniumTestEngine |  slow |    high   |
 * +------------------------+-------+-----------+
 * </pre>
 *
 * <b>Speed</b> refers to the total execution time, when running tests with the
 * given engine (factoring both setup and execution efforts). <b>Precision</b>
 * refers to how closely the automated tests would replicate the interactions
 * and observations of a human user who would manually execute the same steps
 * using a web browser.
 * <p>
 * Note that this test engine is unsuitable to test low-level AJAX framework
 * functionality. For example, it lacks the notion of pixel-based coordinates,
 * and related concepts. To implement low-level AJAX tests, use a different,
 * internal test engine instead.
 *
 * @author Alexander Schmidt
 * @see DomDocumentTestEngine
 */
public class AjaxSeleniumTestEngine extends AbstractAjaxSeleniumTestEngine implements IDomTestEngine {

	private final IDomTestEngineLazySetup setup;

	public AjaxSeleniumTestEngine() {

		this.setup = AjaxSeleniumTestEngineLazySetup.createAndRegister(testEnvironment::openTestNode);
	}

	@Override
	public void setNodeSupplier(Supplier<IDomNode> nodeSupplier) {

		setup.setNodeSupplier(nodeSupplier);
	}

	@Override
	public IDomNode getBodyNode() {

		return setup.getBodyNode();
	}

	@Override
	public void sendEvent(IDomNode node, DomEventType eventType) {

		screenshotQueue.addNewScreenshot("sendEvent-call");

		if (eventType == DomEventType.CLICK) {
			clickAtNodeCoordinates(node);
		} else if (eventType == DomEventType.ENTER) {
			screenshotQueue.addNewScreenshot("sendEvent-enter-wait");
			getWebElement(node).sendKeys(Keys.ENTER);
		} else {
			throw new RuntimeException("Not implemented.");
		}

		waitForServer();

		screenshotQueue.addNewScreenshot("sendEvent-return");
	}

	@Override
	public void setInputValue(IDomStringInputNode node, String text) {

		screenshotQueue.addNewScreenshot("setInputValue-call");

		var webElement = getWebElement(node);
		webElement.clear();
		webElement.sendKeys(text);
		webElement.sendKeys(Keys.TAB);

		screenshotQueue.addNewScreenshot("setInputValue-wait");
		waitForServer();

		screenshotQueue.addNewScreenshot("setInputValue-return");
	}

	@Override
	public String getInputValue(IDomStringInputNode node) {

		WebElement webElement = getWebElement(node);
		return webElement.getAttribute("value");
	}

	/**
	 * Clicks at the coordinates of the given {@link IDomNode}.
	 * <p>
	 * Does <b>not</b> click the associated {@link WebElement} directly, in
	 * order to support click-intercepting overlay elements.
	 * <p>
	 * If there is a click-intercepting overlay above the {@link WebElement} to
	 * click, an attempt to click the the associated {@link WebElement} directly
	 * (via {@link WebElement#click()}) would cause Selenium to throw a
	 * {@link WebDriverException}. Clicking at the coordinates of the
	 * {@link WebElement} circumvents that {@link WebDriverException}.
	 *
	 * @param node
	 *            the {@link IDomNode} to click (never <i>null</i>)
	 */
	private void clickAtNodeCoordinates(IDomNode node) {

		WebElement webElement = getWebElement(node);
		AjaxSeleniumTestPoint clickPoint = createPoint(webElement);
		screenshotQueue//
			.addNewScreenshot("sendEvent-click-x%s-y%s".formatted(clickPoint.getX(), clickPoint.getY()))
			.drawPointMarker(clickPoint);
		new Actions(getWebDriver()).moveToElement(webElement).click().perform();
	}

	private AjaxSeleniumTestPoint createPoint(WebElement webElement) {

		return new AjaxSeleniumTestPoint(webElement.getLocation().getX(), webElement.getLocation().getY());
	}
}
