package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import org.junit.Ignore;
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

		assertEventAndValue(VALUE1);
	}

	@Test
	public void testChangeEventWithTab() {

		openPopup();
		send(inputField, Key.DOWN, Key.TAB);

		assertEventAndValue(VALUE1);
	}

	@Test
	public void testChangeEventWithClick() {

		openPopup();
		clickAutoCompleteValue(VALUE3);

		assertEventAndValue(VALUE3);
	}

	@Test
	public void testNoChangeEventWithEscapeAfterIllegalInput() {

		openPopup();
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);

		waitForServer();
		inputDiv.assertNoEvent();
		indicator.assertIndicatesIllegal();
	}

	@Test
	public void testChangeEventWithInvalidValueAndBlur() {

		openPopup();
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		clickBodyNode();

//		assertEventAndValue(INVALID_INPUT); // FIXME decide behavior
	}

	@Test
	public void testChangeEventWithValidValueAndBlur() {

		openPopup();
		send(inputField, VALUE3.getName());
		waitForAutoCompletePopup();
		clickBodyNode();

		assertEventAndValue(VALUE3);
	}

	// -------------------- submit without popup -------------------- //

	@Test
	public void testSubmitWithoutPopup() {

		// input and submit some value
		openPopup();
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForServer();
		inputDiv.clearEvents();

		// now clear input and close popup with escape, and assert that empty value was submitted
		while (!getAttributeValue(inputField, "value").isEmpty()) {
			send(inputField, Key.BACK_SPACE);
		}
		waitForAutoCompletePopup();
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

	// -------------------- un-submitted -------------------- //

	@Test
	public void testUnsubmittedIndicatorWithEscape() {

		// input some invalid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		// set input value
		send(inputField, Key.ESCAPE);
//		assertUnsubmittedIndicator(true); // FIXME decide behavior
	}

	// -------------------- backdrop -------------------- //

	@Test
	@Ignore("Unnecessary test.")
	public void testNoBackdropBeforeTyping() {

		openPopup();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterTyping() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testNoBackdropAfterSelectionWithEnter() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testNoBackdropAfterSelectionWithClickOnValue() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		// click the value
		clickAutoCompleteValue(VALUE1);

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropAfterInvalidInputAndEnter() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testNoBackdropAfterInvalidInputAndEscape() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();

		// press enter
		send(inputField, Key.ESCAPE);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testNoBackdropIfAlreadySubmitted() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		// remove and regain focus
		unfocusAndRefocusInput();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testBackdropIfSubmittedAndThenAltered() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		// remove and regain focus
		unfocusAndRefocusInput();

		// type something
		send(inputField, "foo");

		assertTrue(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testNoBackdropAfterConfirmationOfUniqueValidValue() {

		// input some valid text
		click(inputField);
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();

		// close the popup
		send(inputField, Key.ESCAPE);

		// remove and regain focus
		unfocusAndRefocusInput();

		// press enter
		send(inputField, Key.ENTER);
		waitForServer();

		assertFalse(isAutoCompleteBackdropDisplayed());
	}

	@Test
	public void testNoBackdropAfterInvalidValueAndFocusLoss() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();

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
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
	}

	/**
	 * Un-focuses the input and re-focuses it.
	 */
	private void unfocusAndRefocusInput() {

		clickBodyNode();
		click(inputField);
	}
}
