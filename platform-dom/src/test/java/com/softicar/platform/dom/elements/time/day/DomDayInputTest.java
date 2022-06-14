package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.dom.input.AbstractDomValueInputDivTest;
import org.junit.Test;

public class DomDayInputTest extends AbstractDomValueInputDivTest<Day> {

	public DomDayInputTest() {

		super(DomDayInput::new);
	}

	@Test
	public void testGetValue() {

		// test blank input
		assertEmptyResultForGetValue("");
		assertEmptyResultForGetValue(" ");

		// test valid input
		assertResultForGetValue("2021-12-15", "2021-12-15");
		assertResultForGetValue("2021-12-15", "15.12.2021");
		assertResultForGetValue("2021-12-15", "12/15/2021");

		// test illegal input
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1, "foo");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1, "2021-02-29");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1, "2021-01-66");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DATE_SPECIFICATION_ARG1, "2021-18-01");
	}

	private void assertResultForGetValue(String expectedValue, String inputValue) {

		super.assertResultForGetValue(new DayParser(expectedValue).parseOrThrow(), inputValue);
	}
}
