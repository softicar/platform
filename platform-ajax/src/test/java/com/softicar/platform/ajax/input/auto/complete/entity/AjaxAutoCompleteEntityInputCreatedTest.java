package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"1
 * Created"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputCreatedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValueSelectionWithPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueSelectionWithActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueRemovalWithPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.setSelectedEntityNone()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueRemovalWithActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.setSelectedEntityNone()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueAlterationWithPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.setSelectedEntity(ENTITY2)
			.execute();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueAlterationWithActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.setSelectedEntity(ENTITY2)
			.execute();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPassiveEmptyInput() {

		setup//
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPassiveFilledInputWithUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testActiveFilledInputWithUnavailableItem() {

		setup//
			.setListenToChange()
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithPassiveEmptyInput() {

		setup//
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithActiveEmptyInput() {

		setup//
			.setListenToChange()
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithPassiveFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationWithActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithPassiveEmptyInput() {

		setup//
			.setMandatory()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithActiveEmptyInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithPassiveEmptyInput() {

		setup//
			.setMandatory()
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithActiveEmptyInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueMissing()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithPassiveFilledInput() {

		setup//
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testExplicitInvalidationAndMandatoryWithActiveFilledInput() {

		setup//
			.setListenToChange()
			.setMandatory()
			.setSelectedEntity(ENTITY1)
			.markValueAsInvalid()
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testRestrictiveInputDisplaysValidIndicatorWithPassiveFilledInput() {

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setStringValue(INVALID_ITEM_NAME)
			.execute();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
