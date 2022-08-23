package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase
 * <b>"2.2 Popup Opened"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * All tests in here should end with an open popup, and a focused input element.
 * <p>
 * FIXME change all method names to begin with the INITIAL situation of the
 * input
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputFocusedPopupOpenedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testUniqueInputWithArrowDown() {

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
	public void testUniqueInputWithArrowDownAndClick() {

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
	public void testUniqueInputWithArrowUp() {

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
	public void testUniqueInputWithBackspace() {

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
	public void testUniqueInputWithTypedPattern() {

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
	public void testUniqueInputWithTypedPartialPattern() {

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
	public void testUniqueInputWithTypedPerfectMatchPattern() {

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
	public void testUniqueInputWithTypedLowerCasePatternAndLowerCaseValues() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(value1, value2) // replace default values
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
	public void testUniqueInputWithTypedLowerCasePatternAndUpperCaseValues() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(value1, value2) // replace default values
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
	public void testUniqueInputWithTypedUpperCasePatternAndLowerCaseValues() {

		var value1 = new AjaxTestEntity(1, "foo");
		var value2 = new AjaxTestEntity(2, "bar");

		setup//
			.setEntities(value1, value2) // replace default values
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
	public void testUniqueInputWithTypedUpperCasePatternAndUpperCaseValues() {

		var value1 = new AjaxTestEntity(1, "FOO");
		var value2 = new AjaxTestEntity(2, "BAR");

		setup//
			.setEntities(value1, value2) // replace default values
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

	@Test
	public void testAmbiguousInputWithArrowDown() {

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
	public void testAmbiguousInputWithArrowDownAndWrapping() {

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
	public void testAmbiguousInputWithArrowUpAndWrapping() {

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
	public void testAmbiguousInputWithTypedPattern() {

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
	public void testIllegalInputWithTypedPattern() {

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
	public void testIllegalInputWithTypedPatternAndArrowDown() {

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
	public void testEmptyInputWithArrowDownAndClick() {

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
}
