package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.2 Popup Opened"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testShiftingWithDownArrowKeyOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForServer()
			.pressDownArrow();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItem(2)
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testShiftingAndForwardWrappingWithDownArrowKeyOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForServer()
			.pressDownArrow()
			.pressDownArrow()
			.pressDownArrow();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItem(1)
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testShiftingBackwardWrappingWithUpArrowKeyOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForServer()
			.pressUpArrow()
			.pressUpArrow();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItem(2)
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidItemNameAndShiftingWithDownArrowKeyOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForServer()
			.pressDownArrow();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testOpenPopupWithEnterOnUniqueFilledInput() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusWithClick()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPopupNotClosedWithClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopup()
			.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedItemNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPopupNotClosedWithClickOnFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopup()
			.focusWithClick();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testBackdropNotDisplayedWithDownArrowOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testBackdropDisplayedWithBackspaceOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace()
			.waitForPopupAndServerFinished();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEnterOnFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
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
	public void testEnterOnFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
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
	public void testDownArrowOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedItemNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testDownArrowOnFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testDownArrowOnFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUpArrowOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressUpArrowAndWaitForPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedItemNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUpArrowOnFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressUpArrowAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemLast()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUpArrowOnFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressUpArrowAndWaitForPopup();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeInvalidItemNameOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndServerFinished();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeUniqueItemNameOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished();

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeAmbiguousItemNameOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypePerfectMatchItemNameOnEmptyInput() {

		setup//
			.add((input, engine) -> engine.setDisplayFunction(item -> item.toDisplayWithoutId()))
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY3.getName())
			.waitForServer();

		asserter//
			.expectClientValue(ENTITY3.getName())
			.expectServerValue(ENTITY3)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY3, ENTITY4)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithLowerCaseItemsAndLowerCasePatternOnDeductiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectValues(item1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithUpperCaseItemsAndLowerCasePatternOnDeductiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectClientValue("foo [1]")
			.expectServerValue(item1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithLowerCaseItemsAndUpperCasePatternOnDeductiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectClientValue("FOO [1]")
			.expectServerValue(item1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithUpperCaseItemsAndUpperCasePatternOnDeductiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectValues(item1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueValidWithPartialPatternOnDeductiveEmptyInput() {

		setup//
			.setMode(DomAutoCompleteInputValidationMode.DEDUCTIVE)
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1")
			.waitForServer();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
