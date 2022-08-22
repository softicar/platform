package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.3 Popup Closed"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All assertions in here should expect a focused input element and a
 * non-displayed popup.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupClosedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testUniqueFilteringAndSelectionWithEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackValue(ENTITY1)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndSelectionWithEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackValue(ENTITY2)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndShiftingAndSelectionWithEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressArrowDown()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackValue(ENTITY3)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testClosePopupWithEscOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndClosePopupWithEscOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndClosePopupWithEscOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidFilteringAndClosePopupWithEscOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClosePopupWithEscOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndClosePopupWithEscOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndClosePopupWithEscOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(5)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testEmptyFilteringAndClosePopupWithEscOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(7)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValueNone()
			.expectServerValueNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testCloseBackdropWithClickOnBackdropOfEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY2.getName())
			.waitForPopupAndServerFinished();
		backdrop//
			.click();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testBackdropRemovedWithTabOnEmptyInputWithOpenPopup() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testBackdropRemovedWithEscOnEmptyInputWithOpenPopup() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingFinishesAfterClosingPopupWithUniqueValueNameOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString(ENTITY1.getName());
			backdrop//
				.click();
		}

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValueNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeUniqueValueNameAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeAmbiguousValueNameAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeInvalidValueNameAndClosePopupOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeUniqueValueNameAndEnterOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY2)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(ENTITY2.toDisplayStringWithId().length())
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbackWithTypeInvalidValueNameAndEnterOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY2)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(ENTITY2.toDisplayStringWithId().length())
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}
}
