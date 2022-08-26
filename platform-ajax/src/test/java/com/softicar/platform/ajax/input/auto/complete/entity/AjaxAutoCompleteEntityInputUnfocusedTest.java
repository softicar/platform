package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"3
 * Unfocused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputUnfocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithBackspaceTillAmbiguousAndEscapeAndClickOnBody() {

		setup//
			.setSelectedValue(VALUE3)
			.execute();

		input//
			.clickInputField()
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
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndEnterAndClickOnBody() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(VALUE1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEscapeAndClickOnBody() {

		setup//
			.execute();

		input//
			.clickInputField()
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
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
