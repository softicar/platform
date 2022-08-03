package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Ignore;
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
	public void testEnterOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressEnter()
			.waitForNoPopup();

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
	public void testEscOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressEsc();

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
	public void testTabOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressTab();

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
	public void testTabOnPassiveFilledInputContainingUniqueItem() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusWithClick()
			.pressTab();

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
	public void testTabOnPassiveFilledInputContainingUnavailableItem() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusWithClick()
			.pressTab();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY)
			.expectServerValueExceptionMessage()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypeInvalidItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(INVALID_ITEM_NAME);

			asserter//
				.expectClientValue(INVALID_ITEM_NAME)
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypeInvalidItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(INVALID_ITEM_NAME);

			asserter//
				.expectClientValue(INVALID_ITEM_NAME)
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypeUniqueItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(ENTITY1.getName());

			asserter//
				.expectClientValue(ENTITY1.getName())
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypeUniqueItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(ENTITY1.getName());

			asserter//
				.expectClientValue(ENTITY1.getName())
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypeAmbiguousItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(AMBIGUOUS_ITEM_NAME_CHUNK);

			asserter//
				.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypeAmbiguousItemNameOnPassiveActiveInput() {

		setup//
			.setListenToChange()
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(AMBIGUOUS_ITEM_NAME_CHUNK);

			asserter//
				.expectClientValue(AMBIGUOUS_ITEM_NAME_CHUNK)
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypePerfectMatchItemNameOnPassiveEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(ENTITY3.getName());

			asserter//
				.expectClientValue(ENTITY3.getName())
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	@Ignore("Does not make sense anymore.")
	public void testLoadingDisplayedBeforePopupWithTypePerfectMatchItemNameOnActiveEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusWithClick()
				.sendString(ENTITY3.getName());

			asserter//
				.expectClientValue(ENTITY3.getName())
				.expectServerValueNone()
				.expectIndicatorLoading()
				.expectPopupNotDisplayed()
				.expectFocus()
				.expectOverlayDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDoNotOpenPopupWithEnterOnPassiveEmptyInput() {

		setup//
			.execute();

		input//
			.focusWithClick()
			.pressEnter()
			.waitForNoPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorValueValid()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectOverlayNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
