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

			i.getEngine().addValues(VALUE1, VALUE2, VALUE3);
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
		indicator.assertIndicatesAmbiguous();

		// press escape
		send(inputField, Key.ESCAPE);
		waitForServer();
		indicator.assertIndicatesAmbiguous();

		// remove text from input and close popup
		clear(inputField);
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);
		waitForServer();

		indicator.assertIndicatesNothing();
	}

	@Test
	public void testTransitionFromAmbiguousToValidIndicatorWithValueFromServer() {

		// input some ambiguous text
		click(inputField);
		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		indicator.assertIndicatesAmbiguous();

		// press escape
		send(inputField, Key.ESCAPE);
		waitForServer();
		indicator.assertIndicatesAmbiguous();

		// set input value
		click(button);
		waitForServer();
		click(inputField);
		waitForServer();

		indicator.assertIndicatesIllegal();
	}

	// -------------------- invalid -> valid -------------------- //

	@Test
	public void testTransitionFromInvalidToOkayIndicatorWithValueFromServer() {

		// input some invalid text
		click(inputField);
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		indicator.assertIndicatesIllegal();

		// set input value
		send(inputField, Key.ESCAPE);
		click(button);
		waitForServer();

		indicator.assertIndicatesIllegal();
	}

	// -------------------- invalid -------------------- //

	@Test
	public void testInvalidAndNotOkayIndicator() {

		// input invalid text
		click(inputField);
		send(inputField, "x");
		waitForAutoCompletePopup();
		indicator.assertIndicatesIllegal();

		// leave input
		send(inputField, Key.ESCAPE);
		clickBodyNode();

		indicator.assertIndicatesIllegal();
	}
}
