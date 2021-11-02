package com.softicar.platform.dom.elements.popup.manager;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import org.junit.Assert;
import org.junit.Test;

public class DomPopupManagerTest extends Assert {

	public DomPopupManagerTest() {

		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testGetInstance() {

		DomPopupManager instance = DomPopupManager.getInstance();

		assertNotNull(instance);
		assertSame(DomPopupManager.getInstance(), instance);
	}

	@Test
	public void testGetPopupWithSameParameters() {

		DomPopup popup1 = DomPopupManager.getInstance().getPopup("foo", PopupA.class, x -> new PopupA());
		DomPopup popup2 = DomPopupManager.getInstance().getPopup("foo", PopupA.class, x -> new PopupA());
		DomPopup popup3 = DomPopupManager.getInstance().getPopup("bar", PopupA.class, x -> new PopupA());
		DomPopup popup4 = DomPopupManager.getInstance().getPopup("foo", PopupB.class, x -> new PopupB());

		assertNotNull(popup1);
		assertNotNull(popup2);
		assertNotNull(popup3);
		assertNotNull(popup4);

		assertSame(popup1, popup2);
		assertNotSame(popup1, popup3);
		assertNotSame(popup1, popup4);
		assertNotSame(popup3, popup4);
	}

	private static class PopupA extends DomPopup {

		// nothing to add
	}

	private static class PopupB extends DomPopup {

		// nothing to add
	}
}
