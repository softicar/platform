package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractEmfFormTest extends AbstractEmfTest {

	protected final EmfFormTestFrame frame;
	protected final EmfTestSubObject testEntity;
	protected final Collection<Object> creationCallbacks;
	protected EmfForm<EmfTestSubObject> form;

	public AbstractEmfFormTest() {

		this.frame = new EmfFormTestFrame();
		this.testEntity = insertTestEntity("foo");
		this.creationCallbacks = new ArrayList<>();

		setNodeSupplier(() -> frame);
	}

	protected EmfForm<EmfTestSubObject> appendEntityForm(EmfTestSubObject entity) {

		return frame.appendChild(new EmfForm<>(frame, entity));
	}

	protected EmfTestSubObject showForm(EmfTestSubObject entity) {

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

	protected void enterNameAndClickSave(String name) {

		setInputValue(EmfTestSubObject.NAME, name);
		clickButton(EmfMarker.SAVE);
	}

	protected void enterNameAndDayAndClickSave(String name, Day day) {

		setInputValue(EmfTestObject.DAY, day.toISOString());
		enterNameAndClickSave(name);
	}

	protected void enterNameAndDayAndClickSaveAndClose(String name, Day day) {

		setInputValue(EmfTestSubObject.NAME, name);
		setInputValue(EmfTestObject.DAY, day.toISOString());
		clickButton(EmfMarker.SAVE_AND_CLOSE);
	}

	protected void assertDisplayedValue(IStaticObject marker, String expectedValue) {

		findBody()//
			.findNode(marker)
			.assertContainsText(expectedValue);
	}

	protected EmfTestSubObject insertTestEntity(String name) {

		EmfTestSubObject entity = new EmfTestSubObject();
		entity.setName(name);
		entity.setNotNullableValue(420);
		entity.save();
		return entity;
	}
}
