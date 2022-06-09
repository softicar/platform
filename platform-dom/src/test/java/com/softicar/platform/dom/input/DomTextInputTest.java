package com.softicar.platform.dom.input;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class DomTextInputTest extends AbstractTest {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	private final DomTextInput input;

	public DomTextInputTest() {

		this.input = new DomTextInput();
	}

	@Test
	public void testDefaultConstructor() {

		assertEquals("text", input.getAttributeValue("type").get());
		assertEquals("", input.getValueText());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetValueWithNull() {

		input.setValue(null);

		assertEquals("", input.getValueText());
		assertEquals("", input.getValueTrimmed());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetValueWithEmptyString() {

		input.setValue("");

		assertEquals("", input.getValueText());
		assertEquals("", input.getValueTrimmed());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetValueWithBlankString() {

		input.setValue("\t\r\n ");

		assertEquals("\t\r\n ", input.getValueText());
		assertEquals("", input.getValueTrimmed());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetValueWithNonEmptyString() {

		input.setValue(" foo ");

		assertEquals(" foo ", input.getValueText());
		assertEquals("foo", input.getValueTrimmed());
		assertFalse(input.isBlank());
	}
}
