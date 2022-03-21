package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Tokenizer;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import com.softicar.platform.dom.input.IDomTextualInput;
import java.util.List;
import org.junit.Test;

public class DomDayTimeInputTest extends AbstractDomValueInputTest<DayTime> {

	public DomDayTimeInputTest() {

		super(DomDayTimeInput::new);
	}

	@Test
	public void testGetValue() {

		// test empty input
		assertEmptyResultForGetValue(",,,");

		// test valid dates and times
		assertResultForGetValue("2021-12-15 08:04:02", "2021-12-15,8,4,2");
		assertResultForGetValue("2021-12-15 23:59:59", "15.12.2021,23,59,59");
		assertResultForGetValue("2021-12-15 00:00:00", "12/15/2021,0,0,0");

		// test illegal combinations
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1.toDisplay("foo"), "foo,0,0,0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1.toDisplay("x:y:z"), "2021-01-01,x,y,z");
		assertExceptionForGetValue(CommonDateI18n.MISSING_DATE_SPECIFICATION, ",0,0,0");
		assertExceptionForGetValue(CommonDateI18n.MISSING_TIME_SPECIFICATION, "2021-01-01,,,");
	}

	@Override
	protected void enterValue(String inputText) {

		List<DomNodeTester> inputs = findNodes(IDomTextualInput.class).toList();

		List<String> values = new Tokenizer(',', '\\').tokenize(inputText);
		for (int i = 0; i < 4; i++) {
			inputs.get(i).setInputValue(values.get(i));
		}
	}

	private void assertResultForGetValue(String expectedValue, String inputText) {

		enterValue(inputText);
		assertEquals(expectedValue, input.getValue().get().toString());
	}
}
