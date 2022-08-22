package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import org.junit.Test;

public class AjaxAutoCompleteEscapeKeyTest extends AbstractAjaxAutoCompleteStringTest {

	public AjaxAutoCompleteEscapeKeyTest() {

		openTestInput(i -> i.getEngine().addItems(ITEM1, ITEM2, ITEM3));
	}

	@Test
	public void testEscapeWithEmptyValue() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);

		assertFalse(isAutoCompletePopupDisplayed());
		indicator.assertIndicatesNothing();
		assertEquals("", getAttributeValue(inputField, "value"));
	}

	@Test
	public void testEscapeWithAmbiguousValue() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);

		assertFalse(isAutoCompletePopupDisplayed());
		indicator.assertIndicatesAmbiguous();
		assertEquals(AMBIGUOUS_INPUT, getAttributeValue(inputField, "value"));
	}

	// this is a test for #37218
	@Test
	public void testEscapeWithSelectedValue() {

		// select specific item
		click(inputField);
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN);
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		// now press escape
		send(inputField, Key.ESCAPE);
		assertEquals(ITEM1.getName(), getAttributeValue(inputField, "value"));
	}

	// this is a test for #37476
	@Test
	public void testEscapeWithValidValue() {

		// enter valid item name
		click(inputField);
		send(inputField, ITEM1.getName());
		waitForAutoCompletePopup();

		// type ESCAPE
		send(inputField, Key.ESCAPE);
		waitForAutoCompletePopupToHide();
		indicator.assertIndicatesNothing();

		// now leave and re-enter
		clickBodyNode();
		click(inputField);
		indicator.assertIndicatesNothing();
	}
}
