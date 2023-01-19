package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.dom.input.AbstractDomValueInputDivTest;
import org.junit.Test;

public class DomDayInputTest extends AbstractDomValueInputDivTest<Day> implements IDomDayChooserDivTest {

	public DomDayInputTest() {

		super(DomDayInput::new);
	}

	@Test
	public void testGetValue() {

		// test blank input
		assertEmptyResultForGetValue("");
		assertEmptyResultForGetValue(" ");

		// test valid input
		enterAndAssertValue("2021-12-15", "2021-12-15");
		enterAndAssertValue("2021-12-15", "15.12.2021");
		enterAndAssertValue("2021-12-15", "12/15/2021");

		// test illegal input
		assertException("foo");
		assertException("2021-02-29");
		assertException("2021-01-66");
		assertException("2021-18-01");
	}

	@Test
	public void testGetValueAfterDayPickedOnEmptyInput() {

		// assert initial state
		assertFalse(input.getValue().isPresent());

		// pick a day when none was selected before
		clickDayPopupButton();
		clickDayInMonth("13");
		assertValue("1999-12-13");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInput() {

		// setup
		enterValue(TEST_TIME.getDay().toString());

		// pick a day when none was selected before
		clickDayPopupButton();
		clickDayInMonth("13");
		assertValue("1999-12-13");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithSameDay() {

		// setup
		enterValue(TEST_TIME.getDay().toString());

		// execute
		clickDayPopupButton();
		clickDayInMonth("31");

		// assert result
		assertValue("1999-12-31");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithChangedYearAndMonth() {

		// setup
		enterValue(TEST_TIME.getDay().toString());

		// execute
		clickDayPopupButton();
		selectYear("2001");
		selectMonth("March");
		clickDayInMonth("24");

		// assert result
		assertValue("2001-03-24");
	}

	@Test
	public void testGetValueAfterDayPickedOnNonEmptyInputWithInvalidDay() {

		// setup
		enterValue("xxx");

		// execute
		clickDayPopupButton();
		clickDayInMonth("13");

		// assert result
		assertValue("1999-12-13");
	}

	private void enterAndAssertValue(String expectedValue, String inputValue) {

		// enter a value text, and test value retrieval
		assertResultForGetValue(new DayParser(expectedValue).parseOrThrow(), inputValue);

		// test value text normalization via change handlers
		assertDomTextInputValue(expectedValue);
	}

	private void assertValue(String expectedValue) {

		assertEquals(expectedValue, input.getValue().get().toString());
	}

	private void assertException(String inputValue) {

		assertExceptionForGetValue(CommonDateI18n.ILLEGAL_DATE_ARG1, inputValue);

		// assert that the value text remains unchanged
		assertDomTextInputValue(inputValue);
	}
}
