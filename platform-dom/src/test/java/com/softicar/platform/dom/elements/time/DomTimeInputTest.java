package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.input.AbstractDomValueInputDivTest;
import com.softicar.platform.dom.input.IDomTextualInput;
import org.junit.Test;

public class DomTimeInputTest extends AbstractDomValueInputDivTest<Time> {

	public DomTimeInputTest() {

		super(() -> new DomTimeInput());
	}

	@Test
	public void testGetValue() {

		// test empty input
		assertEmptyResultForGetValue("");

		// test valid times with hours only
		assertResultForGetValue("10:00:00", "10");
		assertResultForGetValue("05:00:00", "5");

		// test valid times with hours and minutes
		assertResultForGetValue("10:30:00", "10:30");
		assertResultForGetValue("05:25:00", "5:25");

		// test valid times with hours, minutes and seconds
		assertResultForGetValue("00:00:00", "0:0:0");
		assertResultForGetValue("08:04:02", "8:4:2");
		assertResultForGetValue("23:59:59", "23:59:59");

		// test illegal combinations
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "::");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "x:y:z");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "-1:0:0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "24:0:0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:60:0");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:0:61");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:60:60");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:0:");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:0:0:");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "000");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:000");
		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, "0:0:000");
	}

	@Override
	protected void enterValue(String valueText) {

		findNode(IDomTextualInput.class).setInputValue(valueText);
	}

	private void assertResultForGetValue(String expectedValue, String valueText) {

		enterValue(valueText);
		assertEquals(expectedValue, input.getValue().get().toString());
	}
}
