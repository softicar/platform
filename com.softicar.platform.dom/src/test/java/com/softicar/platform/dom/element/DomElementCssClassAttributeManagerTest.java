package com.softicar.platform.dom.element;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import java.util.List;
import org.junit.Test;

public class DomElementCssClassAttributeManagerTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private final TestElement element;
	private final DomElementCssClassAttributeManager manager;

	public DomElementCssClassAttributeManagerTest() {

		CurrentDomDocument.set(new DomDocument());
		this.element = new TestElement();
		this.manager = new DomElementCssClassAttributeManager(element);
	}

	@Test
	public void testSetClasses() {

		manager.setClasses(List.of(A));
		manager.setClasses(List.of(B, C));

		assertEquals("B C", element.getAttributeValue("class").get());
	}

	@Test
	public void testAddClasses() {

		manager.addClasses(List.of(A));
		manager.addClasses(List.of(A, B, C));

		assertEquals("A B C", element.getAttributeValue("class").get());
	}

	@Test
	public void testRemoveClasses() {

		manager.setClasses(List.of(A, B));
		manager.removeClasses(List.of(B, C));

		assertEquals("A", element.getAttributeValue("class").get());
	}

	private static class TestElement extends DomElement {

		@Override
		public DomElementTag getTag() {

			return DomElementTag.DIV;
		}
	}
}
