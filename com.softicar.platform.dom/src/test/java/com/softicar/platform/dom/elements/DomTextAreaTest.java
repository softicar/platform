package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Assert;
import org.junit.Test;

public class DomTextAreaTest extends Assert {

	private final DomTextArea textArea;

	public DomTextAreaTest() {

		CurrentDomDocument.set(new DomDocument());

		this.textArea = new DomTextArea();
	}

	@Test
	public void testGetValueAfterConstruction() {

		assertEquals("", textArea.getValue());
	}

	@Test
	public void testGetValueAfterSetValue() {

		textArea.setValue("foo");

		assertEquals("foo", textArea.getValue());
	}

	@Test
	public void testGetValueAfterSetValueWithNull() {

		textArea.setValue(null);

		assertNull(textArea.getValue());
	}
}
