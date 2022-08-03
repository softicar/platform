package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.3 Popup Closed"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * Note that, for passive input elements, the value is not transferred to the
 * server until the next arbitrary event is handled.
 * <p>
 * All assertions in here should expect a focused input element and a
 * non-displayed popup.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupClosedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testUniqueFilteringAndSelectionWithEnterOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndSelectionWithEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackValue(ENTITY1)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndSelectionWithEnterOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressEnter();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndSelectionWithEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackValue(ENTITY2)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndShiftingAndSelectionWithEnterOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressDownArrow()
			.pressEnter();

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndShiftingAndSelectionWithEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressDownArrow()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackValue(ENTITY3)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testClosePopupWithEscOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClosePopupWithEscOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndClosePopupWithEscOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndClosePopupWithEscOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndClosePopupWithEscOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndClosePopupWithEscOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidFilteringAndClosePopupWithEscOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidFilteringAndClosePopupWithEscOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClosePopupWithEscOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClosePopupWithEscOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndClosePopupWithEscOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndClosePopupWithEscOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndClosePopupWithEscOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(5)
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndClosePopupWithEscOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(5)
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testEmptyFilteringAndClosePopupWithEscOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(7)
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyFilteringAndClosePopupWithEscOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(7)
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValueNone()
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testMandatotyAndEmptyFilteringAndClosePopupWithEscOnPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(7)
			.waitForPopupAndLoadingFinished()
			.pressEsc();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatotyAndEmptyFilteringAndClosePopupWithEscOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(7)
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValueNone()
			.expectServerValueNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testCloseOverlayWithClickOnOverlayOfActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY2.getName())
			.waitForPopupAndLoadingFinished();
		overlay//
			.click();

		asserter//
			.expectClientValue(ENTITY2.getName())
			.expectServerValue(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testOverlayRemovedWithTabOnActiveEmptyInputWithOpenPopup() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testOverlayRemovedWithEscOnActiveEmptyInputWithOpenPopup() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(ENTITY1.getName()) // TODO we could complete this with the server side value
			.expectServerValue(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedWithAmbiguousItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(AMBIGUOUS_ITEM_NAME_CHUNK);

			asserter//
				.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
				.expectServerValueExceptionMessage()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedWithAmbiguousItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(AMBIGUOUS_ITEM_NAME_CHUNK);

			asserter//
				.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testOverlayNotDisplayedWhileLoadingFinishesAfterPopupClosedWithUniqueItemNameOnEmptyPassiveInput() {

		setup//
			.execute();

		input//
			.focusWithClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(ENTITY1.getName());
			body//
				.click();

			asserter//
				.expectClientValue(ENTITY1.getName())
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
	@Ignore("This test does not make sense anymore.")
	public void testOverlayDisplayedWhileLoadingFinishesAfterPopupClosedWithUniqueItemNameOnEmptyActiveInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(ENTITY1.getName());
			overlay//
				.click();

			asserter//
				.expectClientValue(ENTITY1.getName())
				.expectServerValueNone()
				.expectIndicatorCommitting()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingFinishesAfterClosingPopupWithUniqueItemNameOnEmptyPassiveInput() {

		setup//
			.execute();

		input//
			.focusWithClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(ENTITY1.getName());
			body//
				.click();
		}

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingFinishesAfterClosingPopupWithUniqueItemNameOnEmptyActiveInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(ENTITY1.getName());
			overlay//
				.click();
		}

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeUniqueItemNameAndEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeAmbiguousItemNameAndEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeInvalidItemNameAndClosePopupOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndLoadingFinished();
		overlay//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeUniqueItemNameAndEnterOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY2)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(ENTITY2.toDisplayStringWithId().length())
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeInvalidItemNameAndEnterOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY2)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace(ENTITY2.toDisplayStringWithId().length())
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndLoadingFinished();
		overlay//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}
}
