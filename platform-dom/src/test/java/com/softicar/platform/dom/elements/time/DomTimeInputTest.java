package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.input.AbstractDomValueInputDivTest;
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
		enterAndAssertValue("10:00:00", "10");
		enterAndAssertValue("05:00:00", "5");

		// test valid times with hours and minutes
		enterAndAssertValue("10:30:00", "10:30");
		enterAndAssertValue("05:25:00", "5:25");

		// test valid times with hours, minutes and seconds
		enterAndAssertValue("00:00:00", "0:0:0");
		enterAndAssertValue("00:00:00", "000");
		enterAndAssertValue("00:00:00", "0:000");
		enterAndAssertValue("00:00:00", "0:0:000");
		enterAndAssertValue("08:04:02", "8:4:2");
		enterAndAssertValue("23:59:59", "23:59:59");

		// test illegal combinations
		assertException("::");
		assertException("x:y:z");
		assertException("-1:0:0");
		assertException("24:0:0");
		assertException("0:60:0");
		assertException("0:0:61");
		assertException("0:60:60");
		assertException("0:");
		assertException("0:0:");
		assertException("0:0:0:");
	}

	private void enterAndAssertValue(String expectedValue, String inputValue) {

		// enter a value text
		enterValue(inputValue);

		// test value retrieval
		assertEquals(expectedValue, input.getValue().get().toString());

		// test value text normalization via change handlers
		assertDomTextInputValue(expectedValue);
	}

	private void assertException(String inputValue) {

		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1, inputValue);

		// assert that the value text remains unchanged
		assertDomTextInputValue(inputValue);
	}
}
