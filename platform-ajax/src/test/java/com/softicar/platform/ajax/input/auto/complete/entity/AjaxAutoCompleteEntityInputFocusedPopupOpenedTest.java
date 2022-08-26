package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.2 Popup Opened"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with an open popup, and a focused input element.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithArrowDown() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithArrowUp() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueLast()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithClick() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputField()
			.waitForServer();

		asserter//
			.expectValues(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspace() {

		setup//
			.setSelectedValue(VALUE1)
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressBackspace()
			.waitForServer();

		asserter//
			.expectInputText("foo [1")
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithArrowDown() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.clickInputField()
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithArrowUp() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.clickInputField()
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithClick() {

		setup//
			.setSelectedValue(ILLEGAL_VALUE)
			.execute();

		input//
			.clickInputField()
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDown() {

		setup//
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowUp() {

		setup//
			.execute();

		input//
			.clickInputFieldAndClosePopup()
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithClick() {

		setup//
			.execute();

		input//
			.clickInputField()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUES)
			.expectPopupSelectedValueNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePattern() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(VALUE1.getName())
			.waitForServer();

		asserter//
			.expectInputText(VALUE1.getName())
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePartialPattern() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString("foo [1")
			.waitForServer();

		asserter//
			.expectInputText("foo [1")
			.expectSelectedValue(VALUE1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePerfectMatchPattern() {

		setup//
			.add((input, engine) -> engine.setDisplayFunction(value -> value.toDisplayWithoutId()))
			.execute();

		input//
			.clickInputField()
			.sendString(VALUE3.getName())
			.waitForServer();

		asserter//
			.expectInputText(VALUE3.getName())
			.expectSelectedValue(VALUE3)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE3, VALUE4)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPattern() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();

		asserter//
			.expectInputText(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupValues(VALUE2, VALUE3, VALUE4)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDown() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDownAndWrapping() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowUpAndWrapping() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPattern() {

		setup//
			.execute();

		input//
			.clickInputField()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer();

		asserter//
			.expectInputText(ILLEGAL_VALUE_NAME)
			.expectSelectedValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupValuesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndArrowDown() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectFocus()
			.expectBackdropDisplayed()
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
			.clickInputField()
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectValues(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
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
			.clickInputField()
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectInputText("foo [1]")
			.expectSelectedValue(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
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
			.clickInputField()
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectInputText("FOO [1]")
			.expectSelectedValue(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
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
			.clickInputField()
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectValues(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupValues(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
