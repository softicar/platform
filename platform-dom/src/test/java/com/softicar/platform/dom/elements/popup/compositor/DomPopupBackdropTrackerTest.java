package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import java.util.Optional;
import org.junit.Test;

public class DomPopupBackdropTrackerTest extends AbstractTest {

	private final DomPopupBackdropTracker backdropTracker;

	public DomPopupBackdropTrackerTest() {

		this.backdropTracker = new DomPopupBackdropTracker();
		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testAdd() {

		var backdrop1 = createBackdrop();
		var backdrop2 = createBackdrop();

		backdropTracker.add(createPopup(), backdrop1);
		backdropTracker.add(createPopup(), backdrop2);

		assertTrue(backdropTracker.isAnyBackdropPresent());
		assertEquals(2, backdropTracker.getAllBackdrops().size());
		assertTrue(backdropTracker.getAllBackdrops().contains(backdrop1));
		assertTrue(backdropTracker.getAllBackdrops().contains(backdrop2));
	}

	@Test
	public void testRemove() {

		var popup1 = createPopup();
		var backdrop1 = createBackdrop();
		var popup2 = createPopup();
		var backdrop2 = createBackdrop();
		backdropTracker.add(popup1, backdrop1);
		backdropTracker.add(popup2, backdrop2);

		Optional<DomModalPopupBackdrop> backdrop = backdropTracker.remove(popup1);

		assertTrue(backdrop.isPresent());
		assertSame(backdrop1, backdrop.get());
		assertEquals(1, backdropTracker.getAllBackdrops().size());
		assertFalse(backdropTracker.getAllBackdrops().contains(backdrop1));
		assertTrue(backdropTracker.getAllBackdrops().contains(backdrop2));
	}

	@Test
	public void testRemoveWithUnknownPopup() {

		var popup = new DomPopup();

		Optional<DomModalPopupBackdrop> backdrop = backdropTracker.remove(popup);

		assertFalse(backdrop.isPresent());
	}

	@Test
	public void testClear() {

		backdropTracker.add(createPopup(), createBackdrop());
		backdropTracker.add(createPopup(), createBackdrop());

		backdropTracker.clear();

		assertFalse(backdropTracker.isAnyBackdropPresent());
		assertEquals(0, backdropTracker.getAllBackdrops().size());
	}

	@Test
	public void testClearWithoutBackdrops() {

		backdropTracker.clear();

		assertFalse(backdropTracker.isAnyBackdropPresent());
		assertEquals(0, backdropTracker.getAllBackdrops().size());
	}

	@Test
	public void testGetAllBackdrops() {

		var popup1 = createPopup();
		var backdrop1 = createBackdrop();
		var popup2 = createPopup();
		var backdrop2 = createBackdrop();

		backdropTracker.add(popup1, backdrop1);
		backdropTracker.add(popup2, backdrop2);

		assertEquals(2, backdropTracker.getAllBackdrops().size());
		assertSame(backdrop2, backdropTracker.getAllBackdrops().get(0));
		assertSame(backdrop1, backdropTracker.getAllBackdrops().get(1));
	}

	@Test
	public void testIsAnyBackdropPresent() {

		backdropTracker.add(createPopup(), createBackdrop());

		assertTrue(backdropTracker.isAnyBackdropPresent());
	}

	@Test
	public void testIsAnyBackdropPresentWithoutBackdrops() {

		assertFalse(backdropTracker.isAnyBackdropPresent());
	}

	private DomPopup createPopup() {

		return new DomPopup();
	}

	private DomModalPopupBackdrop createBackdrop() {

		return new DomModalPopupBackdrop(INullaryVoidFunction.NO_OPERATION, true);
	}
}
