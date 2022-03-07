package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.string.Tokenizer;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import com.softicar.platform.dom.input.IDomTextualInput;
import java.util.List;
import org.junit.Test;

public class DomTimeInputTest extends AbstractDomValueInputTest<Time> {

	public DomTimeInputTest() {

		super(() -> new DomTimeInput(null));
	}

	@Test
	public void testGetValue() {

		// test empty input
		assertEmptyResultForGetValue("::");

		// test valid dates and times
		assertResultForGetValue("00:00:00", "0:0:0");
		assertResultForGetValue("08:04:02", "8:4:2");
		assertResultForGetValue("23:59:59", "23:59:59");

		// test illegal combinations
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "x:y:z");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "-1:0:0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "24:0:0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:60:0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:60:60");
	}

	@Override
	protected void enterValue(String inputText) {

		List<DomNodeTester> inputs = findNodes(IDomTextualInput.class).toList();

		List<String> values = new Tokenizer(':', '\\').tokenize(inputText);
		for (int i = 0; i < 3; i++) {
			inputs.get(i).setInputValue(values.get(i));
		}
	}

	private void assertResultForGetValue(String expectedValue, String inputValue) {

		enterValue(inputValue);
		assertEquals(expectedValue, input.getValue().get().toString());
	}
}
