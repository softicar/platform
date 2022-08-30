package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import org.junit.Test;

/**
 * Initial state of all tests in this class: The input element was focused, and
 * not yet otherwise interacted with.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityFocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithEnter() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithTab() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnSuccessor()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithArrowDown() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer();

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
	public void testValidInputWithArrowDownAndClickOnInput() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer()
			.clickInputField()
			.waitForServer();

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
	public void testValidInputWithArrowUp() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueLast()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspace() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressBackspace()
			.waitForServer();

		asserter//
			.expectInputText("foo [1")
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillAmbiguousAndEscape() {

		setup//
			.setSelectedValue(VALUE3)
			.execute();
		input//
			.clickInputField();

		input//
			.pressBackspace(5)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillAmbiguousAndEscapeAndClickOnBody() {

		setup//
			.setSelectedValue(VALUE3)
			.execute();
		input//
			.clickInputField();

		input//
			.pressBackspace(5)
			.waitForServer()
			.pressEscape()
			.waitForServer();
		body//
			.click()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
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
			.clickInputField();

		input//
			.pressBackspace(VALUE1.toDisplayStringWithId().length())
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputTextNone()
			.expectSelectedValueNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
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
			.clickInputField();

		input//
			.pressBackspace(VALUE2.toDisplayStringWithId().length())
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
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
			.clickInputField();

		input//
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
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithArrowDownAndEscape() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithEnter() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();
		input//
			.clickInputField();

		input//
			.pressEnter()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithTab() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();
		input//
			.clickInputField();

		input//
			.pressTab()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocusOnSuccessor()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithArrowDown() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer();

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
	public void testIllegalInputWithArrowUp() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowUp()
			.waitForServer();

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
	public void testEmptyInputWithEnter() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithEscape() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressEscape()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTab() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressTab()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnSuccessor()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDown() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDownAndClickOnInput() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer()
			.clickInputField()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowUp() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueLast()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDownAndEscape() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.pressArrowDown()
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePattern() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE1.getName())
			.waitForServer();

		asserter//
			.expectInputText(VALUE1.getName())
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndEnter() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndEnterAndClickOnBody() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEnter()
			.waitForServer();
		body//
			.click()
			.waitForServer();

		asserter//
			.expectInputText(VALUE1)
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndEscape() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndTab() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE1.getName())
			.waitForServer()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnSuccessor()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndClickOnBackdrop() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE2.getName())
			.waitForServer();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE2)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndClickOnPopupRow() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
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
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePartialPattern() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString("foo [1")
			.waitForServer();

		asserter//
			.expectInputText("foo [1")
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePerfectMatchPattern() {

		setup//
			.add((input, engine) -> engine.setDisplayFunction(value -> value.toDisplayWithoutId()))
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(VALUE3.getName())
			.waitForServer();

		asserter//
			.expectInputText(VALUE3.getName())
			.expectSelectedValue(VALUE3)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE3, VALUE4)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPattern() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE2, VALUE3, VALUE4)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEnter() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE2)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEscape() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEscapeAndClickOnBody() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressEscape()
			.waitForServer();
		body//
			.click()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocusOnBody()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndClickOnPopupRow() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();
		popup//
			.clickValueNumber(2)
			.waitForServer();

		asserter//
			.expectValues(VALUE3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE3)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDown() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE2, VALUE3, VALUE4)
			.expectPopupSelectedValue(2)
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDownAndWrapping() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressArrowDown()
			.pressArrowDown()
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE2, VALUE3, VALUE4)
			.expectPopupSelectedValue(1)
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDownAndEnter() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressArrowDown()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(VALUE3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackValue(VALUE3)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowUpAndWrapping() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
			.pressArrowUp()
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE2, VALUE3, VALUE4)
			.expectPopupSelectedValue(2)
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPattern() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer();

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
	public void testEmptyInputWithTypedIllegalPatternAndEscape() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
			.pressEscape()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndClickOnBackdrop() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
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
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndArrowDown() {

		setup//
			.execute();
		input//
			.clickInputField();

		input//
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
			.pressArrowDown()
			.waitForServer();

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
	public void testEmptyInputWithTypedLowerPatternAndLowerValues() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setAvailableValues(value1, value2)
			.execute();
		input//
			.clickInputField();

		input//
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectValues(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedLowerPatternAndUpperValues() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setAvailableValues(value1, value2)
			.execute();
		input//
			.clickInputField();

		input//
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectInputText("foo [1]")
			.expectSelectedValue(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUpperPatternAndLowerValues() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setAvailableValues(value1, value2)
			.execute();
		input//
			.clickInputField();

		input//
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectInputText("FOO [1]")
			.expectSelectedValue(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUpperPatternAndUpperValues() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setAvailableValues(value1, value2)
			.execute();
		input//
			.clickInputField();

		input//
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectValues(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocusOnInput()
			.expectCallbackNone()
			.assertAll();
	}
}
