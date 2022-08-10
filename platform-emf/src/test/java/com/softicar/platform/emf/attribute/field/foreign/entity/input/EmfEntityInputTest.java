package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.edit.EmfEntityInputEditButton;
import com.softicar.platform.emf.attribute.input.auto.complete.EmfAutoCompleteBrowseButton;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfEntityInputTest extends AbstractEmfTest {

	private final EmfTestObject object1;
	private final EmfTestObject object23;
	private int changeCallbackCounter;

	public EmfEntityInputTest() {

		setNodeSupplier(() -> new EmfEntityInput<>(new EmfEntityInputEngine<>(EmfTestObject.TABLE)));
		this.object1 = insertTestObject(1, "one");
		this.object23 = insertTestObject(23, "twentythree");
		this.changeCallbackCounter = 0;
	}

	@Test
	public void testGetValueWithObject1AndId() {

		setInputValue("1");
		assertSame(object1, getValue());
	}

	@Test
	public void testGetValueWithObject1AndName() {

		setInputValue("one");
		assertSame(object1, getValue());
	}

	@Test
	public void testGetValueWithObject1AndNameAndId() {

		setInputValue("one [1]");
		assertSame(object1, getValue());
	}

	@Test
	public void testGetValueWithObject1AndNameAndIdIncomplete() {

		setInputValue("one [1");
		assertSame(object1, getValue());
	}

	@Test
	public void testGetValueWithObject23AndId() {

		setInputValue("23");
		assertSame(object23, getValue());
	}

	@Test
	public void testGetValueWithObject23AndIdHead() {

		setInputValue("2");
		assertSame(object23, getValue());
	}

	@Test
	public void testGetValueWithObject23AndIdTail() {

		setInputValue("3");
		assertSame(object23, getValue());
	}

	@Test
	public void testGetValueWithObject23AndClickInBrowsePopover() {

		openBrowsePopover();
		findNode(DomPopover.class).clickNode(IDisplayString.create("twentythree"));
		assertSame(object23, getValue());
		assertEquals(0, changeCallbackCounter);
	}

	@Test
	public void testGetValueWithObject23AndClickInBrowsePopoverAndChangeCallback() {

		getInput().addChangeCallback(this::handleChangeCallback);
		openBrowsePopover();
		findNode(DomPopover.class).clickNode(IDisplayString.create("twentythree"));
		assertSame(object23, getValue());
		assertEquals(1, changeCallbackCounter);
	}

	@Test
	public void testEditButtonWithoutEntity() {

		try {
			clickEditButton();
		} catch (AssertionError error) {
			assertEquals("Trying to send CLICK event to disabled node.", error.getLocalizedMessage());
		}
	}

	@Test
	public void testEditButtonWithValidEntityAndRight() {

		object23.addAuthorizedUser(user);
		openBrowsePopover();
		findNode(DomPopover.class).clickNode(IDisplayString.create("twentythree"));
		clickEditButton();
		findNodes(EmfFormPopup.class).assertOne();
		findFormPopup(EmfTestObject.class).assertDisplayed();
	}

	@Test
	public void testEditButtonWithValidEntityButWithoutRight() {

		openBrowsePopover();
		findNode(DomPopover.class).clickNode(IDisplayString.create("twentythree"));
		try {
			clickEditButton();
		} catch (AssertionError error) {
			assertEquals("Trying to send CLICK event to disabled node.", error.getLocalizedMessage());
		}
	}

	private EmfEntityInput<EmfTestObject> getInput() {

		return findBody()//
			.findNode(EmfEntityInput.class)
			.assertType(EmfEntityInput.class);
	}

	private Object getValue() {

		return getInput()//
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
			.findNode(EmfAutoCompleteBrowseButton.class)
			.click();
	}

	private void clickEditButton() {

		findBody()//
			.findNode(EmfEntityInputEditButton.class)
			.click();
	}

	private void handleChangeCallback() {

		this.changeCallbackCounter++;
	}

	private EmfTestObject insertTestObject(int id, String name) {

		int insertedId = EmfTestObject.TABLE//
			.createInsert()
			.set(EmfTestObject.ID, id)
			.set(EmfTestObject.NAME, name)
			.execute();
		return EmfTestObject.TABLE.load(insertedId);
	}
}
