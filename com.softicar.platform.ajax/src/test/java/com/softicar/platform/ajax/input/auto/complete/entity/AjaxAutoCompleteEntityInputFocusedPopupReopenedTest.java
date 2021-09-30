package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteEntityInput} interaction phase
 * <b>"2.4 Popup Re-Opened"</b> (see
 * {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupReopenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testOverlayDisplayedWithUniqueItemNameOnActiveEmptyInputWhilePopupDisplayed() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString("fo")
			.waitForPopupAndLoadingFinished();
		overlay//
			.click();
		input//
			.waitForServer()
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndLoadingFinished()
			.sendString("o")
			.waitForPopupAndLoadingFinished();

		asserter//
			.expectClientValue("foo")
			.expectServerValue(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}
}
