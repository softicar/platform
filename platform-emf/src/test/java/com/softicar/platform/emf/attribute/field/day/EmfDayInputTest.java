package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class EmfDayInputTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final Day DAY = Day.fromYMD(2022, 5, 31);
	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	private final EmfDayInput input;

	public EmfDayInputTest() {

		this.input = new EmfDayInput();

		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testSetChangeCallback() {

		var counter = new Counter();
		input.setChangeCallback(counter::increment);
		input.setValue(DAY);

		assertEquals(0, counter.count);
	}

	@Test(expected = NullPointerException.class)
	public void testSetChangeCallbackWithNull() {

		input.setChangeCallback(null);
	}

	@Test
	public void testSetValueAndHandleChangeCallback() {

		var counter = new Counter();
		input.setChangeCallback(counter::increment);

		input.setValueAndHandleChangeCallback(DAY);

		assertEquals(1, counter.count);
		assertEquals(DAY, input.getValueOrNull());
	}

	@Test
	public void testSetValueAndHandleChangeCallbackWithSameValue() {

		var counter = new Counter();
		input.setChangeCallback(counter::increment);

		input.setValueAndHandleChangeCallback(DAY);
		input.setValueAndHandleChangeCallback(DAY);
		input.setValueAndHandleChangeCallback(DAY);

		assertEquals(3, counter.count);
		assertEquals(DAY, input.getValueOrNull());
	}

	@Test
	public void testSetValueAndHandleChangeCallbackWithoutCallback() {

		input.setValueAndHandleChangeCallback(DAY);

		assertEquals(DAY, input.getValueOrNull());
	}

	@Test
	public void testSetValueAndHandleChangeCallbackWithNull() {

		var counter = new Counter();
		input.setChangeCallback(counter::increment);
		input.setValueAndHandleChangeCallback(null);

		assertEquals(1, counter.count);
		assertNull(input.getValueOrNull());
	}

	@Test
	public void testSetValueAndHandleChangeCallbackWithNullWithoutCallback() {

		input.setValueAndHandleChangeCallback(null);

		assertNull(input.getValueOrNull());
	}

	private static class Counter {

		public int count = 0;

		public void increment() {

			this.count++;
		}
	}
}
