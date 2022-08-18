package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.simple.scoped.EmfScopedTestObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public abstract class AbstractEmfFormTest extends AbstractEmfTest {

	protected final Collection<Object> creationCallbacks;
	protected EmfFormTestFrame<?> frame;
	protected EmfForm<? extends IEmfTableRow<?, ?>> form;

	public AbstractEmfFormTest() {

		this.frame = null;
		this.creationCallbacks = new ArrayList<>();

		setNodeSupplier(() -> Objects.requireNonNull(frame, "Frame was not initialized."));
	}

	protected <R extends IEmfTableRow<R, ?>> EmfForm<R> appendEntityForm(R entity) {

		var frame = new EmfFormTestFrame<R>();
		this.frame = frame;

		EmfForm<R> form = new EmfForm<>(frame, entity);
		return frame.appendChild(form);
	}

	protected <R extends IEmfTableRow<R, ?>> R showForm(R entity) {

		this.form = appendEntityForm(entity);
		form.setCallbackAfterCreation(creationCallbacks::add);
		form.peekAndRefresh();
		return entity;
	}

	protected void setInputValue(IStaticObject marker, String value) {

		findBody().setInputValue(marker, value);
	}

	protected void setInputValue(IStaticObject marker, Integer value) {

		findBody().setInputValue(marker, "" + value);
	}

	protected void clickButton(IStaticObject marker) {

		findBody()//
			.findNode(marker)
			.click();
	}

	protected void assertDisplayedValue(IStaticObject marker, String expectedValue) {

		findBody()//
			.findNode(marker)
			.assertContainsText(expectedValue);
	}

	protected EmfTestObject insertTestObject(String name) {

		EmfTestObject object = new EmfTestObject();
		object.setActive(true);
		object.setName(name);
		object.setDay(Day.today());
		object.save();
		return object;
	}

	protected EmfTestSubObject insertTestSubObject(String name, Day day) {

		EmfTestSubObject entity = new EmfTestSubObject();
		entity.setName(name);
		entity.setSimpleEntity(new EmfTestObject().setDay(day).save());
		entity.setNotNullableValue(420);
		entity.save();
		return entity;
	}

	protected EmfScopedTestObject insertScopedTestObject(EmfTestObject testObject, String name, Day day) {

		EmfScopedTestObject object = new EmfScopedTestObject();
		object.setActive(true);
		object.setScope(testObject);
		object.setDay(day);
		object.setName(name);
		object.save();
		return object;
	}

	protected void enterNameAndDayAndClickSave(IStaticObject nameMarker, IStaticObject dayMarker, String name, Day day) {

		setInputValue(dayMarker, day.toISOString());
		setInputValue(nameMarker, name);
		clickButton(EmfTestMarker.FORM_SAVE);
	}

	protected void enterNameAndDayAndClickSaveAndClose(IStaticObject nameMarker, IStaticObject dayMarker, String name, Day day) {

		setInputValue(nameMarker, name);
		setInputValue(dayMarker, day.toISOString());
		clickButton(EmfTestMarker.FORM_SAVE_AND_CLOSE);
	}
}
