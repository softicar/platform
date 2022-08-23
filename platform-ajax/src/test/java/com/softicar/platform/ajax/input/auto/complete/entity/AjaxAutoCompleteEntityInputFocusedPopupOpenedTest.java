package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.2 Popup Opened"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with an open popup, and a focused input element.
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testValidInputWithArrowDown() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup();

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
	public void testValidInputWithArrowDownAndClickOnInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup()
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
	public void testValidInputWithArrowUp() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
			.pressArrowUp()
			.waitForPopup();

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
	public void testValidInputWithBackspace() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		input//
			.focusByClick()
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
	public void testIllegalInputWithArrowDown() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup();

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
	public void testIllegalInputWithArrowUp() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		input//
			.focusByClick()
			.pressArrowUp()
			.waitForPopup();

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
	public void testEmptyInputWithArrowDown() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup();

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
	public void testEmptyInputWithArrowDownAndClickOnInput() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressArrowDown()
			.waitForPopup()
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
	public void testEmptyInputWithArrowUp() {

		setup//
			.execute();

		input//
			.focusByClick()
			.pressArrowUp()
			.waitForPopup();

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
	public void testEmptyInputWithTypedUniquePattern() {

		setup//
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedUniquePartialPattern() {

		setup//
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedUniquePerfectMatchPattern() {

		setup//
			.add((input, engine) -> engine.setDisplayFunction(value -> value.toDisplayWithoutId()))
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedAmbiguousPattern() {

		setup//
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDown() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
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
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowDownAndWrapping() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
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
	public void testEmptyInputWithTypedAmbiguousPatternAndArrowUpAndWrapping() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(AMBIGUOUS_VALUE_NAME_CHUNK)
			.waitForServer()
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
	public void testEmptyInputWithTypedIllegalPattern() {

		setup//
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedIllegalPatternAndArrowDown() {

		setup//
			.execute();

		input//
			.focusByClick()
			.sendString(ILLEGAL_VALUE_NAME)
			.waitForServer()
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
	public void testEmptyInputWithTypedLowerPatternAndLowerValues() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setAvailableEntities(value1, value2)
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedLowerPatternAndUpperValues() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setAvailableEntities(value1, value2)
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedUpperPatternAndLowerValues() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setAvailableEntities(value1, value2)
			.execute();

		input//
			.focusByClick()
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
	public void testEmptyInputWithTypedUpperPatternAndUpperValues() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setAvailableEntities(value1, value2)
			.execute();

		input//
			.focusByClick()
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
