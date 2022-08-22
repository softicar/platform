package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.4 Popup Re-Opened"</b> (see
 * {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupReopenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	@Ignore("Does not make sense since auto-fill was implemented.")
	public void testOverlayDisplayedWithUniqueItemNameOnEmptyInputWhilePopupDisplayed() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString("fo")
			.waitForPopupAndServerFinished();
		overlay//
			.click();
		input//
			.waitForServer()
			.focusWithClick()
			.pressDownArrow()
			.waitForPopupAndServerFinished()
			.sendString("o")
			.waitForPopupAndServerFinished();

		asserter//
			.expectValues(ENTITY1)
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
