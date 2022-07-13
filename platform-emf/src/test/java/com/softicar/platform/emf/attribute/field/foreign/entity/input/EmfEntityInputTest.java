package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
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
		assertExceptionMessage(EmfI18n.PLEASE_SELECT_A_VALID_ENTRY, this::getValue);
	}

	@Test
	public void testGetValueWithObject23AndId() {

		setInputValue("23");
		assertSame(object23, getValue());
	}

	/**
	 * Assert that we do <b>not</b> infer ID "23" from the head (leading
	 * substring) "2" of that ID.
	 */
	@Test
	public void testGetValueWithObject23AndIdHead() {

		setInputValue("2");
		assertExceptionMessage(EmfI18n.PLEASE_SELECT_A_VALID_ENTRY, this::getValue);
	}

	/**
	 * Assert that we do <b>not</b> infer ID "23" from the tail (tailing
	 * substring) "3" of that ID.
	 */
	@Test
	public void testGetValueWithObject23AndIdTail() {

		setInputValue("3");
		assertExceptionMessage(EmfI18n.PLEASE_SELECT_A_VALID_ENTRY, this::getValue);
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
			.findNode(DomPopupButton.class)
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
