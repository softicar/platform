package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import org.junit.Test;

/**
 * Tests auto-complete inputs that are listening for change events.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteChangeEventTest extends AbstractAjaxAutoCompleteStringTest {

	public AjaxAutoCompleteChangeEventTest() {

		openTestInput(i -> {
			i.getEngine().addValues(VALUE1, VALUE2, VALUE3);
			i.listenToChange();
		});
	}

	@Test
	public void testChangeEventWithEnter() {

		openPopup();
		send(inputField, Key.DOWN, Key.ENTER);
		waitForServer();

		assertEventAndValue(VALUE1);
	}

	@Test
	public void testChangeEventWithTab() {

		openPopup();
		send(inputField, Key.DOWN, Key.TAB);
		waitForServer();

		assertEventAndValue(VALUE1);
	}

	@Test
	public void testChangeEventWithClick() {

		openPopup();
		clickAutoCompleteValue(VALUE3);
		waitForServer();

		assertEventAndValue(VALUE3);
	}

	@Test
	public void testChangeEventWithEscapeAfterIllegalInput() {

		openPopup();
		send(inputField, INVALID_INPUT);
		waitForServer();
		send(inputField, Key.ESCAPE);
		waitForServer();

		inputDiv.assertNoEvent();
		indicator.assertIndicatesIllegal();
	}

	@Test
	public void testChangeEventWithInvalidValueAndBlur() {

		openPopup();
		send(inputField, INVALID_INPUT);
		waitForServer();
		clickBodyNode();
		waitForServer();

		inputDiv.assertNoEvent();
		indicator.assertIndicatesIllegal();
	}

	@Test
	public void testChangeEventWithValidValueAndBlur() {

		openPopup();
		send(inputField, VALUE3.getName());
		waitForServer();
		clickBodyNode();
		waitForServer();

		assertEventAndValue(VALUE3);
	}

	// -------------------- submit without popup -------------------- //

	@Test
	public void testSubmitWithoutPopup() {

		// input and submit some value
		openPopup();
		send(inputField, VALUE1.getName());
		waitForServer();
		send(inputField, Key.ENTER);
		waitForServer();
		inputDiv.clearEvents();

		// now clear input and close popup with escape, and assert that empty value was submitted
		while (!getAttributeValue(inputField, "value").isEmpty()) {
			send(inputField, Key.BACK_SPACE);
		}
		waitForServer();
		send(inputField, Key.ESCAPE);
		waitForServer();
		indicator.assertIndicatesNothing();
		inputDiv.assertOneEvent();

		// now try to submit empty value with ENTER
		inputDiv.clearEvents();
		send(inputField, Key.ENTER);
		waitForServer();

		// assert that empty value was not re-submitted
		inputDiv.assertNoEvent();
		assertNull(inputDiv.getValueOrNull());
		indicator.assertIndicatesNothing();
	}

	// -------------------- backdrop -------------------- //

	@Test
	public void testBackdropBeforeTyping() {

		openPopup();

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterTyping() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterSelectionWithEnter() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterSelectionWithClickOnValue() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		// click the value
		clickAutoCompleteValue(VALUE1);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterInvalidInputAndEnter() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForServer();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterInvalidInputAndEscape() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForServer();

		// press enter
		send(inputField, Key.ESCAPE);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropIfAlreadySubmitted() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		// remove and regain focus
		clickBodyNode();
		waitForServer();
		click(inputField);
		waitForServer();

		assertTrue(isAutoCompleteBackdropDisplayed());

		click(inputField);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropIfSubmittedAndThenAltered() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		// remove and regain focus
		clickBodyNode();
		waitForServer();
		click(inputField);

		// type something
		send(inputField, "foo");
		waitForServer();

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterConfirmationOfUniqueValidValue() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForServer();

		// close the popup
		send(inputField, Key.ESCAPE);
		waitForServer();

		// remove and regain focus
		clickBodyNode();
		waitForServer();
		click(inputField);

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterInvalidValueAndFocusLoss() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForServer();

		clickBodyNode();
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	// -------------------- private -------------------- //

	/**
	 * Asserts that a change event was triggered.
	 */
	private void assertEvent() {

		waitForServer();
		inputDiv.assertOneEvent();
	}

	/**
	 * Asserts that a change event was triggered, and the value of the input.
	 */
	private void assertEventAndValue(AjaxAutoCompleteTestValue value) {

		assertEvent();
		assertSame(value, inputDiv.getValueOrNull());
	}

	/**
	 * Enters the name of the given value and waits for the pop-up.
	 */
	private void openPopup() {

		click(inputField);
		waitForServer();
	}
}
