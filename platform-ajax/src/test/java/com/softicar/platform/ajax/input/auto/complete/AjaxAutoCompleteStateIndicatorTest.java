package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.document.CurrentDomDocument;
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
		indicator.assertValueAmbiguous();

		// press escape
		send(inputField, Key.ESCAPE);
		indicator.assertValueAmbiguous();

		// set input value
		click(button);
		waitForServer();
		click(inputField);
		indicator.assertValueIllegal();
	}

	// -------------------- invalid -> valid -------------------- //

	@Test
	public void testTransitionFromInvalidToOkayIndicatorWithValueFromServer() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		indicator.assertValueIllegal();

		// set input value
		send(inputField, Key.ESCAPE);
		click(button);
		waitForServer();
		indicator.assertNotOkay();
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
		send(inputField, Key.ESCAPE);
		clickBodyNode();
		indicator.assertNotOkay(true);
	}
}
