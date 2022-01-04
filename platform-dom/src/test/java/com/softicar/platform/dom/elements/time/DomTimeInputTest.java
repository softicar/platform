package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.core.i18n.I18n1;
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

public class DomTimeInputTest extends AbstractTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();
	private final DomTimeInput input;

	public DomTimeInputTest() {

		this.input = new DomTimeInput(null);

		setNodeSupplier(() -> input);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Test
	public void testParseValue() {

		// test empty input
		enterValues("::");
		assertEmpty(input.parseValue());

		// test valid dates and times
		assertValueForParseValue("00:00:00", "0:0:0");
		assertValueForParseValue("08:04:02", "8:4:2");
		assertValueForParseValue("23:59:59", "23:59:59");

		// test illegal combinations
		assertExceptionForParseValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "x:y:z");
		assertExceptionForParseValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "-1:0:0");
		assertExceptionForParseValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "24:0:0");
		assertExceptionForParseValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:60:0");
		assertExceptionForParseValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:60:60");
	}

	private void assertValueForParseValue(String expectedValue, String inputValue) {

		enterValues(inputValue);
		assertEquals(expectedValue, input.parseValue().get().toString());
	}

	private void assertExceptionForParseValue(I18n1 expectedMessage, String inputValue) {

		enterValues(inputValue);
		assertExceptionMessage(expectedMessage.toDisplay(inputValue), () -> input.parseValue());
	}

	private void enterValues(String valueList) {

		List<DomNodeTester> inputs = findNodes(IDomStringInputNode.class).toList();

		List<String> values = new Tokenizer(':', '\\').tokenize(valueList);
		for (int i = 0; i < 3; i++) {
			inputs.get(i).setInputValue(values.get(i));
		}
	}
}
