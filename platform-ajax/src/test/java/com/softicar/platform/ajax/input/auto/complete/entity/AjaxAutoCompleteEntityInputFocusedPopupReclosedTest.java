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
	public void testValidInputWithBackspaceTillEmptyAndTypedUniquePatternAndEnter() {

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
	public void testValidInputWithBackspaceTillEmptyAndTypedIllegalPatternAndEscape() {

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
			.waitForPopupAndServerFinished()
			.pressEscape()
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
	public void testIllegalInputWithBackspaceTillEmptyAndTypedUniquePatternAndEnter() {

		setup//
			.execute();
		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished()
			.pressEscape()
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
	public void testIllegalInputWithBackspaceTillEmptyAndTypedIllegalPatternAndEscape() {

		setup//
			.execute();
		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished()
			.pressEscape()
			.waitForServer();

		input//
			.focusByClick()
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
			.sendString("other illegal value name")
			.waitForPopupAndServerFinished()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectClientValue("other illegal value name")
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
