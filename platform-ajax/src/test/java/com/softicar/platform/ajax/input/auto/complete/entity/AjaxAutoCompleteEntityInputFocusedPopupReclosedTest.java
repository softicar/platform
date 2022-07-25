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
	public void testCallbacksWithValidAndValidItemNamesAndEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();
		input//
			.pressBackspace(ENTITY1.toDisplayStringWithId().length())
			.sendString(ENTITY2.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCount(2)
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testCallbacksWithInvalidAndValidValidItemNamesAndEnterOnActiveEmptyInput() {

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
		input//
			.focusWithClick()
			.pressBackspace(INVALID_ITEM_NAME.length())
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
			.expectCallbackCount(2)
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testCallbacksWithValidAndInvalidValidItemNamesAndEnterOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished()
			.pressEnter()
			.waitForServer();
		input//
			.focusWithClick()
			.pressBackspace(ENTITY1.toDisplayStringWithId().length())
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
			.expectCallbackCount(2)
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testCallbacksWithInvalidAndInvalidValidItemNamesAndEnterOnActiveEmptyInput() {

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
		input//
			.focusWithClick()
			.pressBackspace(INVALID_ITEM_NAME.length())
			.sendString("other invalid item name")
			.waitForPopupAndLoadingFinished();
		overlay//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue("other invalid item name")
			.expectServerValueExceptionMessage()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackCount(2)
			.expectCallbackValueNone()
			.assertAll();
	}
}
