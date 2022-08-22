package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"3
 * Unfocused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputUnfocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testServerValueTransferAfterSelectionWithRandomEventOnArbitraryElement() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForServer()
			.pressEnter();

		// server value is set after an arbitrary event handling was triggered
		eventTrigger//
			.trigger();

		asserter//
			.expectClientValue(ENTITY1)
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testServerValueTransferAfterAmbiguousFilteringAndClosePopupWithEscOnFilledInput() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(5)
			.waitForPopupAndServerFinished()
			.pressEsc();

		// server value is set after an arbitrary event handling was triggered
		eventTrigger//
			.trigger();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndSelectionWithClickNotOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndServerFinished();
		overlay//
			.click()
			.waitForServer();
		body//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndShiftingAndSelectionWithClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndServerFinished();
		popup//
			.clickEntityNumber(2);

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndSelectionWithClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished();
		popup//
			.clickEntityNumber(1);

		asserter//
			.expectClientValue(ENTITY1)
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testLoadingIndicatorAfterFocusLossWithIdOnEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(ENTITY1.getIdAsString());
			body//
				.click();

			asserter//
				.expectClientValue(ENTITY1.getIdAsString())
				.expectServerValue(ENTITY1)
				.expectIndicatorNone()
				.expectPopupNotDisplayed()
				.expectNoFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDeferredIndicatorUpdateAfterFocusLossWithIdOnEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(ENTITY1.getIdAsString());
			body//
				.click();
		}

		input//
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testLoadingIndicatorAfterFocusLossWithPartialIdOnEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");
		final AjaxTestEntity item3 = new AjaxTestEntity(32, "lol"); // the ID of this item contains the ID of another item

		setup//
			.setEntities(item1, item2, item3) // replace default items
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(item2.getIdAsString());
			body//
				.click();

			asserter//
				.expectClientValue(item2.getIdAsString())
				.expectServerValue(item2)
				.expectIndicatorNone()
				.expectPopupNotDisplayed()
				.expectNoFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDeferredIndicatorUpdateAfterFocusLossWithPartialIdOnEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");
		final AjaxTestEntity item3 = new AjaxTestEntity(32, "lol"); // the ID of this item literally contains the ID of another item

		setup//
			.setEntities(item1, item2, item3) // replace default items
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(item2.getIdAsString());
			body//
				.click();
		}

		input//
			.waitForServer();

		asserter//
			.expectValues(item2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
