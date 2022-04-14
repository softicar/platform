package com.softicar.platform.dom.input;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Test;

public class DomInputTest extends AbstractTest {

	private final DomInput input;

	public DomInputTest() {

		CurrentDomDocument.set(new DomDocument());

		this.input = new DomInput() {
			// nothing to add
		};
	}

	@Test
	public void testGetValueAfterConstuction() {

		assertNull(input.getAttribute("value").getValue());
	}

	@Test
	public void testGetValueAfterSetValueString() {

		input.setAttribute("value", "foo");

		assertEquals("foo", input.getAttribute("value").getValue());
	}

	@Test
	public void testGetValueAfterSetValueStringWithNull() {

		input.setAttribute("value", "foo");
		input.setAttribute("value", null);

		assertNull(input.getAttribute("value").getValue());
	}
}
