package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.2 Popup Opened"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testArrowDownOnAmbiguousInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();

		input//
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedValue(2)
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowDownAndWrappingOnAmbiguousInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();

		input//
			.pressArrowDown()
			.pressArrowDown()
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedValue(1)
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowUpAndWrappingOnAmbiguousInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();

		input//
			.pressArrowUp()
			.pressArrowUp()
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedValue(2)
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowDownWithIllegalInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer();

		input//
			.pressArrowDown()
			.waitForServer();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
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
	public void testClickAfterArrowDownOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup();

		input//
			.focusByClick()
			.waitForServer();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedValueNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testClickAfterArrowDownOnFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup();

		input//
			.focusByClick()
			.waitForServer();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowDownOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDownAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testBackspaceOnFilledInput() {

		setup//
			.setListenToChange()
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick();

		input//
			.pressBackspace()
			.waitForPopupAndServerFinished();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testEnterOnUniqueInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick();

		input//
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
	public void testEnterOnIllegalInput() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick();

		input//
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
	public void testArrowDownOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick();

		input//
			.pressArrowDownAndWaitForPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedValueNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowDownOnUniqueInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick();

		input//
			.pressArrowDownAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowDownOnIllegalInput() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick();

		input//
			.pressArrowDownAndWaitForPopup();

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
	public void testArrowUpOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick();

		input//
			.pressArrowUpAndWaitForPopup();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITIES)
			.expectPopupSelectedValueNone()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowUpOnUniqueInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick();

		input//
			.pressArrowUpAndWaitForPopup();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueLast()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testArrowUpOnIllegalInput() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick();

		input//
			.pressArrowUpAndWaitForPopup();

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
	public void testTypeIllegalPatternOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick();

		input//
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForPopupAndServerFinished();

		asserter//
			.expectClientValue(ILLEGAL_VALUE_NAME)
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
	public void testTypeUniquePatternOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick();

		input//
			.sendString(ENTITY1.getName())
			.waitForPopupAndServerFinished();

		asserter//
			.expectClientValue(ENTITY1.getName())
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeAmbiguousPatternNameOnEmptyInput() {

		setup//
			.setListenToChange()
			.execute();

		input//
			.focusByClick();

		input//
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer();

		asserter//
			.expectClientValue(AMBIGUOUS_VALUE_NAME_CHUNK)
			.expectServerValueExceptionMessage()
			.expectIndicatorAmbiguous()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY2, ENTITY3, ENTITY4)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypePartialPatternOnEmptyInput() {

		setup//
			.execute();

		input//
			.focusByClick();

		input//
			.sendString("foo [1")
			.waitForServer();

		asserter//
			.expectClientValue("foo [1")
			.expectServerValue(ENTITY1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypePerfectMatchPatternOnEmptyInput() {

		setup//
			.add((input, engine) -> engine.setDisplayFunction(value -> value.toDisplayWithoutId()))
			.setListenToChange()
			.execute();

		input//
			.focusByClick();

		input//
			.sendString(ENTITY3.getName())
			.waitForServer();

		asserter//
			.expectClientValue(ENTITY3.getName())
			.expectServerValue(ENTITY3)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(ENTITY3, ENTITY4)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeLowerCasePatternWithLowerCaseValuesOnEmptyInput() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(value1, value2) // replace default values
			.execute();

		input//
			.focusByClick();

		input//
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectValues(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeLowerCasePatternWithUpperCaseValuesOnEmptyInput() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(value1, value2) // replace default values
			.execute();

		input//
			.focusByClick();

		input//
			.sendString("foo [1]")
			.waitForServer();

		asserter//
			.expectClientValue("foo [1]")
			.expectServerValue(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeUpperCasePatternWithLowerCaseValuesOnEmptyInput() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(value1, value2) // replace default values
			.execute();

		input//
			.focusByClick();

		input//
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectClientValue("FOO [1]")
			.expectServerValue(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testTypeUpperCasePatternWithUpperCaseValuesOnEmptyInput() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(value1, value2) // replace default values
			.execute();

		input//
			.focusByClick();

		input//
			.sendString("FOO [1]")
			.waitForServer();

		asserter//
			.expectValues(value1)
			.expectIndicatorNone()
			.expectPopupDisplayed()
			.expectPopupEntities(value1)
			.expectPopupSelectedValueFirst()
			.expectFocus()
			.expectBackdropDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
