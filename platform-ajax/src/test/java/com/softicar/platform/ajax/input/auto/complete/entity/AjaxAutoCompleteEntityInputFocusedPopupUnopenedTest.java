package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.1 Popup Un-Opened"</b> (see
 * {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with the popup not yet being opened, and a
 * focused input element.
 * <p>
 * FIXME change all method names to begin with the INITIAL situation of the
 * input
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupUnopenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testUniqueInputWithEnter() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithEnter() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithEnter() {

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
	public void testEmptyInputWithEscape() {

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
	public void testEmptyInputWithTab() {

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

	// TODO bad name
	@Test
	public void testUniqueInputWithTabWithUniqueValue() {

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

	// TODO bad name
	@Test
	public void testIllegalInputWithTabWithIllegalValue() {

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
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}
}
