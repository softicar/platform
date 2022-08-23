package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"2
 * Focused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithFocusByClick() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.waitForServer();

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
	public void testValidInputWithFocusByTab() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByTab()
			.waitForServer();

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
	public void testIllegalInputWithFocusByClick() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick()
			.waitForServer();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testIllegalInputWithFocusByTab() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByTab()
			.waitForServer();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithFocusByClick() {

		setup//
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithFocusByTab() {

		setup//
			.execute();

		input//
			.focusByTab()
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
}
