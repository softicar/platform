package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomValueOption;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Before;
import org.junit.Test;

public class DomDayPopoverTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final Day testDay = Day.fromYMD(2022, 3, 10);

	public DomDayPopoverTest() {

		setNodeSupplier(DomDayInput::new);
		CurrentClock.set(new TestClock().setInstant(testDay.toDate().toInstant()));
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Before
	public void assertAllElementsPresent() {

		findNodes(DomTextInput.class).assertOne();
		findNodes(DomButton.class).assertOne();
	}

	@Test
	public void testPopoverButton() {

		findNode(DomButton.class).click();
		findNodes(DomPopover.class).assertOne();
	}

	@Test
	public void testTodayButton() {

		findNode(DomButton.class).click();
		findNode(DomPopover.class).clickNode(DomI18n.TODAY);

		assertPopoverClosedAndSelectedDay(testDay);
	}

	@Test
	public void testEscapeToClosePopover() {

		findNode(DomButton.class).click();
		findNode(DomPopupFrame.class).sendEvent(DomEventType.ESCAPE);
		assertPopoverClosedAndSelectedDay(null);
	}

	@Test
	public void testDaySelectionCell() {

		findNode(DomButton.class).click();
		findNode(DomPopover.class)//
			.findNodes(DomCell.class)
			.withText("" + testDay.getNext().getIndexWithinMonth())
			.assertOne()
			.click();
		assertPopoverClosedAndSelectedDay(testDay.getNext());
	}

	@Test
	public void testCorrectDaySelectedWhenInputIsEmpty() {

		findNode(DomButton.class).click();
		assertEquals(testDay, getDomDayChooserDiv().getDay());
	}

	@Test
	public void testCorrectDaySelectedWhenInputIsNotEmpty() {

		findNode(DomTextInput.class).setInputValue(testDay.getNext().toString());
		findNode(DomButton.class).click();
		assertEquals(testDay.getNext(), getDomDayChooserDiv().getDay());
	}

	@Test
	public void testCorrectDaySelectedWhenInputContainsGarbage() {

		findNode(DomTextInput.class).setInputValue("2022-02-30");
		findNode(DomButton.class).click();
		assertEquals(testDay, getDomDayChooserDiv().getDay());
	}

	@Test
	public void testCorrectDaySelectedWhenChangingYearAndMonthAndDismissingPopover() {

		findNode(DomButton.class).click();
		findNodes(AbstractDomValueSelect.class)//
			.first()
			.click()
			.findNodes(DomValueOption.class)
			.withText("2023")
			.assertOne()
			.click();
		findNodes(AbstractDomValueSelect.class)//
			.last()
			.click()
			.findNodes(DomValueOption.class)
			.first()
			.click();
		findNode(DomPopupFrame.class).sendEvent(DomEventType.ESCAPE);
		assertPopoverClosedAndSelectedDay(null);
	}

	private DomDayChooserDiv getDomDayChooserDiv() {

		return findNode(DomPopover.class)//
			.findNode(DomDayChooserDiv.class)
			.assertType(DomDayChooserDiv.class);
	}

	private void assertPopoverClosedAndSelectedDay(Day selectedDay) {

		findNodes(DomPopover.class).assertNone();
		assertEquals(selectedDay != null? selectedDay.toString() : "", findNode(DomTextInput.class).getInputValue());
	}
}
