package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import org.junit.Test;

public class AjaxAutoCompleteEnterKeyTest extends AbstractAjaxAutoCompleteStringTest {

	public AjaxAutoCompleteEnterKeyTest() {

		openTestInput(i -> i.getEngine().addValues(VALUE1, VALUE2, VALUE3));
	}

	@Test
	public void testEnterKeySelection() {

		send(inputField, Key.DOWN);
		waitForServer();
		send(inputField, Key.DOWN, Key.DOWN, Key.ENTER);
		waitForServer();

		assertInputValue(VALUE2);
	}

	@Test
	public void testEnterKeyWithInvalidValue() {

		// enter invalid input and press ENTER
		send(inputField, INVALID_INPUT);
		waitForServer();
		send(inputField, Key.ENTER);
		waitForServer();

		// assert popup is not closed but input keeps focus
		assertTrue(isAutoCompletePopupDisplayed());
		assertFocused(inputField);
	}

	@Test
	public void testEnterKeyWithClosedPopupWithValidButIncompleteValue() {

		// enter valid but incomplete input
		send(inputField, INCOMPLETE_VALUE1_NAME);
		waitForServer();

		// close popup using ESCAPE
		send(inputField, Key.ESCAPE);
		waitForServer();
		assertFalse(isAutoCompletePopupDisplayed());

		// now press ENTER
		send(inputField, Key.ENTER);
		waitForServer();

		// assert popup is closed and input has full value name
		assertFalse(isAutoCompletePopupDisplayed());
		assertFocused(inputField);
		assertEquals(VALUE1.getName(), getAttributeValue(inputField, "value"));
	}
}
