package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.3 Popup Closed"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should
 * <ul>
 * <li>start with an open popup, and</li>
 * <li>end with a closed popup and a focused input element.</li>
 * </ul>
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupClosedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testClosePopupWithEnterOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEnterAfterTypingOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEnterOnAmbiguousInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEnterAfterArrowDownOnAmbiguousInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEnterAfterTypingOnInvalidInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeOnEmptyInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeAfterBackspaceOnEmptyInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressBackspace(7)
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
	public void testClosePopupWithEscapeOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeAfterTypingOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeAfterSelecionOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeOnAmbiguousInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeAfterBackspaceOnAmbiguousInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithEscapeOnInvalidInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithTabOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithBackdropClickOnUniqueInput() {

		setup//
			.setListenToChange()
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
	public void testClosePopupWithBackdropClickOnIllegalInput() {

		setup//
			.setListenToChange()
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
