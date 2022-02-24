package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.thread.Locker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public class AjaxAutoCompleteTest extends AbstractAjaxAutoCompleteStringTest {

	private final List<String> requests;

	public AjaxAutoCompleteTest() {

		this.requests = new ArrayList<>();

		openTestInput(i -> i.getEngine().addItems(ITEM1, ITEM2, ITEM3, ITEM4));
		inputDiv.getEngine().setRequestListener(requests::add);
	}

	// -------------------- showing -------------------- //

	@Test
	public void testDisplayPopupOnKeyDown() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupItems(ITEM1, ITEM2, ITEM3, ITEM4);
		indicator.assertValueValid(true);
	}

	@Test
	public void testDisplayPopupOnKeyUp() {

		send(inputField, Key.UP);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupItems(ITEM1, ITEM2, ITEM3, ITEM4);
		indicator.assertValueValid(true);
	}

	@Test
	public void testDisplayPopupOnInput() {

		click(inputField);
		simulateInput(inputField);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupItems(ITEM1, ITEM2, ITEM3, ITEM4);
	}

	// -------------------- items -------------------- //

	@Test
	public void testDisplayPopupItemsWithoutFocusLossAndPerfectMatchWithReopeningAfterSelection() {

		click(inputField);
		send(inputField, ITEM3.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertPopupItems(ITEM3);
	}

	@Test
	public void testDisplayPopupItemsWithoutFocusLossWithReopeningAfterEmptySelection() {

		click(inputField);
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertPopupItems(ITEM1, ITEM2, ITEM3, ITEM4);
	}

	// -------------------- reopen -------------------- //

	@Test
	public void testReopeningPopupAfterSelection() {

		// open popup and close it
		send(inputField, ITEM1.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(ITEM1.getName(), getAttributeValue(inputField, "value"));

		// try to open popup again
		clear(inputField);
		send(inputField, ITEM2.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(ITEM2.getName(), getAttributeValue(inputField, "value"));
	}

	@Test
	public void testReopeningPopupAfterFocusLost() {

		// open popup and close it
		send(inputField, ITEM1.getName());
		waitForAutoCompletePopup();
		clickBodyNode();
		waitForAutoCompletePopupToHide();

		// try to open popup again
		clear(inputField);
		send(inputField, ITEM2.getName());
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForAutoCompletePopupToHide();

		assertEquals(ITEM2.getName(), getAttributeValue(inputField, "value"));
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

		assertEquals(ITEM1.getName(), getAttributeValue(inputField, "value"));
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
		indicator.assertValueValid(true);
		assertEquals("", getAttributeValue(inputField, "value"));
	}

	@Test
	public void testFocusLossAmbiguousValue() {

		click(inputField);
		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		indicator.assertValueAmbiguous(true);

		clickBodyNode();
		waitForServer();

		assertFalse(isAutoCompletePopupDisplayed());
		assertEquals(AMBIGUOUS_INPUT, getAttributeValue(inputField, "value"));
	}

	// -------------------- filtering -------------------- //

	@Test
	public void testDisplayItemsWithoutFiltering() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();

		assertRequests("");
		assertPopupItems(ITEM1, ITEM2, ITEM3, ITEM4);
	}

	@Test
	public void testDisplayItemsWithFiltering() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();

		assertRequests(AMBIGUOUS_INPUT);
		assertPopupItems(ITEM1, ITEM3, ITEM4);
	}

	// -------------------- tab -------------------- //

	@Test
	public void testTabKeySelection() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.TAB);
		waitForServer();

		assertInputValue(ITEM1);
	}

	@Test
	public void testTabKeyWithInvalidValue() {

		// enter invalid input and press TAB
		send(inputField, INVALID_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.TAB);
		waitForServer();

		// assert popup is not closed but input keeps focus
		assertTrue(isAutoCompletePopupDisplayed());
		assertFocused(inputField);
	}

	// -------------------- selection -------------------- //

	@Test
	public void testSelectionAfterFilteringWithDefaultItem() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(ITEM1);
	}

	@Test
	public void testSelectionAfterFilteringWithArrowNavigation() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(ITEM3);
	}

	@Test
	public void testSelectionAfterFilteringWithPingpongArrowNavigation() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.UP, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(ITEM1);
	}

	@Test
	public void testSelectionAfterFilteringWithWrappingArrowNavigation() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.DOWN, Key.ENTER);
		waitForServer();

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(ITEM4);
	}

	@Test
	public void testItemSelectionWithClick() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();

		clickAutoCompleteItem(ITEM3);
		waitWhile(() -> isAutoCompletePopupDisplayed());

		assertRequests(AMBIGUOUS_INPUT);
		assertInputValue(ITEM3);
		assertFalse(isAutoCompletePopupDisplayed());
	}

	@Test
	public void testSelectionWhileLoading() {

		send(inputField, AMBIGUOUS_INPUT);
		waitForAutoCompletePopup();

		try (Locker locker = inputDiv.getEngine().lock()) {
			send(inputField, INVALID_INPUT);
			send(inputField, Key.DOWN);
			send(inputField, Key.ENTER);
		}
		waitForServer();

		indicator.assertValueValid(true);
		assertFalse(isAutoCompletePopupDisplayed());
		assertInputValue(ITEM1);
	}

	@Test
	public void testMoreItemsInfoDisplayed() {

		for (int i = 0; i < 16; i++) {
			AjaxAutoCompleteTestItem item = new AjaxAutoCompleteTestItem("" + i);
			inputDiv.getEngine().addItem(item);
		}
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertTrue(isAutoCompleteMoreItemsInfoElementDisplayed());
	}

	@Test
	public void testMoreItemsInfoNotDisplayed() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertFalse(isAutoCompleteMoreItemsInfoElementDisplayed());
	}

	@Test
	public void testNoItemPlaceholderDisplayed() {

		inputDiv.getEngine().clearItems();
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertTrue(isAutoCompleteItemPlaceholderElementDisplayed());
	}

	@Test
	public void testNoItemPlaceholderNotDisplayed() {

		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		assertFalse(isAutoCompleteItemPlaceholderElementDisplayed());
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
