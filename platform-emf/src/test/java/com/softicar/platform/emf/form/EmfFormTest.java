package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfFormTest extends AbstractEmfFormTest {

	private static final Day SOME_DAY = Day.fromYMD(2019, 4, 3);
	private static final Day OTHER_DAY = Day.fromYMD(2019, 6, 7);
	private static final String SOME_NAME = "Foo";
	private static final String OTHER_NAME = "Bar";

	@Test
	public void testCreationOfNewEntity() {

		// create entity and show form
		EmfTestSubObject entity = showForm(new EmfTestSubObject());

		// enter input values and save entity
		setInputValue(EmfTestSubObject.NOT_NULLABLE_VALUE, 420);
		enterNameAndDayAndClickSaveAndClose(SOME_NAME, SOME_DAY);

		// verify entity
		assertFalse(entity.impermanent());
		assertNotNull(entity.getId());
		assertEquals(SOME_NAME, entity.getName());
		assertEquals(SOME_DAY, entity.getSimpleEntity().getDay());

		// verify call-backs
		assertSame(entity, Asserts.assertOne(creationCallbacks));

		// verify UI
		frame.assertIsClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testCreationOfNewEntityWithExceptionInAfterCreationCallback() {

		// create entity and show form
		EmfTestSubObject entity = showForm(new EmfTestSubObject());
		form.setCallbackAfterCreation(dummy -> {
			throw new SofticarUserException(IDisplayString.create("EXCEPTION FROM CALLBACK"));
		});

		// enter input values and save entity
		setInputValue(EmfTestSubObject.NOT_NULLABLE_VALUE, 420);
		enterNameAndDayAndClickSaveAndClose(SOME_NAME, SOME_DAY);

		// verify entity
		assertFalse(entity.impermanent());
		assertNotNull(entity.getId());
		assertEquals(SOME_NAME, entity.getName());
		assertEquals(SOME_DAY, entity.getSimpleEntity().getDay());

		// verify UI
		findBody().assertContainsText("EXCEPTION FROM CALLBACK");
		frame.assertIsNotClosed();
	}

	@Test
	public void testCreationOfNewEntityWithMissingMandatoryField() {

		// create entity and show form
		EmfTestSubObject entity = showForm(new EmfTestSubObject());

		// click save button without entering values
		clickButton(EmfMarker.SAVE);

		// verify entity
		assertTrue(entity.impermanent());
		assertNull(entity.getId());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		findBody()//
			.assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(EmfTestSubObject.NAME.getTitle()))
			.assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(EmfTestSubObject.NOT_NULLABLE_VALUE.getTitle()));
		frame.assertIsNotClosed();
	}

	@Test
	public void testCreationOfNewEntityWithoutClosing() {

		// create entity and show form
		EmfTestSubObject entity = showForm(new EmfTestSubObject());

		// enter input values and save entity
		setInputValue(EmfTestSubObject.NOT_NULLABLE_VALUE, 420);
		enterNameAndDayAndClickSave(SOME_NAME, SOME_DAY);

		// verify entity
		assertFalse(entity.impermanent());
		assertNotNull(entity.getId());
		assertEquals(SOME_NAME, entity.getName());
		assertEquals(SOME_DAY, entity.getSimpleEntity().getDay());

		// verify call-backs
		assertSame(entity, Asserts.assertOne(creationCallbacks));

		// verify UI
		frame.assertIsNotClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testEditOfEntity() {

		// insert entity and show form
		EmfTestSubObject entity = showForm(insertEntity(SOME_NAME, SOME_DAY));

		// enter input values and save entity
		clickButton(new EmfCommonActionMarker(EmfEditAction.class));
		enterNameAndDayAndClickSaveAndClose(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getSimpleEntity().getDay());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testEditOfEntityWithoutClosing() {

		// insert entity and show form
		EmfTestSubObject entity = showForm(insertEntity(SOME_NAME, SOME_DAY));

		// enter input values and save entity
		clickButton(new EmfCommonActionMarker(EmfEditAction.class));
		enterNameAndDayAndClickSave(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getSimpleEntity().getDay());
		assertDisplayedValue(EmfTestSubObject.NAME, OTHER_NAME);
		assertDisplayedValue(EmfTestObject.DAY, OTHER_DAY.toISOString());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsNotClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testDirectEditOfEntity() {

		// insert entity and show form
		EmfTestSubObject entity = insertEntity(SOME_NAME, SOME_DAY);
		EmfForm<EmfTestSubObject> form = appendEntityForm(entity);
		form.setDirectEditing(true);
		form.peekAndRefresh();

		// enter input values and save entity
		enterNameAndDayAndClickSaveAndClose(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getSimpleEntity().getDay());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testDirectEditOfEntityWithoutClosing() {

		// insert entity and show form
		EmfTestSubObject entity = insertEntity(SOME_NAME, SOME_DAY);
		EmfForm<EmfTestSubObject> form = appendEntityForm(entity);
		form.setDirectEditing(true);
		form.peekAndRefresh();

		// enter input values and save entity
		enterNameAndDayAndClickSave(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getSimpleEntity().getDay());
		assertDisplayedValue(EmfTestSubObject.NAME, OTHER_NAME);
		assertDisplayedValue(EmfTestObject.DAY, OTHER_DAY.toISOString());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsNotClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	// ------------------------------ private ------------------------------ //

	private EmfTestSubObject insertEntity(String name, Day day) {

		return new EmfTestSubObject()//
			.setName(name)
			.setSimpleEntity(new EmfTestObject().setDay(day).save())
			.setNotNullableValue(420)
			.save();
	}
}
