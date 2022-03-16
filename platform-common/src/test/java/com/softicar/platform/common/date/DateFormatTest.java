package com.softicar.platform.common.date;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the class {@link DateFormat}.
 *
 * @author Oliver Richers
 */
public class DateFormatTest {

	@Test
	public void parsesYYYY_MM_DD() {

		Day day = DateFormat.YYYY_MM_DD.parseDay("2007-03-27");

		assertEquals(2007, day.getYear().getAbsoluteIndex());
		assertEquals(3, day.getMonth().getIndexWithinYear());
		assertEquals(27, day.getIndexWithinMonth());
	}

	@Test
	public void parsesDD_MM_YYYY() {

		Day day = DateFormat.DD_MM_YYYY.parseDay("14.11.2060");

		assertEquals(2060, day.getYear().getAbsoluteIndex());
		assertEquals(11, day.getMonth().getIndexWithinYear());
		assertEquals(14, day.getIndexWithinMonth());
	}

	@Test
	public void parsesMM_DD_YYYY() {

		Day day = DateFormat.MM_DD_YYYY.parseDay("12/28/1903");

		assertEquals(1903, day.getYear().getAbsoluteIndex());
		assertEquals(12, day.getMonth().getIndexWithinYear());
		assertEquals(28, day.getIndexWithinMonth());
	}

	@Test
	public void parsesYYMMDD() {

		Day day = DateFormat.YYMMDD.parseDay("070304");

		assertEquals(7, day.getYear().getAbsoluteIndex() % 100);
		assertEquals(3, day.getMonth().getIndexWithinYear());
		assertEquals(4, day.getIndexWithinMonth());
	}

	@Test
	public void parsesYYWWD() {

		Day day = DateFormat.YYWWD.parseDay("88335");

		assertEquals(88, day.getYear().getAbsoluteIndex() % 100);
		assertEquals(33, day.getWeek().getIndexWithinYear());
		assertEquals(5, day.getIndexWithinWeek());
	}
}
