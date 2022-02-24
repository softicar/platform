package com.softicar.platform.dom.input;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import org.junit.Rule;
import org.junit.Test;

public class DomTextInputTest extends AbstractTest {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();
	private final DomTextInput input;

	public DomTextInputTest() {

		this.input = new DomTextInput();
	}

	@Test
	public void testDefaultConstructor() {

		assertEquals("text", input.getAttributeValue("type").get());
		assertEquals("", input.getInputText());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetInputTextWithNull() {

		input.setInputText(null);

		assertEquals("", input.getInputText());
		assertEquals("", input.getInputTextTrimmed());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetInputTextWithEmptyString() {

		input.setInputText("");

		assertEquals("", input.getInputText());
		assertEquals("", input.getInputTextTrimmed());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetInputTextWithBlankString() {

		input.setInputText("\t\r\n ");

		assertEquals("\t\r\n ", input.getInputText());
		assertEquals("", input.getInputTextTrimmed());
		assertTrue(input.isBlank());
	}

	@Test
	public void testSetInputTextWithNonEmptyString() {

		input.setInputText(" foo ");

		assertEquals(" foo ", input.getInputText());
		assertEquals("foo", input.getInputTextTrimmed());
		assertFalse(input.isBlank());
	}
}
