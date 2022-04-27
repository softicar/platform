package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.parent.IDomParentElement;
import org.junit.Test;
import org.mockito.Mockito;

public class DomPopupTest extends AbstractTest {

	private final DomBody body;
	private final DomDocument document;
	private final TestPopup popup;

	public DomPopupTest() {

		this.document = new DomDocument();
		CurrentDomDocument.set(document);

		this.body = document.getBody();
		this.popup = new TestPopup();

		IDomEvent event = Mockito.mock(IDomEvent.class);
		document.setCurrentEvent(event);
	}

	@Test
	public void testShow() {

		popup.show();
		assertTrue(popup.isAppended());
		assertSame(body, getRoot(popup));
	}

	@Test
	public void testHide() {

		popup.close();
		assertFalse(popup.isAppended());
		assertNotSame(body, getRoot(popup));
	}

	@Test
	public void testHideWithCloseCallback() {

		CloseCallback closeCallback = new CloseCallback();
		popup.configure(settings -> settings.setCallbackBeforeClose(closeCallback));
		popup.show();
		assertEquals(0, closeCallback.getCount());
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
