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
	public void testCallbacksWithValidAndValidValueNamesAndEnterOnEmptyInput() {

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
	public void testCallbacksWithInvalidAndValidValidValueNamesAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		input//
			.focusWithClick()
			.pressBackspace(INVALID_VALUE_NAME.length())
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
	public void testCallbacksWithValidAndInvalidValidValueNamesAndEnterOnEmptyInput() {

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
			.sendString(INVALID_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(INVALID_VALUE_NAME)
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
	public void testCallbacksWithInvalidAndInvalidValidValueNamesAndEnterOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		input//
			.focusWithClick()
			.pressBackspace(INVALID_VALUE_NAME.length())
			.sendString("other invalid value name")
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue("other invalid value name")
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
