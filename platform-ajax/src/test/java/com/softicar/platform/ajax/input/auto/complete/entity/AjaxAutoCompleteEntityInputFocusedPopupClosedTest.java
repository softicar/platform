package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.3 Popup Closed"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with a closed popup, and a focused input
 * element.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupClosedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithArrowDownAndEscape() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputField()
			.pressArrowDown()
			.waitForServer()
			.pressEscape()
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

	@Test
	public void testValidInputWithBackspaceTillAmbiguousAndEscape() {

		setup//
			.setSelectedValue(VALUE3)
			.execute();

		input//
			.clickInputField()
			.pressBackspace(5)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndEscape() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputField()
			.pressBackspace(VALUE1.toDisplayStringWithId().length())
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputTextNone()
			.expectSelectedValueNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedUniquePatternAndEnter() {

		setup//
			.setSelectedValue(VALUE2)
			.execute();

		input//
			.clickInputField()
			.pressBackspace(VALUE2.toDisplayStringWithId().length())
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
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedIllegalPatternAndClickOnBackdrop() {

		setup//
			.setSelectedValue(VALUE2)
			.execute();

		input//
			.clickInputField()
			.pressBackspace(VALUE2.toDisplayStringWithId().length())
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDownAndEscape() {

		setup//
			.execute();

		input//
			.clickInputField()
			.pressArrowDown()
			.waitForServer()
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
	public void testEmptyInputWithTypedUniquePatternAndEnter() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndEscape() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndTab() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndClickOnBackdrop() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(VALUE2.getName())
			.waitForServer();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE2)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndClickOnPopupRow() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(VALUE1.getName())
			.waitForServer();
		popup//
			.clickValueNumber(1)
			.waitForServer();

		asserter//
			.expectInputText(VALUE1)
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEnter() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE2)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEscape() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndClickOnPopupRow() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();
		popup//
			.clickValueNumber(2)
			.waitForServer();

		asserter//
			.expectValues(VALUE3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE3)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDownAndEnter() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressArrowDown()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackValue(VALUE3)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndEscape() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndClickOnBackdrop() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
