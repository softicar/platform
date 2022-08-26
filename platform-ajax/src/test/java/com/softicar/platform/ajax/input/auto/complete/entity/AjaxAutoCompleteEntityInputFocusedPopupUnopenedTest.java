package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.1 Popup Un-Opened"</b> (see
 * {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with the popup not yet being opened, and a
 * focused input element.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupUnopenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithEnter() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputField()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithTab() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithEnter() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(null)
			.assertAll();
	}

	@Test
	public void testIllegalInputWithTab() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.clickInputField()
			.pressTab()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithEnter() {

		setup//
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressEnter()
			.waitForNoPopup();

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
	public void testEmptyInputWithEscape() {

		setup//
			.execute();

		input//
			.clickInputField()
			.pressEscape()
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
	public void testEmptyInputWithTab() {

		setup//
			.execute();

		input//
			.clickInputField()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDownAndClickOnInput() {

		setup//
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressArrowDown()
			.waitForServer()
			.clickInputField()
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
	public void testValidInputWithArrowDownAndClickOnInput() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressArrowDown()
			.waitForServer()
			.clickInputField()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
