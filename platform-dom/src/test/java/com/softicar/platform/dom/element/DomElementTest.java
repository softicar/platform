package com.softicar.platform.dom.element;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Assert;
import org.junit.Test;

public class DomElementTest extends Assert {

	private DomElement element;

	public DomElementTest() {

		CurrentDomDocument.set(new DomDocument());

		this.element = new DomElement() {

			@Override
			public DomElementTag getTag() {

				return DomElementTag.DIV;
			}
		};
	}

	@Test
	public void testSetCssClass() {

		element.setCssClass(() -> "foo");

		assertEquals("foo", element.getAttributeValue("class").get());
	}

	@Test
	public void testSetCssClasses() {

		element.setCssClass(() -> "foo", () -> "bar", () -> "baz");

		assertEquals("foo bar baz", element.getAttributeValue("class").get());
	}

	@Test
	public void testAddCssClass() {

		element.setCssClass(() -> "foo");
		element.addCssClass(() -> "bar");
		element.addCssClass(() -> "baz");

		assertEquals("bar baz foo", element.getAttributeValue("class").get());
	}

	@Test
	public void testAddCssClassWithRedundantCall() {

		element.setCssClass(() -> "foo");
		element.addCssClass(() -> "bar", () -> "baz");
		element.addCssClass(() -> "bar", () -> "foo");

		assertEquals("bar baz foo", element.getAttributeValue("class").get());
	}

	@Test
	public void testRemoveCssClass() {

		element.setCssClass(() -> "foo", () -> "bar", () -> "baz");
		element.removeCssClass(() -> "bar");

		assertEquals("baz foo", element.getAttributeValue("class").get());
	}

	@Test
	public void testUnsetCssClass() {

		element.setCssClass(() -> "foo", () -> "bar", () -> "baz");
		element.unsetCssClass();

		assertFalse(element.getAttributeValue("class").isPresent());
	}
}
