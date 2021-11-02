package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import org.junit.Test;

public class AjaxAutoCompleteEnterKeyTest extends AbstractAjaxAutoCompleteStringTest {

	public AjaxAutoCompleteEnterKeyTest() {

		openTestInput(i -> i.getEngine().addItems(ITEM1, ITEM2, ITEM3));
	}

	@Test
	public void testEnterKeySelection() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.DOWN, Key.ENTER);

		assertInputValue(ITEM2);
	}

	@Test
	public void testEnterKeyWithInvalidValue() {

		// enter invalid input and press ENTER
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);

		// assert popup is not closed but input keeps focus
		assertTrue(isAutoCompletePopupDisplayed());
		assertFocused(inputField);
	}

	@Test
	public void testEnterKeyWithClosedPopupWithValidButIncompleteValue() {

		// enter valid but incomplete input
		send(inputField, INCOMPLETE_ITEM1_NAME);
		waitForAutoCompletePopup();

		// close popup using ESCAPE
		send(inputField, Key.ESCAPE);
		assertFalse(isAutoCompletePopupDisplayed());

		// now press ENTER
		send(inputField, Key.ENTER);
		waitForServer();

		// assert popup is closed and input has full item name
		assertFalse(isAutoCompletePopupDisplayed());
		assertFocused(inputField);
		assertEquals(INCOMPLETE_ITEM1_NAME, getAttributeValue(inputField, "value"));
	}
}
