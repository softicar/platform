package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.document.CurrentDomDocument;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests the indicators of auto-complete inputs.
 *
 * @author Oliver Richers
 */
public class AjaxAutoCompleteStateIndicatorTest extends AbstractAjaxAutoCompleteStringTest {

	private TestButton button;

	public AjaxAutoCompleteStateIndicatorTest() {

		openTestInput(i -> {
			this.button = new TestButton(() -> i.getInputField().setValue(INVALID_INPUT));
			CurrentDomDocument.get().getBody().appendChild(button);

			i.getEngine().addItems(ITEM1, ITEM2, ITEM3);
		});
	}

	// -------------------- loading -------------------- //

	@Test
	@Ignore("This test does not make sense anymore.")
	public void testLoadingIndicator() {

		try (Locker locker = inputDiv.getEngine().lock()) {
			indicator.assertLoading(false);
			send(inputField, Key.DOWN);
			indicator.assertLoading(true);
		}

		waitForAutoCompletePopup();
		indicator.assertLoading(false);
	}

	// -------------------- ambiguous -> valid -------------------- //

	@Test
	public void testTransitionFromAmbiguousToValidIndicatorWithEmptyValue() {

		// set non-empty value
		click(button);
		waitForServer();

		// input some ambiguous text
		clear(inputField);
		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		indicator.assertValueAmbiguous(true);

		// press escape
		send(inputField, Key.ESCAPE);
		indicator.assertValueAmbiguous(true);

		// remove text from input and close popup
		clear(inputField);
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);
		indicator.assertValueValid(true);
	}

	@Test
	public void testTransitionFromAmbiguousToValidIndicatorWithValueFromServer() {

		// input some ambiguous text
		click(inputField);
		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		indicator.assertValueAmbiguous(true);

		// press escape
		send(inputField, Key.ESCAPE);
		indicator.assertValueAmbiguous(true);

		// set input value
		click(button);
		waitForServer();
		click(inputField);
		indicator.assertValueValid(true);
	}

	// -------------------- invalid -> valid -------------------- //

	@Test
	public void testTransitionFromInvalidToOkayIndicatorWithValueFromServer() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		indicator.assertValueIllegal(true);

		// set input value
		click(button);
		waitForServer();
		indicator.assertValueValid(true);
	}

	// -------------------- invalid -------------------- //

	@Test
	public void testInvalidAndNotOkayIndicator() {

		// input invalid text
		click(inputField);
		send(inputField, "x");
		waitForAutoCompletePopup();
		indicator.assertValueIllegal(true);

		// leave input
		clickBodyNode();
		indicator.assertNotOkay(true);
	}
}
