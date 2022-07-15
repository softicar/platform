package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.form.scope.EmfFormViewScopeAction;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.simple.scoped.EmfScopedTestObject;
import org.junit.Test;

public class EmfFormTest extends AbstractEmfFormTest<EmfScopedTestObject> {

	private static final Day SOME_DAY = Day.fromYMD(2019, 4, 3);
	private static final Day OTHER_DAY = Day.fromYMD(2019, 6, 7);
	private static final String SOME_NAME = "Foo";
	private static final String OTHER_NAME = "Bar";
	private final EmfTestObject scopeObject;

	public EmfFormTest() {

		this.scopeObject = insertTestObject("Scope");
	}

	@Test
	public void testCreationOfNewEntity() {

		// create entity and show form
		EmfScopedTestObject entity = showForm(createScopedTestObject());

		// enter input values and save entity
		enterNameAndDayAndClickSaveAndClose(SOME_NAME, SOME_DAY);

		// verify entity
		assertFalse(entity.impermanent());
		assertNotNull(entity.getId());
		assertEquals(scopeObject, entity.getScope());
		assertEquals(SOME_NAME, entity.getName());
		assertEquals(SOME_DAY, entity.getDay());

		// verify call-backs
		assertSame(entity, Asserts.assertOne(creationCallbacks));

		// verify UI
		frame.assertIsClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testCreationOfNewEntityWithExceptionInAfterCreationCallback() {

		// create entity and show form
		EmfScopedTestObject entity = showForm(createScopedTestObject());
		form.setCallbackAfterCreation(dummy -> {
			throw new SofticarUserException(IDisplayString.create("EXCEPTION FROM CALLBACK"));
		});

		// enter input values and save entity
		enterNameAndDayAndClickSaveAndClose(SOME_NAME, SOME_DAY);

		// verify entity
		assertFalse(entity.impermanent());
		assertNotNull(entity.getId());
		assertEquals(scopeObject, entity.getScope());
		assertEquals(SOME_NAME, entity.getName());
		assertEquals(SOME_DAY, entity.getDay());

		// verify UI
		findBody().assertContainsText("EXCEPTION FROM CALLBACK");
		frame.assertIsNotClosed();
	}

	@Test
	public void testCreationOfNewEntityWithMissingMandatoryField() {

		// create entity and show form
		// FIXME using createScopedTestObject() is not possible, results in wrong ROLLBACK state
		EmfScopedTestObject entity = showForm(new EmfScopedTestObject());

		// click save button without entering values
		clickButton(EmfMarker.SAVE);

		// verify entity
		assertTrue(entity.impermanent());
		assertNull(entity.getId());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		findBody()//
			.assertContainsText(EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(EmfScopedTestObject.NAME.getTitle()));
		frame.assertIsNotClosed();
	}

	@Test
	public void testCreationOfNewEntityWithoutClosing() {

		// create entity and show form
		EmfScopedTestObject entity = showForm(createScopedTestObject());

		// enter input values and save entity
		enterNameAndDayAndClickSave(SOME_NAME, SOME_DAY);

		// verify entity
		assertFalse(entity.impermanent());
		assertNotNull(entity.getId());
		assertEquals(scopeObject, entity.getScope());
		assertEquals(SOME_NAME, entity.getName());
		assertEquals(SOME_DAY, entity.getDay());

		// verify call-backs
		assertSame(entity, Asserts.assertOne(creationCallbacks));

		// verify UI
		frame.assertIsNotClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testEditOfEntity() {

		// insert entity and show form
		EmfScopedTestObject entity = showForm(insertScopedTestObject(scopeObject, SOME_NAME, SOME_DAY));

		// enter input values and save entity
		clickButton(new EmfCommonActionMarker(EmfEditAction.class));
		enterNameAndDayAndClickSaveAndClose(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getDay());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testEditOfEntityWithoutClosing() {

		// insert entity and show form
		EmfScopedTestObject entity = showForm(insertScopedTestObject(scopeObject, SOME_NAME, SOME_DAY));

		// enter input values and save entity
		clickButton(new EmfCommonActionMarker(EmfEditAction.class));
		enterNameAndDayAndClickSave(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getDay());
		assertDisplayedValue(EmfScopedTestObject.NAME, OTHER_NAME);
		assertDisplayedValue(EmfScopedTestObject.DAY, OTHER_DAY.toISOString());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsNotClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testDirectEditOfEntity() {

		// insert entity and show form
		EmfScopedTestObject entity = insertScopedTestObject(scopeObject, SOME_NAME, SOME_DAY);
		var form = appendEntityForm(entity);
		form.setDirectEditing(true);
		form.peekAndRefresh();

		// enter input values and save entity
		enterNameAndDayAndClickSaveAndClose(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getDay());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testDirectEditOfEntityWithoutClosing() {

		// insert entity and show form
		EmfScopedTestObject entity = insertScopedTestObject(scopeObject, SOME_NAME, SOME_DAY);
		var form = appendEntityForm(entity);
		form.setDirectEditing(true);
		form.peekAndRefresh();

		// enter input values and save entity
		enterNameAndDayAndClickSave(OTHER_NAME, OTHER_DAY);

		// verify entity
		assertEquals(OTHER_NAME, entity.getName());
		assertEquals(OTHER_DAY, entity.getDay());
		assertDisplayedValue(EmfScopedTestObject.NAME, OTHER_NAME);
		assertDisplayedValue(EmfScopedTestObject.DAY, OTHER_DAY.toISOString());

		// verify call-backs
		assertNone(creationCallbacks);

		// verify UI
		frame.assertIsNotClosed();
		frame.assertIsChangedOnRefreshBus(entity);
	}

	@Test
	public void testViewScopeOfEntityWithoutPermission() {

		// insert entity and show form
		showForm(insertScopedTestObject(scopeObject, SOME_NAME, SOME_DAY));

		// assert that no view scope action is available
		findNodes(new EmfCommonActionMarker(EmfFormViewScopeAction.class)).assertNone();
	}

	@Test
	public void testViewScopeOfEntityWithPermission() {

		// add current user to view permission
		scopeObject.addAuthorizedUser(user);

		// insert entity and show form
		showForm(insertScopedTestObject(scopeObject, SOME_NAME, SOME_DAY));
		clickButton(new EmfCommonActionMarker(EmfFormViewScopeAction.class));

		// assert that the scope popup is displayed
		findFormPopup(EmfTestObject.class).assertDisplayed();
	}

	@Test
	public void testViewScopeOfEntityWithoutScope() {

		// insert entity, create new frame
		var testObject = insertTestObject("Test");
		var otherFrame = new EmfFormTestFrame<EmfTestObject>();
		setNodeSupplier(() -> otherFrame);

		// show form
		var otherForm = otherFrame.appendChild(new EmfForm<>(otherFrame, testObject));
		otherForm.peekAndRefresh();

		// assert that no view scope action is available
		findNodes(new EmfCommonActionMarker(EmfFormViewScopeAction.class)).assertNone();
	}

	// ------------------------------ private ------------------------------ //

	private EmfScopedTestObject createScopedTestObject() {

		return EmfScopedTestObject.TABLE.createEntity(scopeObject);
	}

	private void enterNameAndClickSave(String name) {

		setInputValue(EmfScopedTestObject.NAME, name);
		clickButton(EmfMarker.SAVE);
	}

	private void enterNameAndDayAndClickSave(String name, Day day) {

		setInputValue(EmfScopedTestObject.DAY, day.toISOString());
		enterNameAndClickSave(name);
	}

	private void enterNameAndDayAndClickSaveAndClose(String name, Day day) {

		setInputValue(EmfScopedTestObject.NAME, name);
		setInputValue(EmfScopedTestObject.DAY, day.toISOString());
		clickButton(EmfMarker.SAVE_AND_CLOSE);
	}
}
