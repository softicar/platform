package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import org.junit.Test;

public class DomPopupStateTrackerTest extends AbstractTest {

	private final DomPopupStateTracker stateTracker;

	public DomPopupStateTrackerTest() {

		this.stateTracker = new DomPopupStateTracker();
		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testSetOpenWithOpenPopup() {

		var popup = new DomPopup();
		stateTracker.setOpen(popup);

		stateTracker.setOpen(popup);

		assertTrue(stateTracker.isOpen(popup));
		assertEquals(1, stateTracker.getAllOpenInReverseOrder().size());
	}

	@Test
	public void testSetOpenWithClosedPopup() {

		var popup = new DomPopup();
		stateTracker.setOpen(popup);
		stateTracker.setClosed(popup);

		stateTracker.setOpen(popup);

		assertTrue(stateTracker.isOpen(popup));
		assertEquals(1, stateTracker.getAllOpenInReverseOrder().size());
	}

	@Test
	public void testSetOpenWithUnknownPopup() {

		var popup = new DomPopup();

		stateTracker.setOpen(popup);

		assertTrue(stateTracker.isOpen(popup));
		assertEquals(1, stateTracker.getAllOpenInReverseOrder().size());
	}

	@Test(expected = NullPointerException.class)
	public void testSetOpenWithNull() {

		stateTracker.setOpen(null);
	}

	@Test
	public void testSetClosedWithOpenPopup() {

		var popup = new DomPopup();
		stateTracker.setOpen(popup);

		stateTracker.setClosed(popup);

		assertFalse(stateTracker.isOpen(popup));
		assertEquals(0, stateTracker.getAllOpenInReverseOrder().size());
	}

	@Test
	public void testSetClosedWithClosedPopup() {

		var popup = new DomPopup();
		stateTracker.setOpen(popup);
		stateTracker.setClosed(popup);

		stateTracker.setClosed(popup);

		assertFalse(stateTracker.isOpen(popup));
		assertEquals(0, stateTracker.getAllOpenInReverseOrder().size());
	}

	@Test
	public void testSetClosedWithUnknownPopup() {

		var popup = new DomPopup();

		stateTracker.setClosed(popup);

		assertFalse(stateTracker.isOpen(popup));
		assertEquals(0, stateTracker.getAllOpenInReverseOrder().size());
	}

	@Test(expected = NullPointerException.class)
	public void testSetClosedWithNull() {

		stateTracker.setClosed(null);
	}

	@Test
	public void testGetAllOpenInReverseOrder() {

		var popup1 = new DomPopup();
		var popup2 = new DomPopup();
		var popup3 = new DomPopup();
		stateTracker.setOpen(popup1);
		stateTracker.setOpen(popup2);
		stateTracker.setClosed(popup2);
		stateTracker.setOpen(popup3);

		var allOpen = stateTracker.getAllOpenInReverseOrder();

		assertEquals(2, allOpen.size());
		assertTrue(allOpen.contains(popup1));
		assertTrue(allOpen.contains(popup3));
	}

	@Test
	public void testGetAllOpenInReverseOrderWithoutPopups() {

		var popups = stateTracker.getAllOpenInReverseOrder();
		assertTrue(popups.isEmpty());
	}

	@Test
	public void testIsOpenWithOpenPopup() {

		var popup = new DomPopup();
		stateTracker.setOpen(popup);

		boolean open = stateTracker.isOpen(popup);

		assertTrue(open);
	}

	@Test
	public void testIsOpenWithClosedPopup() {

		var popup = new DomPopup();
		stateTracker.setOpen(popup);
		stateTracker.setClosed(popup);

		boolean open = stateTracker.isOpen(popup);

		assertFalse(open);
	}

	@Test
	public void testIsOpenWithUnknownPopup() {

		boolean open = stateTracker.isOpen(new DomPopup());
		assertFalse(open);
	}
}
