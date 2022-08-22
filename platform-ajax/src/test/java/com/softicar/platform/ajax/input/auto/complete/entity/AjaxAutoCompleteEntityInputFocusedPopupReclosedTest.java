package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.3 Popup Closed"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupReclosedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testCallbacksWithValidAndValidItemNamesAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();
		input//
			.pressBackspace(ENTITY1.toDisplayStringWithId().length())
			.sendString(ENTITY2.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCount(2)
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testCallbacksWithInvalidAndValidValidItemNamesAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		input//
			.focusWithClick()
			.pressBackspace(INVALID_ITEM_NAME.length())
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
			.expectCallbackCount(1)
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbacksWithValidAndInvalidValidItemNamesAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();
		input//
			.focusWithClick()
			.pressBackspace(ENTITY1.toDisplayStringWithId().length())
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCount(2)
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testCallbacksWithInvalidAndInvalidValidItemNamesAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		input//
			.focusWithClick()
			.pressBackspace(INVALID_ITEM_NAME.length())
			.sendString("other invalid item name")
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue("other invalid item name")
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
