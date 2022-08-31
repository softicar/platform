package com.softicar.platform.ajax.input.auto.complete.entity;

import org.junit.Test;

/**
 * Initial state of all tests in this class: The input element was appended, and
 * not yet focused or otherwise interacted with.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityAppendedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInput() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithFocusByClick() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputField();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithFocusByTab() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.focusByTab();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputAfterChange() {

		setup//
			.setSelectedValue(VALUE1)
			.setSelectedValue(VALUE2)
			.execute();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInput() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		asserter//
			.expectInputText(ILLEGAL_VALUE.toDisplayStringWithId())
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithFocusByClick() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.clickInputField();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithFocusByTab() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.focusByTab();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInput() {

		setup//
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithFocusByClick() {

		setup//
			.execute();

		input//
			.clickInputField();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueNone()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithFocusByTab() {

		setup//
			.execute();

		input//
			.focusByTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueNone()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputAfterClear() {

		setup//
			.setSelectedValue(VALUE1)
			.setSelectedValueNone()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackNone()
			.assertAll();
	}
}
