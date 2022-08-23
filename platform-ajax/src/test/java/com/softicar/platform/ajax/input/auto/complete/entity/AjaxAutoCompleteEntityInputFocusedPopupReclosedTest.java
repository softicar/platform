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
			.execute();

		input//
			.focusByClick()
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
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		input//
			.focusByClick()
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
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
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();
		input//
			.focusByClick()
			.pressBackspace(ENTITY1.toDisplayStringWithId().length())
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
			.expectCallbackCount(2)
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testCallbacksWithInvalidAndInvalidValidValueNamesAndEnterOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		input//
			.focusByClick()
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
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
