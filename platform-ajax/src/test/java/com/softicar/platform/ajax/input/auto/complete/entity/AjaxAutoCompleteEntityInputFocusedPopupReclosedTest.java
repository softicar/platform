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
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();

		input//
			.pressBackspace(VALUE1.toDisplayStringWithId().length())
			.sendString(VALUE2.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCount(2)
			.expectCallbackValue(VALUE2)
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedIllegalPatternAndEscape() {

		setup//
			.execute();
		input//
			.focusByClick()
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();

		input//
			.focusByClick()
			.pressBackspace(VALUE1.toDisplayStringWithId().length())
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
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
			.waitForServer()
			.pressEscape()
			.waitForServer();

		input//
			.focusByClick()
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCount(1)
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testIllegalInputWithBackspaceTillEmptyAndTypedIllegalPatternAndEscape() {

		setup//
			.execute();
		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		input//
			.focusByClick()
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
			.sendString("other illegal value name")
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText("other illegal value name")
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
