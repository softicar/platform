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
	public void testRetrieveValue() {

		// test empty input
		enterValue("");
		assertEmpty(input.retrieveValue());

		// test valid dates
		assertValueForRetrieveValue("2021-12-15", "2021-12-15");
		assertValueForRetrieveValue("2021-12-15", "15.12.2021");
		assertValueForRetrieveValue("2021-12-15", "12/15/2021");

		// test illegal dates
		assertExceptionForRetrieveValue("foo");
		assertExceptionForRetrieveValue("2021-02-29");
		assertExceptionForRetrieveValue("2021-01-66");
		assertExceptionForRetrieveValue("2021-18-01");
	}

	private void assertExceptionForRetrieveValue(String inputValue) {

		enterValue(inputValue);
		assertException(() -> input.retrieveValue(), CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay(inputValue));
	}

	private void assertValueForRetrieveValue(String expectedValue, String inputValue) {

		enterValue(inputValue);
		assertEquals(expectedValue, input.retrieveValue().get().toISOString());
	}

	private void enterValue(String value) {

		findNode(IDomStringInputNode.class).setInputValue(value);
	}
}
