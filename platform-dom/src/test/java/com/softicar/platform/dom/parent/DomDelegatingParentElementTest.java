package com.softicar.platform.dom.parent;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.IDomTextNode;
import org.junit.Test;

public class DomDelegatingParentElementTest extends AbstractTest {

	public DomDelegatingParentElementTest() {

		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void test() {

		TestNode node = new TestNode();
		IDomTextNode textNode = node.appendText("foo");

		assertNotSame(node, textNode.getParent());
	}

	private static class TestNode extends DomDelegatingParentElement {

		private final Div outer;
		private final Div inner;

		public TestNode() {

			this.outer = new Div();
			this.inner = new Div();
		}

		@Override
		protected IDomParentElement getTargetParentElement() {

			return inner;
		}

		@Override
		protected IDomElement getTargetElement() {

			return outer;
		}

		@Override
		protected IDomNode getTargetNode() {

			return outer;
		}
	}

	private static class Div extends DomParentElement {

		@Override
		public DomElementTag getTag() {

			return DomElementTag.DIV;
		}
	}
}
