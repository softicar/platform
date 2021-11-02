package com.softicar.platform.emf.attribute.field.foreign.entity.input;


import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfEntityInputTest extends AbstractEmfTest {

	public EmfEntityInputTest() {

		setNodeSupplier(() -> new EmfEntityInput<>(new EmfEntityInputEngine<>(EmfTestUser.TABLE)));
	}

	@Test
	public void testGetValueWithIntegerInput() {

		setInputValue(user.getId() + "");
		assertSame(user, getInputValue());
	}

	@Test
	public void testGetValueWithDisplayNameWithId() {

		setInputValue(user.toDisplay().toString());
		assertSame(user, getInputValue());
	}

	@Test
	public void testGetValueWithFirstName() {

		setInputValue(user.getFirstName());
		assertSame(user, getInputValue());
	}

	private Object getInputValue() {

		return findBody()//
			.findNode(EmfEntityInput.class)
			.assertType(EmfEntityInput.class)
			.getValueOrThrow();
	}

	private void setInputValue(String value) {

		findBody()//
			.findNode(DomTextInput.class)
			.setInputValue(value);
	}
}
