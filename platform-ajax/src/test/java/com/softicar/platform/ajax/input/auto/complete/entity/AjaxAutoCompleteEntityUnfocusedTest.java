package com.softicar.platform.ajax.input.auto.complete.entity;

import org.junit.Test;

/**
 * Initial state of all tests in this class: The input element was focused
 * before, and lost focus.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityUnfocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testIllegalInputWithFocusLossByTabAndRefocusByClick() {

		setup//
			.execute();
		input//
			.clickInputField()
			.sendString(ILLEGAL_VALUE_NAME)
			.pressEscape()
			.pressTab();

		input//
			.clickInputField();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithFocusLossByClickAndRefocusByClick() {

		setup//
			.execute();
		input//
			.clickInputField()
			.sendString(ILLEGAL_VALUE_NAME)
			.pressEscape();
		body//
			.click();

		input//
			.clickInputField();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}
}
