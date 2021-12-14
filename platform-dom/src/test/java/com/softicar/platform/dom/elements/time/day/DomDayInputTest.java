package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.input.IDomStringInputNode;
import org.junit.Rule;
import org.junit.Test;

public class DomDayInputTest extends AbstractTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();
	private final DomDayInput input;

	public DomDayInputTest() {

		this.input = new DomDayInput();

		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Test
	public void testGetDayOrThrowIfInvalid() {

		// test empty input
		enterValue("");
		assertEmpty(input.getDayOrThrowIfInvalid());

		// test valid dates
		assertValueForGetDayOrThrowIfInvalid("2021-12-15", "2021-12-15");
		assertValueForGetDayOrThrowIfInvalid("2021-12-15", "15.12.2021");
		assertValueForGetDayOrThrowIfInvalid("2021-12-15", "12/15/2021");

		// test illegal dates
		assertExceptionForGetDayOrThrowIfInvalid("foo");
		assertExceptionForGetDayOrThrowIfInvalid("2021-02-29");
		assertExceptionForGetDayOrThrowIfInvalid("2021-01-66");
		assertExceptionForGetDayOrThrowIfInvalid("2021-18-01");
	}

	private void assertExceptionForGetDayOrThrowIfInvalid(String inputValue) {

		enterValue(inputValue);
		assertExceptionMessage(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay(inputValue), () -> input.getDayOrThrowIfInvalid());
	}

	private void assertValueForGetDayOrThrowIfInvalid(String expectedValue, String inputValue) {

		enterValue(inputValue);
		assertEquals(expectedValue, input.getDayOrThrowIfInvalid().get().toISOString());
	}

	private void enterValue(String value) {

		findNode(IDomStringInputNode.class).setInputValue(value);
	}
}
