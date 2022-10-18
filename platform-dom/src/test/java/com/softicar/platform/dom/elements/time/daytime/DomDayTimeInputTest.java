package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.time.day.IDomDayChooserDivTest;
import com.softicar.platform.dom.input.AbstractDomValueInputDivTest;
import org.junit.Test;

public class DomDayTimeInputTest extends AbstractDomValueInputDivTest<DayTime> implements IDomDayChooserDivTest {

	public DomDayTimeInputTest() {

		super(DomDayTimeInput::new);
	}

	@Test
	public void testGetValue() {

		// test empty input
		assertEmptyResultForGetValue("");

		// test valid dates and times
		enterAndAssertValue("2021-12-15 08:04:02", "2021-12-15 8:4:2");
		enterAndAssertValue("2021-12-15 23:59:59", "15.12.2021 23:59:59");
		enterAndAssertValue("2021-12-15 00:00:00", "12/15/2021 0:0:0");
		enterAndAssertValue("2021-01-01 00:00:00", "2021-01-01");

		// test illegal combinations
		assertException("foo 0:0:0");
		assertException("2021-01-01 x:y:z");
		assertException("0:0:0");
	}

	@Test
	public void testGetValueAfterDayPickedOnEmptyInput() {

		// assert initial state
		assertFalse(input.getValue().isPresent());

		// execute
		clickDayPopupButton();
		clickDayInMonth("13");

		// assert result
		assertValue("1999-12-13 00:00:00");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInput() {

		// setup
		enterValue(TEST_TIME.toString());

		// execute
		clickDayPopupButton();
		clickDayInMonth("13");

		// assert result
		assertValue("1999-12-13 14:30:59");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithSameDay() {

		// setup
		enterValue(TEST_TIME.toString());

		// execute
		clickDayPopupButton();
		clickDayInMonth("31");

		// assert result
		assertValue("1999-12-31 14:30:59");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithChangedYearAndMonth() {

		// setup
		enterValue(TEST_TIME.toString());

		// execute
		clickDayPopupButton();
		selectYear("2001");
		selectMonth("March");
		clickDayInMonth("24");

		// assert result
		assertValue("2001-03-24 14:30:59");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithInvalidDayAndValidTime() {

		// setup
		enterValue("xxx " + TEST_TIME.getTime());

		// execute
		clickDayPopupButton();
		clickDayInMonth("13");

		// assert result
		assertValue("1999-12-13 00:00:00");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithValidDayAndInvalidTime() {

		// setup
		enterValue(TEST_TIME.getDay() + " xxx");

		// execute
		clickDayPopupButton();
		clickDayInMonth("13");

		// assert result
		assertValue("1999-12-13 00:00:00");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithInvalidDayAndInvalidTime() {

		// setup
		enterValue("xxx yyy");

		// execute
		clickDayPopupButton();
		clickDayInMonth("13");

		// assert result
		assertValue("1999-12-13 00:00:00");
	}

	private void enterAndAssertValue(String expectedValue, String inputValue) {

		// enter a value text
		enterValue(inputValue);

		// test value retrieval
		assertEquals(expectedValue, input.getValue().get().toString());

		// test value text normalization via change handlers
		assertDomTextInputValue(expectedValue);
	}

	private void assertValue(String expectedValue) {

		assertEquals(expectedValue, input.getValue().get().toString());
	}

	private void assertException(String inputValue) {

		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DAYTIME_SPECIFICATION_ARG1, inputValue);

		// assert that the value text remains unchanged
		assertDomTextInputValue(inputValue);
	}
}
