package com.softicar.platform.dom.node;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.softicar.platform.common.container.iterable.Iterables;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.attribute.DomAttribute;
import com.softicar.platform.dom.attribute.DomAttributeRegistry;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.engine.IDomEngine;
import java.util.function.Consumer;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * Test cases for {@link AbstractDomNode}.
 *
 * @author Oliver Richers
 */
public class AbstractDomNodeTest extends AbstractTest {

	private final IDomDocument document;
	private final IDomEngine domEngine;
	private final DomAttributeRegistry attributeRegistry;
	private final AbstractDomNode node;

	public AbstractDomNodeTest() {

		this.document = mock(IDomDocument.class);
		this.domEngine = mock(IDomEngine.class);
		this.attributeRegistry = new DomAttributeRegistry(document);

		when(document.getAttributeRegistry()).thenReturn(attributeRegistry);
		when(document.getEngine()).thenReturn(domEngine);

		this.node = new AbstractDomNode(document) {

			@Override
			public void executeAlert(IDisplayString message) {

				throw new UnsupportedOperationException();
			}

			@Override
			public void executeConfirm(INullaryVoidFunction confirmHandler, IDisplayString message) {

				throw new UnsupportedOperationException();
			}

			@Override
			public void executePrompt(Consumer<String> promptHandler, IDisplayString message, String defaultValue) {

				throw new UnsupportedOperationException();
			}
		};
	}

	// -------------------- ATTRIBUTE METHODS -------------------- //

	@Test
	public void testGetAttributeAfterConstruction() {

		assertNull(node.getAttribute("foo").getValue());
		assertFalse(node.getAttributeValue("foo").isPresent());
	}

	@Test
	public void testSetAttribute() {

		node.setAttribute("foo", "bar");

		assertEquals("bar", node.getAttribute("foo").getValue());
		assertEquals("bar", node.getAttributeValue("foo").get());
	}

	@Test
	public void testSetAttributeWithNullValue() {

		node.setAttribute("foo", null);

		assertNull(node.getAttribute("foo").getValue());
		assertFalse(node.getAttributeValue("foo").isPresent());
	}

	@Test
	public void testSetAttributeWithBooleanValue() {

		node.setAttribute("foo", true);

		verify(domEngine).setNodeAttribute(same(node), eq(new DomAttribute("foo", "true", false)));
		assertEquals("true", node.getAttribute("foo").getValue());
		assertEquals("true", node.getAttributeValue("foo").get());
		assertEquals("true", node.getAttribute("foo").getValue_JS());
	}

	@Test
	public void testSetAttributeWithIntegerValue() {

		node.setAttribute("foo", 42);

		verify(domEngine).setNodeAttribute(same(node), eq(new DomAttribute("foo", "42", false)));
		assertEquals("42", node.getAttribute("foo").getValue());
		assertEquals("42", node.getAttributeValue("foo").get());
		assertEquals("42", node.getAttribute("foo").getValue_JS());
	}

	@Test
	public void testSetAttributeWithStringValue() {

		node.setAttribute("foo", "bar");

		verify(domEngine).setNodeAttribute(same(node), eq(new DomAttribute("foo", "bar", true)));
		assertEquals("bar", node.getAttribute("foo").getValue());
		assertEquals("bar", node.getAttributeValue("foo").get());
		assertEquals("'bar'", node.getAttribute("foo").getValue_JS());
	}

	@Test
	public void testSetAttributeWithUnquotedStringValue() {

		node.setAttribute("foo", "bar", false);

		verify(domEngine).setNodeAttribute(same(node), eq(new DomAttribute("foo", "bar", false)));
		assertEquals("bar", node.getAttribute("foo").getValue());
		assertEquals("bar", node.getAttributeValue("foo").get());
		assertEquals("bar", node.getAttribute("foo").getValue_JS());
	}

	@Test
	public void testUnsetAttribute() {

		node.setAttribute("foo", "bar");
		node.unsetAttribute("foo");

		InOrder inOrder = Mockito.inOrder(domEngine);
		inOrder.verify(domEngine).setNodeAttribute(same(node), eq(new DomAttribute("foo", "bar", true)));
		inOrder.verify(domEngine).setNodeAttribute(same(node), eq(new DomAttribute("foo", null, false)));
		assertNull(node.getAttribute("foo").getValue());
		assertFalse(node.getAttributeValue("foo").isPresent());
	}

	@Test
	public void testGetAttributes() {

		assertEquals(0, Iterables.getSize(node.getAttributes()));

		node.setAttribute("foo", "bar");
		assertEquals(1, Iterables.getSize(node.getAttributes()));

		node.setAttribute("foo", "baz");
		assertEquals(1, Iterables.getSize(node.getAttributes()));

		node.setAttribute("bar", 33);
		assertEquals(2, Iterables.getSize(node.getAttributes()));
	}

	// -------------------- ATTRIBUTE MAP METHODS -------------------- //

	@Test
	public void setsBooleanAttributeInMap() {

		node.getAccessor().setAttributeInMap("foo", true);

		verify(domEngine, never()).setNodeAttribute(any(IDomNode.class), any(IDomAttribute.class));
		assertEquals("true", node.getAttribute("foo").getValue());
		assertEquals("true", node.getAttribute("foo").getValue_JS());
	}

	@Test
	public void setsStringAttributeInMap() {

		node.getAccessor().setAttributeInMap("foo", "bar");

		verify(domEngine, never()).setNodeAttribute(any(IDomNode.class), any(IDomAttribute.class));
		assertEquals("bar", node.getAttribute("foo").getValue());
		assertEquals("'bar'", node.getAttribute("foo").getValue_JS());
	}

	@Test
	public void setsUnquotedStringAttributeInMap() {

		node.getAccessor().setAttributeInMap("foo", "bar", false);

		verify(domEngine, never()).setNodeAttribute(any(IDomNode.class), any(IDomAttribute.class));
		assertEquals("bar", node.getAttribute("foo").getValue());
		assertEquals("bar", node.getAttribute("foo").getValue_JS());
	}
}
