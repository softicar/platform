package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfEntityInputTest extends AbstractEmfTest {

	private final EmfTestObject testObject1;
	private final EmfTestObject testObject23;

	public EmfEntityInputTest() {

		setNodeSupplier(() -> new EmfEntityInput<>(new EmfEntityInputEngine<>(EmfTestObject.TABLE)));
		this.testObject1 = insertTestObject(1, "one");
		this.testObject23 = insertTestObject(23, "twentythree");
	}

	@Test
	public void testGetValueWithId() {

		setInputValue("1");
		assertSame(testObject1, getValue());
	}

	@Test
	public void testGetValueWithOtherObjectId() {

		setInputValue("23");
		assertSame(testObject23, getValue());
	}

	/**
	 * Assert that we do <b>not</b> infer ID "23" from a leading substring ("2")
	 * of that ID.
	 */
	@Test
	public void testGetValueWithOtherObjectIdLeadingSubstring() {

		setInputValue("2");
		assertException(this::getValue, EmfI18n.PLEASE_SELECT_A_VALID_ENTRY);
	}

	/**
	 * Assert that we do <b>not</b> infer ID "23" from a tailing substring ("3")
	 * of that ID.
	 */
	@Test
	public void testGetValueWithOtherObjectIdTailingSubstring() {

		setInputValue("3");
		assertException(this::getValue, EmfI18n.PLEASE_SELECT_A_VALID_ENTRY);
	}

	@Test
	public void testGetValueWithName() {

		setInputValue("one");
		assertSame(testObject1, getValue());
	}

	@Test
	public void testGetValueWithNameAndId() {

		setInputValue("one [1]");
		assertSame(testObject1, getValue());
	}

	private Object getValue() {

		return findBody()//
			.findNode(EmfEntityInput.class)
			.assertType(EmfEntityInput.class)
			.getValue()
			.orElse(null);
	}

	private void setInputValue(String value) {

		findBody()//
			.findNode(DomTextInput.class)
			.setInputValue(value);
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
