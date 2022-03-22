package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteEntityInput} interaction phase
 * <b>"4 Re-Focused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputRefocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testInvalidIndicatorRetainedAfterFocusLossAndRefocusWithClickAndClickOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForLoadingFinished()
			.pressEsc();
		body//
			.click();
		input//
			.focusWithClick();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidIndicatorRetainedAfterFocusLossAndRefocusWithTabAndClickOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForLoadingFinished()
			.pressEsc()
			.pressTab()
			.focusWithClick();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
