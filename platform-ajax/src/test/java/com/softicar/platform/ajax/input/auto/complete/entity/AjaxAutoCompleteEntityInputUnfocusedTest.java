package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
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
			.waitForLoadingFinished()
			.pressEnter();

		// server value is set after an arbitrary event handling was triggered
		eventTrigger//
			.trigger();

		asserter//
			.expectClientValue(ENTITY1)
			.expectServerValue(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testServerValueTransferAfterAmbiguousFilteringAndClosePopupWithEscOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(5)
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		// server value is set after an arbitrary event handling was triggered
		eventTrigger//
			.trigger();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndSelectionWithClickNotOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished();
		body//
			.click();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueNone()
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndShiftingAndSelectionWithClickOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished();
		popup//
			.clickEntityNumber(2);

		asserter//
			.expectClientValue(ENTITY3)
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus() // TODO this should be changed: click-selecting an item should result in the input still being focused
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndSelectionWithClickOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished();
		popup//
			.clickEntityNumber(1);

		asserter//
			.expectClientValue(ENTITY1)
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus() // TODO this should be changed: click-selecting an item should result in the input still being focused
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testLoadingIndicatorAfterFocusLossWithIdOnPassiveEmptyInput() {

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
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectNoFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDeferredIndicatorUpdateAfterFocusLossWithIdOnPassiveEmptyInput() {

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
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(ENTITY1.getIdAsString())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testLoadingIndicatorAfterFocusLossWithPartialIdOnPassiveEmptyInput() {

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
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectNoFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDeferredIndicatorUpdateAfterFocusLossWithPartialIdOnPassiveEmptyInput() {

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
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(item2.getIdAsString())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidationWithMatchOnRestrictivePassiveEmptyInput() {

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.toDisplayStringWithId())
			.waitForLoadingFinished();

		eventTrigger//
			.trigger()
			.waitForServer();

		asserter//
			.expectClientValue(ENTITY1.toDisplayStringWithId())
			.expectServerValue(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidationWithMismatchOnRestrictivePassiveEmptyInput() {

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.execute();

		input//
			.focusWithClick()
			.sendString("fo")
			.waitForLoadingFinished();

		eventTrigger//
			.trigger()
			.waitForServer();

		asserter//
			.expectClientValue("fo")
			.expectServerValueExceptionMessage()
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidationWithIncludedUpperCaseItemNameOnRestrictivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "FOOO");

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString(item1.toDisplayStringWithId())
			.waitForPopupAndLoadingFinished();

		eventTrigger//
			.trigger()
			.waitForServer();

		asserter//
			.expectClientValue(item1.toDisplayStringWithId())
			.expectServerValue(item1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
