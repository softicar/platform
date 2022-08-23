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
	public void testIllegalInputWithFocusLossByTabAndRefocusByClick() {

		setup//
			.execute();
		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
			.pressEscape()
			.pressTab()
			.waitForServer();

		input//
			.focusByClick()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithFocusLossByClickAndRefocusByClick() {

		setup//
			.execute();
		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
			.pressEscape();
		body//
			.click()
			.waitForServer();

		input//
			.focusByClick()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
