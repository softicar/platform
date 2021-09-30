package com.softicar.platform.ajax.input.text;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Test;

public class AjaxTextInputTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final String SOME_TEXT = "foo bar";
	private static final String SOME_SPECIAL_TEXT = " Ä\tßč ";
	private final AjaxTextInputTestDiv testDiv;
	private final DomTextInput input;
	private final DomButton button;

	public AjaxTextInputTest() {

		this.testDiv = openTestNode(AjaxTextInputTestDiv::new);
		this.input = testDiv.getInput();
		this.button = testDiv.getButton();
	}

	@Test
	public void testInitialDefaultValue() {

		assertEquals("", getAttributeValue(input, "value"));
		assertEquals("", testDiv.getInput().getValue());
	}

	@Test
	public void testEnteringValue() {

		send(input, SOME_TEXT);
		click(button);

		waitForServer();
		assertEquals(SOME_TEXT, testDiv.getInput().getValue());
	}

	@Test
	public void testSpecialCharactersValue() {

		send(input, SOME_SPECIAL_TEXT);
		click(button);

		waitForServer();
		assertEquals(SOME_SPECIAL_TEXT, testDiv.getInput().getValue());
	}
}
