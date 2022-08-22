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
			.focusWithClick()
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
			.focusWithClick()
			.pressEsc();

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
			.focusWithClick()
			.pressTab();

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
	public void testTabOnFilledInputContainingUniqueValue() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressTab();

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
	public void testTabOnFilledInputContainingUnavailableValue() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressTab();

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

	@Test
	public void testDoNotOpenPopupWithEnterOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
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
}
