package com.softicar.platform.emf.attribute.field.floating;



import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import org.junit.Test;

public class EmfFloatingPointInputTest extends AbstractEmfTest {

	private final IEmfFloatingPointAttributeStrategy<Double> strategy;
	private final EmfFloatingPointInput<Double> input;
	private final DomNodeTester inputNode;

	public EmfFloatingPointInputTest() {

		this.strategy = new Strategy();
		this.input = new EmfFloatingPointInput<>(strategy);

		setNodeSupplier(() -> input);
		this.inputNode = findBody().findNode(DomTextInput.class);
	}

	// ------------------------------ getValueOrThrow() ------------------------------ //

	@Test
	public void testGetValueWithEmptyInput() {

		inputNode.setInputValue("");
		Double value = input.getValueOrThrow();

		assertNull(value);
	}

	@Test
	public void testGetValueWithNormalInput() {

		inputNode.setInputValue("3.14");
		Double value = input.getValueOrThrow();

		assertEquals(3.14, value, 0.01);
	}

	@Test
	public void testGetValueWithCommaAsDecimalSeparator() {

		inputNode.setInputValue("3,14");
		Double value = input.getValueOrThrow();

		assertEquals(3.14, value, 0.01);
	}

	@Test(expected = EmfInputException.class)
	public void testGetValueWithInvalidInput() {

		inputNode.setInputValue("xxx");
		input.getValueOrThrow();
	}

	// ------------------------------ setValue() ------------------------------ //

	@Test
	public void testSetValue() {

		input.setValue(3.14);
		String value = inputNode.getInputValue();

		assertEquals("3.14", value);
	}

	@Test
	public void testSetValueWithNull() {

		input.setValue((Double) null);
		String value = inputNode.getInputValue();

		assertEquals("", value);
	}

	private static class Strategy implements IEmfFloatingPointAttributeStrategy<Double> {

		@Override
		public String formatValue(Double value) {

			return DoubleFormatter.formatDouble(value, 6);
		}

		@Override
		public boolean isParseable(String text) {

			return DoubleParser.isDouble(text);
		}

		@Override
		public Double parseValue(String text) {

			return DoubleParser.parseDouble(text);
		}
	}
}
