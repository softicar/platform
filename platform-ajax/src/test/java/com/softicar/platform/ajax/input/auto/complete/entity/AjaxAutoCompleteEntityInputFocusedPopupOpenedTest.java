package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteEntityInput} interaction phase
 * <b>"2.2 Popup Opened"</b> (see
 * {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testShiftingWithDownArrowKeyOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForLoadingFinished()
			.pressDownArrow();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueNone()
			.expectIndicatorValueAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItem(2)
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testShiftingAndForwardWrappingWithDownArrowKeyOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForLoadingFinished()
			.pressDownArrow()
			.pressDownArrow()
			.pressDownArrow();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueNone()
			.expectIndicatorValueAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItem(1)
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testShiftingBackwardWrappingWithUpArrowKeyOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForLoadingFinished()
			.pressUpArrow()
			.pressUpArrow();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueNone()
			.expectIndicatorValueAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItem(2)
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testInvalidItemNameAndShiftingWithDownArrowKeyOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForLoadingFinished()
			.pressDownArrow();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testOpenPopupWithEnterOnPassiveUniqueFilledInput() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusWithClick()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPopupNotClosedWithClickOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressDownArrow()
			.waitForPopup()
			.focusWithClick();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedItemNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testPopupNotClosedWithClickOnPassiveFilledInput() {

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
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testVisualizationWhileLoadingInOpenPopupWithTypingOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForPopupAndLoadingFinished();

		try (Locker lock = inputEngine.createLocker()) {
			input//
				.sendString("zi");

			asserter//
				.expectClientValue("bazi")
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupDisplayed()
				.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
				.expectPopupSelectedItemNone()
				.expectFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testOverlayNotDisplayedWithDownArrowOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testOverlayDisplayedWithBackspaceOnActiveFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressBackspace()
			.waitForPopupAndLoadingFinished();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValue(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEnterOnPassiveFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressEnter()
			.waitForNoPopup();

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
	public void testEnterOnPassiveFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testDownArrowOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedItemNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testDownArrowOnPassiveFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testDownArrowOnPassiveFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressDownArrowAndWaitForPopup();

		asserter//
			.expectServerValueNone()
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUpArrowOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressUpArrowAndWaitForPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedItemNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUpArrowOnPassiveFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressUpArrowAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemLast()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testUpArrowOnPassiveFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressUpArrowAndWaitForPopup();

		asserter//
			.expectServerValueNone()
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeInvalidItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeInvalidItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(INVALID_ITEM_NAME)
			.waitForPopupAndLoadingFinished();

		asserter//
			.expectClientValue(INVALID_ITEM_NAME)
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupDisplayed()
			.expectPopupEntitiesNone()
			.expectFocus()
			.expectOverlayDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeUniqueItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeUniqueItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndLoadingFinished();

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeAmbiguousItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueNone()
			.expectIndicatorValueAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeAmbiguousItemNameOnPassiveActiveInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(AMBIGUOUS_ITEM_NAME_CHUNK)
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
			.expectServerValueNone()
			.expectIndicatorValueAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypePerfectMatchItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY3.getName())
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(ENTITY3.getName())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY3)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypePerfectMatchItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusWithClick()
			.sendString(ENTITY3.getName())
			.waitForLoadingFinished();

		asserter//
			.expectClientValue(ENTITY3.getName())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY3)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithLowerCaseItemsAndLowerCasePatternOnDeductivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("foo [1]")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithUpperCaseItemsAndLowerCasePatternOnDeductivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("foo [1]")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithLowerCaseItemsAndUpperCasePatternOnDeductivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("FOO [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("FOO [1]")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithUpperCaseItemsAndUpperCasePatternOnDeductivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("FOO [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("FOO [1]")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithLowerCaseItemsAndLowerCasePatternOnRestrictivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("foo [1]")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithUpperCaseItemsAndLowerCasePatternOnRestrictivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("foo [1]")
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithLowerCaseItemsAndUpperCasePatternOnRestrictivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "bar");

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("FOO [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("FOO [1]")
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testMatchWithUpperCaseItemsAndUpperCasePatternOnRestrictivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString("FOO [1]")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("FOO [1]")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueIllegalWithPartialPatternOnRestrictivePassiveEmptyInput() {

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValueNone()
			.expectIndicatorValueIllegal()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testValueValidWithPartialPatternOnDeductivePassiveEmptyInput() {

		setup//
			.setMode(DomAutoCompleteInputValidationMode.DEDUCTIVE)
			.execute();

		input//
			.focusWithClick()
			.sendString("foo [1")
			.waitForLoadingFinished();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testWithIncludedUpperCaseItemNameOnRestrictivePassiveEmptyInput() {

		final AjaxTestEntity item1 = new AjaxTestEntity(1, "FOO");
		final AjaxTestEntity item2 = new AjaxTestEntity(2, "FOOO");

		setup//
			.setMode(DomAutoCompleteInputValidationMode.RESTRICTIVE)
			.setEntities(item1, item2) // replace default items
			.execute();

		input//
			.focusWithClick()
			.sendString(item1.toDisplayStringWithId())
			.waitForPopupAndLoadingFinished();

		asserter//
			.expectClientValue(item1.toDisplayStringWithId())
			.expectServerValueNone()
			.expectIndicatorValueValid()
			.expectPopupDisplayed()
			.expectPopupEntities(item1)
			.expectPopupSelectedItemFirst()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
