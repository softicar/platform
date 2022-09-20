package com.softicar.platform.ajax.dom.button;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomKeys;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Test;

public class AjaxDomButtonTest extends AbstractAjaxSeleniumLowLevelTest {

	// FIXME temporary
//	@After
//	public void discardWebDriver() {
//
//		testEngine.discardWebDriver();
//	}

	private static final String SOME_TEXT = "Hey there!";
	private final AjaxDomButtonTestDiv testDiv;
	private final DomTextInput input;
	private final DomButton button;
	private final DomDiv output;

	public AjaxDomButtonTest() {

		this.testDiv = openTestNode(AjaxDomButtonTestDiv::new);
		this.input = testDiv.getInput();
		this.button = testDiv.getButton();
		this.output = testDiv.getOutput();
	}

	@Test
	public void testClick() {

		send(input, SOME_TEXT);
		click(button);

		waitForServer();
		assertEquals(SOME_TEXT, getText(output));
	}

	@Test
	public void testClickAtTopBorder() {

		send(input, SOME_TEXT);
		clickAt(testDiv.getButton(), 10, 0);

		waitForServer();
		assertEquals(SOME_TEXT, getText(output));
	}

	@Test
	public void testClickAtLeftBorder() {

		send(input, SOME_TEXT);
		clickAt(testDiv.getButton(), 0, 10);

		waitForServer();
		assertEquals(SOME_TEXT, getText(output));
	}

	@Test
	public void testEnter() {

		send(input, SOME_TEXT);
		send(button, Key.ENTER);

		waitForServer();
		assertEquals(SOME_TEXT, getText(output));
	}

	@Test
	public void testSpace() {

		send(input, SOME_TEXT);
		send(button, Key.SPACE);

		waitForServer();
		assertEquals(SOME_TEXT, getText(output));
	}

	@Test
	public void testWithSimulatedEvents() {

		send(input, SOME_TEXT);
		simulateKeyDown(button, DomKeys.ENTER);
		simulateKeyUp(button, DomKeys.ENTER);

		waitForServer();
		assertEquals(SOME_TEXT, getText(output));
	}

	@Test
	public void testCancelationOfEnterWithEscape() {

		send(input, SOME_TEXT);
		simulateKeyDown(button, DomKeys.ENTER);
		simulateKeyDown(button, DomKeys.ESCAPE);
		simulateKeyUp(button, DomKeys.ESCAPE);
		simulateKeyUp(button, DomKeys.ENTER);

		waitForServer();
		assertEquals("", getText(output));
	}
}
