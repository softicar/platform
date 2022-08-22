package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"2
 * Focused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testClickFocusOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input.focusWithClick();

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
	public void testTabFocusOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input.focusWithTab();

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
	public void testClickFocusOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithClick();

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
	public void testTabFocusOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithTab();

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
	public void testClickFocusOnFilledInputWithUnavailableItem() {

		setup//
			.setListenToChange()
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input.focusWithClick();

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
	public void testTabFocusOnFilledInputWithUnavailableItem() {

		setup//
			.setListenToChange()
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input.focusWithTab();

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
	public void testExplicitInvalidationWithClickFocusOnEmptyInput() {

		setup//
			.setListenToChange()
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

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
	public void testExplicitInvalidationWithTabFocusOnEmptyInput() {

		setup//
			.setListenToChange()
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

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
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationWithClickFocusOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationWithTabFocusOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
