package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.1 Popup Un-Opened"</b> (see
 * {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupUnopenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testEnterOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressEnter()
			.waitForNoPopup();

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
	public void testEscOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressEsc()
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
	public void testTabOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabWithUniqueValueOnFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabWithIllegalValueOnFilledInput() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick()
			.pressTab()
			.waitForServer();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
