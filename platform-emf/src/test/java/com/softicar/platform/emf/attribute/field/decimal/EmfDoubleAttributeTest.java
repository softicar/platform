package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfDoubleAttributeTest extends AbstractEmfTest {

	private final EmfDoubleAttribute<EmfTestObject> attribute;
	private final IEmfInput<Double> input;
	private final DomNodeTester inputTester;

	public EmfDoubleAttributeTest() {

		IDbDoubleField<EmfTestObject> field = Mockito.mock(IDbDoubleField.class);
		this.attribute = new EmfDoubleAttribute<>(field);
		this.input = attribute.createInput(null).get();

		setNodeSupplier(() -> input);
		this.inputTester = findBody().findNode(DomTextInput.class);
	}

	@Test
	public void testGetValueWithEmptyInput() {

		inputTester.setInputValue("");
		Double value = input.getValue().orElse(null);

		assertNull(value);
	}

	@Test
	public void testGetValueWithNormalInput() {

		inputTester.setInputValue("3.14");
		Double value = input.getValue().get();

		assertEquals(3.14, value, 0.01);
	}

	@Test
	public void testGetValueWithCommaAsDecimalSeparator() {

		var locale = new Locale().setDecimalSeparator(",");

		try (var scope = new LocaleScope(locale)) {
			inputTester.setInputValue("3,14");
			Double value = input.getValue().get();

			assertEquals(3.14, value, 0.01);
		}
	}

	@Test
	public void testGetValueWithInvalidInput() {

		inputTester.setInputValue("xxx");

		assertException(() -> input.getValue(), CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay("x"));
	}

	@Test
	public void testSetValue() {

		input.setValue(3.14);
		String value = inputTester.getInputValue();

		assertEquals("3.14", value);
	}

	@Test
	public void testSetValueWithNull() {

		input.setValue((Double) null);
		String value = inputTester.getInputValue();

		assertEquals("", value);
	}

}
