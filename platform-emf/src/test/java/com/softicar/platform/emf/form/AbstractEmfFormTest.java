package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.simple.scoped.EmfScopedTestObject;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractEmfFormTest<R extends IEmfTableRow<R, ?>> extends AbstractEmfTest {

	protected final EmfFormTestFrame<R> frame;
	protected final Collection<Object> creationCallbacks;
	protected EmfForm<R> form;

	public AbstractEmfFormTest() {

		this.frame = new EmfFormTestFrame<>();
		this.creationCallbacks = new ArrayList<>();

		setNodeSupplier(() -> frame);
	}

	protected EmfForm<R> appendEntityForm(R entity) {

		return frame.appendChild(new EmfForm<>(frame, entity));
	}

	protected R showForm(R entity) {

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

	protected EmfTestSubObject insertTestSubObject(String name) {

		EmfTestSubObject entity = new EmfTestSubObject();
		entity.setName(name);
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
}
