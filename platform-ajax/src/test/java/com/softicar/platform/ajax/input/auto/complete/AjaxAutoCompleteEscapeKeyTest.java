package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import org.junit.Test;

public class AjaxAutoCompleteEscapeKeyTest extends AbstractAjaxAutoCompleteStringTest {

	public AjaxAutoCompleteEscapeKeyTest() {

		openTestInput(i -> i.getEngine().addValues(VALUE1, VALUE2, VALUE3));
	}

	@Test
	public void testEscapeWithEmptyValue() {

		send(inputField, Key.DOWN);
		waitForServer();
		send(inputField, Key.ESCAPE);
		waitForServer();

		assertFalse(isAutoCompletePopupDisplayed());
		indicator.assertIndicatesNothing();
		assertEquals("", getAttributeValue(inputField, "value"));
	}

	@Test
	public void testEscapeWithAmbiguousValue() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForServer();
		send(inputField, Key.ESCAPE);
		waitForServer();

		assertFalse(isAutoCompletePopupDisplayed());
		indicator.assertIndicatesAmbiguous();
		assertEquals(AMBIGUOUS_INPUT, getAttributeValue(inputField, "value"));
	}

	@Test
	public void testEscapeWithSelectedValue() {

		// select specific value
		click(inputField);
		send(inputField, Key.DOWN);
		waitForServer();
		send(inputField, Key.DOWN);
		send(inputField, Key.ENTER);
		waitForServer();

		// now press escape
		send(inputField, Key.ESCAPE);
		waitForServer();

		assertEquals(VALUE1.getName(), getAttributeValue(inputField, "value"));
	}

	@Test
	public void testEscapeWithValidValue() {

		// enter valid value name
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		// type ESCAPE
		send(inputField, Key.ESCAPE);
		waitForServer();
		indicator.assertIndicatesNothing();

		// now leave and re-enter
		clickBodyNode();
		waitForServer();
		click(inputField);
		waitForServer();

		indicator.assertIndicatesNothing();
	}
}
