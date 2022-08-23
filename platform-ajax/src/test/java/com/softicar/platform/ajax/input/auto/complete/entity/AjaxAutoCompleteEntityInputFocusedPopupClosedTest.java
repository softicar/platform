package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.3 Popup Closed"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with a closed popup, and a focused input
 * element.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupClosedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithArrowDownAndEscape() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopupAndServerFinished()
			.pressEsc()
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
	public void testValidInputWithBackspaceTillAmbiguousAndEscape() {

		setup//
			.setSelectedEntity(ENTITY3)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(5)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndEscape() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(ENTITY1.toDisplayStringWithId().length())
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValueNone()
			.expectServerValueNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedUniquePatternAndEnter() {

		setup//
			.setSelectedEntity(ENTITY2)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(ENTITY2.toDisplayStringWithId().length())
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testValidInputWithBackspaceTillEmptyAndTypedIllegalPatternAndBackdropClick() {

		setup//
			.setSelectedEntity(ENTITY2)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(ENTITY2.toDisplayStringWithId().length())
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValueNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithArrowDownAndEscape() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopupAndServerFinished()
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
	public void testEmptyInputWithTypedUniquePatternAndEnter() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndEscape() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndTab() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished()
			.pressTab()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY1)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedUniquePatternAndBackdropClick() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ENTITY2.getName())
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEnter() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackCountOne()
			.expectCallbackValue(ENTITY2)
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndEscape() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDownAndEnter() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForPopupAndServerFinished()
			.pressArrowDown()
			.pressEnter()
			.waitForServer();

		asserter//
			.expectValues(ENTITY3)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackValue(ENTITY3)
			.expectCallbackCountOne()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndEscape() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished()
			.pressEsc()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEmptyInputWithTypedIllegalPatternAndBackdropClick() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();
		backdrop//
			.click()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
