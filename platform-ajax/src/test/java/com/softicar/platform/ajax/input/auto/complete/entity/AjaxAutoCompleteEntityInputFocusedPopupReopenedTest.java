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
	public void testBackdropDisplayedWithUniqueItemNameOnEmptyInputWhilePopupDisplayed() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString("fo")
			.waitForPopupAndServerFinished();
		backdrop//
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
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}
}
