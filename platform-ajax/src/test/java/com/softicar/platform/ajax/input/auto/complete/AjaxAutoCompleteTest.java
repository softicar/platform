package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.thread.Locker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Ignore;
import org.junit.Test;

public class AjaxAutoCompleteTest extends AbstractAjaxAutoCompleteStringTest {

	private final List<String> requests;

	public AjaxAutoCompleteTest() {

		this.requests = new ArrayList<>();

		openTestInput(i -> i.getEngine().addValues(VALUE1, VALUE2, VALUE3, VALUE4));
		inputDiv.getEngine().setRequestListener(requests::add);
	}

	// -------------------- showing -------------------- //

	@Test
	public void testDisplayPopupOnKeyDown() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupValues(VALUE1, VALUE2, VALUE3, VALUE4);
		indicator.assertIndicatesNothing();
	}

	@Test
	public void testDisplayPopupOnKeyUp() {

		send(inputField, Key.UP);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupValues(VALUE1, VALUE2, VALUE3, VALUE4);
		indicator.assertIndicatesNothing();
	}

	@Test
	public void testDisplayPopupOnInput() {

		click(inputField);
		simulateInput(inputField);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupValues(VALUE1, VALUE2, VALUE3, VALUE4);
	}

	// -------------------- values -------------------- //

	@Test
	public void testDisplayPopupValuesWithoutFocusLossAndPerfectMatchWithReopeningAfterSelection() {

		click(inputField);
		send(inputField, VALUE3.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertPopupValues(VALUE3, VALUE4);
	}

	@Test
	public void testDisplayPopupValuesWithoutFocusLossWithReopeningAfterEmptySelection() {

		click(inputField);
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);
		waitForAutoCompletePopupToHide();

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertPopupValues(VALUE1, VALUE2, VALUE3, VALUE4);
	}

	// -------------------- reopen -------------------- //

	@Test
	public void testReopeningPopupAfterSelection() {

		// open popup and close it
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(VALUE1.getName(), getAttributeValue(inputField, "value"));

		// try to open popup again
		clear(inputField);
		send(inputField, VALUE2.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(VALUE2.getName(), getAttributeValue(inputField, "value"));
	}

	@Test
	public void testReopeningPopupAfterFocusLost() {

		// open popup and close it
		send(inputField, VALUE1.getName());
		waitForAutoCompletePopup();
		clickBodyNode();
		waitForAutoCompletePopupToHide();

		// try to open popup again
		clear(inputField);
		send(inputField, VALUE2.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(VALUE2.getName(), getAttributeValue(inputField, "value"));
	}

	// this is a test for #37204
	@Test
	public void testReopeningPopupAfterEscape() {

		// open popup and close it
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.ESCAPE);
		waitForAutoCompletePopupToHide();

		// try to open popup again
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN);
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(VALUE1.getName(), getAttributeValue(inputField, "value"));
	}

	// -------------------- blur -------------------- //

	@Test
	public void testFocusLossWithEmptyValue() {

		click(inputField);
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		clickBodyNode();
		waitForAutoCompletePopupToHide();

		assertFalse(isAutoCompletePopupDisplayed());
		indicator.assertIndicatesNothing();
		assertEquals("", getAttributeValue(inputField, "value"));
	}

	@Test
	public void testFocusLossAmbiguousValue() {

		click(inputField);
		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		indicator.assertIndicatesAmbiguous();

		clickBodyNode();
		waitForServer();

		assertFalse(isAutoCompletePopupDisplayed());
		assertEquals(AMBIGUOUS_INPUT, getAttributeValue(inputField, "value"));
	}

	// -------------------- filtering -------------------- //

	@Test
	public void testDisplayValuesWithoutFiltering() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupValues(VALUE1, VALUE2, VALUE3, VALUE4);
	}

	@Test
	public void testDisplayValuesWithFiltering() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();

		assertRequests(AMBIGUOUS_INPUT);
		assertPopupValues(VALUE1, VALUE3, VALUE4);
	}

	// -------------------- tab -------------------- //

	@Test
	public void testTabKeySelection() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.TAB);
		waitForServer();

		assertInputValue(VALUE1);
	}

	@Test
	public void testTabKeyWithInvalidValue() {

		// enter invalid input and press TAB
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.TAB);
		waitForServer();

		// assert popup is closed
		assertFalse(isAutoCompletePopupDisplayed());
		assertFocused(inputDiv.getParent());
	}

	// -------------------- selection -------------------- //

	@Test
	public void testSelectionAfterFilteringWithDefaultValue() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(VALUE1);
	}

	@Test
	public void testSelectionAfterFilteringWithArrowNavigation() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(VALUE3);
	}

	@Test
	public void testSelectionAfterFilteringWithPingpongArrowNavigation() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.UP, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(VALUE1);
	}

	@Test
	public void testSelectionAfterFilteringWithWrappingArrowNavigation() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.DOWN, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(VALUE4);
	}

	@Test
	public void testValueSelectionWithClick() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();

		clickAutoCompleteValue(VALUE3);
		waitWhile(() -> isAutoCompletePopupDisplayed());

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(VALUE3);
		assertFalse(isAutoCompletePopupDisplayed());
	}

	@Test
	@Ignore("This test does not make sense anymore.")
	public void testSelectionWhileLoading() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();

		try (Locker locker = inputDiv.getEngine().lock()) {
			send(inputField, INVALID_INPUT);
			send(inputField, Key.DOWN);
			send(inputField, Key.ENTER);
		}
		waitForServer();

		indicator.assertIndicatesNothing();
		assertFalse(isAutoCompletePopupDisplayed());
		assertInputValue(VALUE1);
	}

	@Test
	public void testMoreValuesInfoDisplayed() {

		for (int i = 0; i < 16; i++) {
			var value = new AjaxAutoCompleteTestValue("" + i);
			inputDiv.getEngine().addValue(value);
		}
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertTrue(isAutoCompleteMoreValuesInfoElementDisplayed());
	}

	@Test
	public void testMoreValuesInfoNotDisplayed() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertFalse(isAutoCompleteMoreValuesInfoElementDisplayed());
	}

	@Test
	public void testNoValuePlaceholderDisplayed() {

		inputDiv.getEngine().clearValues();
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertTrue(isAutoCompleteValuePlaceholderElementDisplayed());
	}

	@Test
	public void testNoValuePlaceholderNotDisplayed() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertFalse(isAutoCompleteValuePlaceholderElementDisplayed());
	}

	// -------------------- private -------------------- //

	private void assertRequests(String...expectedRequests) {

		assertRequests(Arrays.asList(expectedRequests));
	}

	private void assertRequests(Collection<String> expectedRequests) {

		Set<String> expectedRequestsSet = new TreeSet<>(expectedRequests);
		Set<String> actualRequestsSet = new TreeSet<>(requests);

		String message = String.format("expected:<%s> but was:<%s>", expectedRequestsSet, actualRequestsSet);
		for (String expectedRequest: expectedRequestsSet) {
			assertTrue(message, actualRequestsSet.contains(expectedRequest));
		}
	}
}
