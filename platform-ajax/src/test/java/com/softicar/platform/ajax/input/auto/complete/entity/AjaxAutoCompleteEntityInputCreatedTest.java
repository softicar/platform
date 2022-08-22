package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"1
 * Created"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputCreatedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValueSelectionWithFilledInput() {

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
	public void testValueRemovalWithFilledInput() {

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
	public void testValueAlterationWithFilledInput() {

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
	public void testEmptyInput() {

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
	public void testFilledInput() {

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
	public void testFilledInputWithUnavailableItem() {

		setup//
			.setListenToChange()
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorNotOkay()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMandatoryWithEmptyInput() {

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
	public void testMandatoryWithFilledInput() {

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
}
