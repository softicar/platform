package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfEntityInputTest extends AbstractEmfTest {

	private boolean changed;

	public EmfEntityInputTest() {

		setNodeSupplier(() -> new EmfEntityInput<>(new EmfEntityInputEngine<>(EmfTestUser.TABLE)));
		this.changed = false;
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

	@Test
	public void testGetValueWithBrowsePopover() {

		openBrowsePopover();
		findNode(DomPopover.class).clickNode(user.toDisplay());
		assertSame(user, getInputValue());
	}

	@Test
	public void testGetValueAndVerifyChangeCallback() {

		getEntityInput().setChangeCallback(this::setChanged);
		openBrowsePopover();
		findNode(DomPopover.class).clickNode(user.toDisplay());
		assertSame(user, getInputValue());
		assertSame(changed, true);
	}

	private EmfEntityInput<EmfTestUser> getEntityInput() {

		return findBody()//
			.findNode(EmfEntityInput.class)
			.assertType(EmfEntityInput.class);
	}

	private Object getInputValue() {

		return getEntityInput()//
			.getValue()
			.orElse(null);
	}

	private void setInputValue(String value) {

		findBody()//
			.findNode(DomTextInput.class)
			.setInputValue(value);
	}

	private void openBrowsePopover() {

		findBody()//
			.findNode(DomPopupButton.class)
			.click();
	}

	private void setChanged() {

		this.changed = true;
	}
}
