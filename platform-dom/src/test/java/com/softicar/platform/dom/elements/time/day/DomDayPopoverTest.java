package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Before;
import org.junit.Test;

public class DomDayPopoverTest extends AbstractTest implements IDomTestEngineMethods {

	private final IDomTestEngine engine = new DomDocumentTestEngine();

	private final Day testDay = Day.fromYMD(2022, 3, 10);

	public DomDayPopoverTest() {

		setNodeSupplier(() -> new DomDayInput(testDay));
		CurrentClock.set(new TestClock().setInstant(testDay.toDate().toInstant()));
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Before
	public void assertAllElementsPresent() {

		findNodes(DomTextInput.class).assertOne();
		findNodes(DomButton.class).assertOne();
	}

	@Test
	public void testPopupButton() {

		findNode(DomButton.class).click();
		findNodes(DomPopover.class).assertOne();
	}

	@Test
	public void testTodayButton() {

		findNode(DomButton.class).click();
		findNode(DomPopover.class).clickNode(DomI18n.TODAY);

		assertPopupClosedAndSelectedDay(testDay);
	}

	@Test
	public void testDaySelectionButton() {

		findNode(DomButton.class).click();
		findNode(DomPopover.class)//
			.findNodes(DomCell.class)
			.withText("" + testDay.getNext().getIndexWithinMonth())
			.assertOne()
			.click();
		assertPopupClosedAndSelectedDay(testDay.getNext());
	}

	private void assertPopupClosedAndSelectedDay(Day selectedDay) {

		findNodes(DomPopover.class).assertNone();
		assertEquals(selectedDay.toString(), findNode(DomTextInput.class).getInputValue());
	}
}
