package com.softicar.platform.common.date;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the class {@link Day}.
 *
 * @author Oliver Richers
 */
public class DayTest extends Assert {

	private Date zeroDate;
	private Day today;
	private Day firstDay;
	private Day firstOfJanuary1970;
	private Day lastOfJanuary1970;
	private Day firstOfJanuary2009;
	private Day firstOfAugust2009;
	private Day thirdOfAugust2009;
	private Day sixthOfAugust2009;
	private Day tenthOfAugust2009;

	@Before
	public void setUp() {

		zeroDate = new Date(0);

		today = Day.today();
		firstDay = Day.fromYMD(0, 1, 1);
		firstOfJanuary1970 = Day.fromYMD(1970, 1, 1);
		lastOfJanuary1970 = Day.fromYMD(1970, 1, 31);
		firstOfJanuary2009 = Day.fromYMD(2009, 1, 1);
		firstOfAugust2009 = Day.fromYMD(2009, 8, 1);
		thirdOfAugust2009 = Day.fromYMD(2009, 8, 3);
		sixthOfAugust2009 = Day.fromYMD(2009, 8, 6);
		tenthOfAugust2009 = Day.fromYMD(2009, 8, 10);
	}

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void fromZeroDate() {

		assertEquals(Day.fromDate(zeroDate), firstOfJanuary1970);
	}

	@Test
	public void fromNullDate() {

		assertNull(Day.fromDate(null));
	}

	@Test
	public void getAbsoluteIndex() {

		assertEquals(firstDay.getAbsoluteIndex(), 0);
	}

	@Test
	public void getRelativePlus() {

		assertEquals(firstOfJanuary1970.getRelative(30), lastOfJanuary1970);
	}

	@Test
	public void getRelativeMinus() {

		assertEquals(lastOfJanuary1970.getRelative(-30), firstOfJanuary1970);
	}

	@Test
	public void getIndexWithinYear() {

		assertEquals(firstOfJanuary2009.getIndexWithinYear(), 1);
	}

	@Test
	public void getIndexSince1970() {

		assertEquals(lastOfJanuary1970.getIndexSince1970(), 30);
	}

	@Test
	public void getIndexWithinWeek() {

		assertEquals(firstOfAugust2009.getIndexWithinWeek(), 6);
	}

	@Test
	public void getWeekDay() {

		assertEquals(firstOfAugust2009.getWeekday(), Weekday.SATURDAY);
	}

	@Test
	public void getWeek() {

		assertEquals(firstOfAugust2009.getWeek(), Year.get(2009).getWeeks().get(31));
	}

	@Test
	public void get1970() {

		assertEquals(Day.get1970(), firstOfJanuary1970);
	}

	@Test
	public void getBegin() {

		assertEquals(firstOfAugust2009.getBegin(), DayTime.fromYMD_HMS(2009, 8, 1, 0, 0, 0));
	}

	@Test
	public void getEnd() {

		assertEquals(firstOfAugust2009.getEnd(), DayTime.fromYMD_HMS(2009, 8, 1, 23, 59, 59));
	}

	@Test
	public void format() {

		assertEquals(firstOfAugust2009.format("%dom%.%m%.%y%"), "01.08.2009");
	}

	@Test
	public void testToString() {

		assertEquals(firstOfAugust2009.toString(), "2009-08-01");
	}

	@Test
	public void testToISOString() {

		assertEquals(firstOfAugust2009.toISOString(), "2009-08-01");
	}

	@Test
	public void testToGermanString() {

		assertEquals(firstOfAugust2009.toGermanString(), "01.08.2009");
	}

	@Test
	public void testToDMString() {

		assertEquals(firstOfAugust2009.toDMString(), "1.8.");
	}

	@Test
	public void testToHumanString() {

		assertEquals(today.getRelative(-3).toHumanString(), Day.today().getRelative(-3).toString());
		assertEquals(today.getRelative(-2).toHumanString(), CommonDateI18n._2_DAYS_AGO.toString());
		assertEquals(today.getRelative(-1).toHumanString(), CommonDateI18n.YESTERDAY.toString());
		assertEquals(today.getRelative(0).toHumanString(), CommonDateI18n.TODAY.toString());
		assertEquals(today.getRelative(1).toHumanString(), CommonDateI18n.TOMORROW.toString());
		assertEquals(today.getRelative(2).toHumanString(), Day.today().getRelative(2).toString());
	}

	@Test
	public void testToYYMMDDString() {

		assertEquals(firstOfAugust2009.toYYMMDDString(), "090801");
		assertEquals(lastOfJanuary1970.toYYMMDDString(), "700131");
	}

	@Test
	public void testToDayTime() {

		assertEquals(firstOfAugust2009.toDayTime(), DayTime.fromYMD_HMS(2009, 8, 1, 0, 0, 0));
	}

	@Test
	public void testToDayTimePlusTime() {

		assertEquals(firstOfAugust2009.toDayTime(4000), DayTime.fromYMD_HMS(2009, 8, 1, 1, 6, 40));
	}

	@Test
	public void testToDate() {

		assertEquals(firstOfAugust2009.toDate().getTime(), firstOfAugust2009.toMillis());
	}

	@Test
	public void testToMillis() {

		long time1970 = firstOfJanuary1970.toMillis();
		assertEquals(Day.fromYMD(1970, 1, 2).toMillis(), time1970 + 24 * 60 * 60 * 1000);
	}

	@Test
	public void testParse() {

		assertEquals(Day.parse(DateFormat.YYYYMMDD, "20090801"), firstOfAugust2009);
	}

	@Test
	public void testToday() {

		assertEquals(Day.today(), Day.fromDate(new Date()));
	}

	@Test
	public void testTodayWithTestClock() {

		final Day expectedDay = Day.fromYMD(2017, 7, 14);
		final long millis = 1500000000000L;

		CurrentClock.set(new TestClock(ZoneOffset.UTC, Instant.ofEpochMilli(millis)));

		assertEquals(expectedDay, Day.today());
	}

	@Test
	public void testIsMonday() {

		assertFalse(firstOfAugust2009.isMonday());
		assertTrue(thirdOfAugust2009.isMonday());
	}

	@Test
	public void testGetNextWeekDay() {

		assertTrue(firstOfAugust2009.getNextWeekDay(Weekday.MONDAY).equals(thirdOfAugust2009));
		assertTrue(thirdOfAugust2009.getNextWeekDay(Weekday.THURSDAY).equals(sixthOfAugust2009));
		assertTrue(thirdOfAugust2009.getNextWeekDay(Weekday.MONDAY).equals(tenthOfAugust2009));
	}

	@Test
	public void testEquals() {

		assertEquals(Day.fromYMD(2015, 9, 28), Day.fromYMD(2015, 9, 28));
		assertNotEquals(Day.fromYMD(2015, 9, 28), Day.fromYMD(2015, 9, 29));
	}

	@Test
	public void testHashCode() {

		assertEquals(Day.fromYMD(2015, 9, 28).hashCode(), Day.fromYMD(2015, 9, 28).hashCode());
	}
}
