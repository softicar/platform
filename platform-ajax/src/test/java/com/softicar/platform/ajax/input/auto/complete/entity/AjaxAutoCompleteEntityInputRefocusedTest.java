package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"4
 * Re-Focused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputRefocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testInvalidIndicatorRetainedAfterFocusLossAndRefocusWithClickAndClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_VALUE_NAME)
			.waitForServer()
			.pressEsc();
		body//
			.click();
		input//
			.focusWithClick();

		asserter//
			.expectClientValue(INVALID_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidIndicatorRetainedAfterFocusLossAndRefocusWithTabAndClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_VALUE_NAME)
			.waitForServer()
			.pressEsc()
			.pressTab()
			.focusWithClick();

		asserter//
			.expectClientValue(INVALID_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
