package com.softicar.platform.ajax.input.auto.complete.entity;

import org.junit.Test;

/**
 * Initial state of all tests in this class: The pop-up was closed, and
 * re-opened.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityPopupReOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedUniquePatternAndEnter() {

		setup//
			.execute();
		input//
			.clickInputField()
			.sendString(VALUE1.getName())
			.pressEnter()
			.clickInputField();

		input//
			.pressBackspace(VALUE1.toDisplayStringWithId().length())
			.sendString(VALUE2.getName())
			.pressEnter();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCount(2)
			.expectCallbackValue(VALUE2)
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedIllegalPatternAndEscape() {

		setup//
			.execute();
		input//
			.clickInputField()
			.sendString(VALUE1.getName())
			.pressEnter()
			.clickInputField();

		input//
			.pressBackspace(VALUE1.toDisplayStringWithId().length())
			.sendString(ILLEGAL_VALUE_NAME)
			.pressEscape();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCount(2)
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithBackspaceTillEmptyAndTypedUniquePatternAndEnter() {

		setup//
			.execute();
		input//
			.clickInputField()
			.sendString(ILLEGAL_VALUE_NAME)
			.pressEscape()
			.clickInputField();

		input//
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
			.sendString(VALUE1.getName())
			.pressEnter();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCount(1)
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testIllegalInputWithBackspaceTillEmptyAndTypedIllegalPatternAndEscape() {

		setup//
			.execute();
		input//
			.clickInputField()
			.sendString(ILLEGAL_VALUE_NAME)
			.pressEscape()
			.clickInputField();

		input//
			.pressBackspace(ILLEGAL_VALUE_NAME.length())
			.sendString("other illegal value name")
			.pressEscape();

		asserter//
			.expectInputText("other illegal value name")
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}
}
