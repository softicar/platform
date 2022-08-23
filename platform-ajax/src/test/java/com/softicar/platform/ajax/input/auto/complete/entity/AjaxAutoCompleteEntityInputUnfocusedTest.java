package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"3
 * Unfocused"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputUnfocusedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testServerValueTransferAfterSelectionWithRandomEventOnArbitraryElement() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForServer()
			.pressEnter();

		// server value is set after an arbitrary event handling was triggered
		eventTrigger//
			.trigger();

		asserter//
			.expectClientValue(ENTITY1)
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testServerValueTransferAfterAmbiguousFilteringAndClosePopupWithEscOnFilledInput() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(5)
			.waitForPopupAndServerFinished()
			.pressEsc();

		// server value is set after an arbitrary event handling was triggered
		eventTrigger//
			.trigger();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndSelectionWithClickNotOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();
		body//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testAmbiguousFilteringAndShiftingAndSelectionWithClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished();
		popup//
			.clickEntityNumber(2);

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY3)
			.assertAll();
	}

	@Test
	public void testUniqueFilteringAndSelectionWithClickOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished();
		popup//
			.clickEntityNumber(1);

		asserter//
			.expectClientValue(ENTITY1)
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testLoadingIndicatorAfterFocusLossWithIdOnEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusByClick()
				.sendString(ENTITY1.getIdAsString());
			body//
				.click();

			asserter//
				.expectClientValue(ENTITY1.getIdAsString())
				.expectServerValue(ENTITY1)
				.expectIndicatorNone()
				.expectPopupNotDisplayed()
				.expectNoFocus()
				.expectBackdropNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDeferredIndicatorUpdateAfterFocusLossWithIdOnEmptyInput() {

		setup//
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusByClick()
				.sendString(ENTITY1.getIdAsString());
			body//
				.click();
		}

		input//
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testLoadingIndicatorAfterFocusLossWithPartialIdOnEmptyInput() {

		final AjaxTestEntity value1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity value2 = new AjaxTestEntity(2, "bar");
		final AjaxTestEntity value3 = new AjaxTestEntity(32, "lol"); // the ID of this value contains the ID of another value

		setup//
			.setAvailableEntities(value1, value2, value3) // replace default values
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusByClick()
				.sendString(value2.getIdAsString());
			body//
				.click();

			asserter//
				.expectClientValue(value2.getIdAsString())
				.expectServerValue(value2)
				.expectIndicatorNone()
				.expectPopupNotDisplayed()
				.expectNoFocus()
				.expectBackdropNotDisplayed()
				.expectCallbackNone()
				.assertAll();
		}
	}

	@Test
	public void testDeferredIndicatorUpdateAfterFocusLossWithPartialIdOnEmptyInput() {

		final AjaxTestEntity value1 = new AjaxTestEntity(1, "foo");
		final AjaxTestEntity value2 = new AjaxTestEntity(2, "bar");
		final AjaxTestEntity value3 = new AjaxTestEntity(32, "lol"); // the ID of this value literally contains the ID of another value

		setup//
			.setAvailableEntities(value1, value2, value3) // replace default values
			.execute();

		try (Locker locker = inputEngine.createLocker()) {
			input//
				.focusByClick()
				.sendString(value2.getIdAsString());
			body//
				.click();
		}

		input//
			.waitForServer();

		asserter//
			.expectValues(value2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(value2)
			.assertAll();
	}
}
