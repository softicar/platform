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
	public void testClickFocusOnPassiveEmptyInput() {

		setup//
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabFocusOnPassiveEmptyInput() {

		setup//
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClickFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClickFocusOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabFocusOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClickFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClickFocusOnPassiveFilledInputWithUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input.focusWithClick();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabFocusOnPassiveFilledInputWithUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input.focusWithTab();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClickFocusOnActiveFilledInputWithUnavailableItem() {

		setup//
			.setListenToChange()
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input.focusWithClick();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTabFocusOnActiveFilledInputWithUnavailableItem() {

		setup//
			.setListenToChange()
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input.focusWithTab();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithClickFocusOnPassiveEmptyInput() {

		setup//
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithTabFocusOnPassiveEmptyInput() {

		setup//
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithClickFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithTabFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationWithClickFocusOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationWithTabFocusOnPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationWithClickFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationWithTabFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithClickFocusOnPassiveEmptyInput() {

		setup//
			.setMandatory()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithTabFocusOnPassiveEmptyInput() {

		setup//
			.setMandatory()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithClickFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithTabFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithTabFocusOnPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithClickFocusOnPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithTabFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithClickFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithClickFocusOnPassiveEmptyInput() {

		setup//
			.setMandatory()
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithTabFocusOnPassiveEmptyInput() {

		setup//
			.setMandatory()
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithClickFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.setMandatory()
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithTabFocusOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.setMandatory()
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationAndMandatoryWithClickFocusOnPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationAndMandatoryWithTabFocusOnPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationAndMandatoryWithClickFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testExplicitInvalidationAndMandatoryWithTabFocusOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
//			.markValueAsInvalid()
			.execute();

		input.focusWithTab();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
