package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import org.junit.Rule;
import org.junit.Test;

public class DomPopupTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final TestPopup popup;

	public DomPopupTest() {

		this.popup = new TestPopup();
		setNodeSupplier(DomDiv::new);
	}

	private IDomNode getBody() {

		return getEngine().getBodyNode();
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testOpen() {

		popup.open();
		assertTrue(popup.isAppended());
		assertSame(getBody(), getRoot(popup));
	}

	@Test
	public void testClose() {

		popup.close();
		assertFalse(popup.isAppended());
		assertNotSame(getBody(), getRoot(popup));
	}

	@Test
	public void testCloseWithCloseCallback() {

		CloseCallback closeCallback = new CloseCallback();
		popup.configure(settings -> settings.setCallbackBeforeClose(closeCallback));
		popup.open();
		assertEquals(0, closeCallback.getCount());
		popup.close();
		assertEquals(1, closeCallback.getCount());
		popup.close();
		assertEquals(1, closeCallback.getCount());
	}

	private IDomElement getRoot(IDomElement node) {

		IDomParentElement parent = node.getParent();
		return parent != null? getRoot(parent) : node;
	}

	private class TestPopup extends DomPopup {

		public TestPopup() {

			appendChild(new DomDiv());
		}
	}

	private class CloseCallback implements INullaryVoidFunction {

		private int count;

		public CloseCallback() {

			this.count = 0;
		}

		@Override
		public void apply() {

			count++;
		}

		public int getCount() {

			return count;
		}
	}
}
