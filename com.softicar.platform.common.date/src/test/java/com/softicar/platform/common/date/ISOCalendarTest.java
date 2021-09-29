package com.softicar.platform.common.date;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the class {@link ISOCalendar}.
 *
 * @author Oliver Richers
 */
public class ISOCalendarTest {

	@Test
	public void getFirstDayOfYearReturnsCorrectDay() {

		assertEquals(584388, ISOCalendar.getFirstDayOfYear(1600));
		assertEquals(730851, ISOCalendar.getFirstDayOfYear(2001));
	}

	@Test
	public void getFirstDayOfYearIsCongruentToDayCount() {

		for (int year = 0; year != 4000; ++year) {
			int dayCount = YearType.getForYear(year).getDayCount();
			int firstDay = ISOCalendar.getFirstDayOfYear(year);
			int nextFirstDay = ISOCalendar.getFirstDayOfYear(year + 1);

			assertEquals(dayCount, nextFirstDay - firstDay);
		}
	}

	@Test
	public void getYearWithDayReturnsCorrectYear() {

		assertEquals(1599, ISOCalendar.getYearWithDay(584387));
		assertEquals(1600, ISOCalendar.getYearWithDay(584388));

		assertEquals(2000, ISOCalendar.getYearWithDay(730850));
		assertEquals(2001, ISOCalendar.getYearWithDay(730851));
	}

	@Test
	public void getYearWithDayIsCongruentToFirstDayOfYear() {

		for (int year = 0; year != 4000; ++year) {
			int firstDay = ISOCalendar.getFirstDayOfYear(year);

			assertEquals(year, ISOCalendar.getYearWithDay(firstDay));
		}
	}
}
