package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.string.Tokenizer;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.input.IDomStringInputNode;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;

public class DomDayTimeInputTest extends AbstractTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();
	private final DomDayTimeInput input;

	public DomDayTimeInputTest() {

		this.input = new DomDayTimeInput(null);

		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Test
	public void testRetrieveValue() {

		// test empty input
		enterValues(",,,");
		assertEmpty(input.retrieveValue());

		// test valid dates and times
		assertValueForRetrieveValue("2021-12-15 08:04:02", "2021-12-15,8,4,2");
		assertValueForRetrieveValue("2021-12-15 23:59:59", "15.12.2021,23,59,59");
		assertValueForRetrieveValue("2021-12-15 00:00:00", "12/15/2021,0,0,0");

		// test illegal combinations
		assertExceptionForRetrieveValue(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay("foo"), "foo,0,0,0");
		assertExceptionForRetrieveValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1.toDisplay("x:y:z"), "2021-01-01,x,y,z");
		assertExceptionForRetrieveValue(CommonDateI18n.MISSING_DATE_SPECIFICATION, ",0,0,0");
		assertExceptionForRetrieveValue(CommonDateI18n.MISSING_TIME_SPECIFICATION, "2021-01-01,,,");
	}

	private void assertValueForRetrieveValue(String expectedValue, String inputValue) {

		enterValues(inputValue);
		assertEquals(expectedValue, input.retrieveValue().get().toString());
	}

	private void assertExceptionForRetrieveValue(IDisplayString expectedMessage, String inputValue) {

		enterValues(inputValue);
		assertException(() -> input.retrieveValue(), expectedMessage);
	}

	private void enterValues(String valueList) {

		List<DomNodeTester> inputs = findNodes(IDomStringInputNode.class).toList();

		List<String> values = new Tokenizer(',', '\\').tokenize(valueList);
		for (int i = 0; i < 4; i++) {
			inputs.get(i).setInputValue(values.get(i));
		}
	}
}
