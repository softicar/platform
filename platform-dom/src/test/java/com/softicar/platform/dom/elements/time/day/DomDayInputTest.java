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
	public void testParseValue() {

		// test empty input
		enterValue("");
		assertEmpty(input.parseValue());

		// test valid dates
		assertValueForParseValue("2021-12-15", "2021-12-15");
		assertValueForParseValue("2021-12-15", "15.12.2021");
		assertValueForParseValue("2021-12-15", "12/15/2021");

		// test illegal dates
		assertExceptionForParseValue("foo");
		assertExceptionForParseValue("2021-02-29");
		assertExceptionForParseValue("2021-01-66");
		assertExceptionForParseValue("2021-18-01");
	}

	private void assertExceptionForParseValue(String inputValue) {

		enterValue(inputValue);
		assertExceptionMessage(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay(inputValue), () -> input.parseValue());
	}

	private void assertValueForParseValue(String expectedValue, String inputValue) {

		enterValue(inputValue);
		assertEquals(expectedValue, input.parseValue().get().toISOString());
	}

	private void enterValue(String value) {

		findNode(IDomStringInputNode.class).setInputValue(value);
	}
}
