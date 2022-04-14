package com.softicar.platform.common.date;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DurationTest extends AbstractTest {

	private static final int SECONDS_PER_HOUR = 3600;
	private static final int SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;
	private static final DayTime MORNING = new DayTime(Day.fromYMD(2017, 1, 1), 8, 14, 31);
	private static final DayTime NOON = new DayTime(Day.fromYMD(2017, 1, 1), 12, 12, 45);
	private static final DayTime AFTERNOON = new DayTime(Day.fromYMD(2017, 1, 1), 16, 45, 13);

	@Test
	public void testGetTotalSecondsWithZeroDifference() {

		assertEquals(0, new Duration(MORNING, MORNING).getTotalSeconds());
		assertEquals(0, new Duration(NOON, NOON).getTotalSeconds());
		assertEquals(0, new Duration(AFTERNOON, AFTERNOON).getTotalSeconds());
	}

	@Test
	public void testGetTotalSecondsWithPositiveDifference() {

		assertEquals(4 * 3600 - 2 * 60 + 14, new Duration(MORNING, NOON).getTotalSeconds());
		assertEquals(4 * 3600 + 33 * 60 - 32, new Duration(NOON, AFTERNOON).getTotalSeconds());
		assertEquals(8 * 3600 + 31 * 60 - 18, new Duration(MORNING, AFTERNOON).getTotalSeconds());
	}

	@Test
	public void testGetTotalSecondsWithNegativeDifference() {

		assertEquals(-4 * 3600 + 2 * 60 - 14, new Duration(NOON, MORNING).getTotalSeconds());
		assertEquals(-4 * 3600 - 33 * 60 + 32, new Duration(AFTERNOON, NOON).getTotalSeconds());
		assertEquals(-8 * 3600 - 31 * 60 + 18, new Duration(AFTERNOON, MORNING).getTotalSeconds());
	}

	@Test
	public void testToString() {

		assertEquals("-3d -3h -36min -47s", new Duration(-3 * SECONDS_PER_DAY - 4 * SECONDS_PER_HOUR + 23 * 60 + 13).toString());
		assertEquals("-1 seconds", new Duration(-1).toString());
		assertEquals("0 seconds", new Duration(0).toString());
		assertEquals("1 seconds", new Duration(1).toString());
		assertEquals("2d 20h 23min 13s", new Duration(3 * SECONDS_PER_DAY - 4 * SECONDS_PER_HOUR + 23 * 60 + 13).toString());
	}

	@Test
	public void testEquals() {

		assertTrue(new Duration(0).equals(new Duration(0)));
		assertTrue(new Duration(13).equals(new Duration(13)));

		assertFalse(new Duration(0).equals(new Duration(13)));
		assertFalse(new Duration(13).equals(new Duration(-13)));
	}

	@Test
	public void testGetDays() {

		assertEquals(-3, new Duration(-3 * SECONDS_PER_DAY - 12345).getDays());
		assertEquals(-3, new Duration(-3 * SECONDS_PER_DAY - 1).getDays());
		assertEquals(-3, new Duration(-3 * SECONDS_PER_DAY).getDays());
		assertEquals(-2, new Duration(-3 * SECONDS_PER_DAY + 1).getDays());

		assertEquals(-1, new Duration(-1 * SECONDS_PER_DAY - 1).getDays());
		assertEquals(-1, new Duration(-1 * SECONDS_PER_DAY).getDays());
		assertEquals(-0, new Duration(-1 * SECONDS_PER_DAY + 1).getDays());

		assertEquals(0, new Duration(-1).getDays());
		assertEquals(0, new Duration(+0).getDays());
		assertEquals(0, new Duration(+1).getDays());

		assertEquals(0, new Duration(1 * SECONDS_PER_DAY - 1).getDays());
		assertEquals(1, new Duration(1 * SECONDS_PER_DAY + 0).getDays());
		assertEquals(1, new Duration(1 * SECONDS_PER_DAY + 1).getDays());

		assertEquals(2, new Duration(3 * SECONDS_PER_DAY - 1).getDays());
		assertEquals(3, new Duration(3 * SECONDS_PER_DAY + 0).getDays());
		assertEquals(3, new Duration(3 * SECONDS_PER_DAY + 1).getDays());
		assertEquals(3, new Duration(3 * SECONDS_PER_DAY + 12345).getDays());
	}

	@Test
	public void testGetHoursOfDay() {

		assertEquals(21, new Duration(3 * SECONDS_PER_DAY - 3 * SECONDS_PER_HOUR + 1234).getHoursOfDay());

		assertEquals(-1, new Duration(-1 * SECONDS_PER_HOUR - 1).getHoursOfDay());
		assertEquals(-1, new Duration(-1 * SECONDS_PER_HOUR).getHoursOfDay());
		assertEquals(-0, new Duration(-1 * SECONDS_PER_HOUR + 1).getHoursOfDay());

		assertEquals(0, new Duration(-1).getHoursOfDay());
		assertEquals(0, new Duration(+0).getHoursOfDay());
		assertEquals(0, new Duration(+1).getHoursOfDay());

		assertEquals(0, new Duration(1 * SECONDS_PER_HOUR - 1).getHoursOfDay());
		assertEquals(1, new Duration(1 * SECONDS_PER_HOUR).getHoursOfDay());
		assertEquals(1, new Duration(1 * SECONDS_PER_HOUR + 1).getHoursOfDay());

		assertEquals(3, new Duration(3 * SECONDS_PER_DAY + 3 * SECONDS_PER_HOUR + 1234).getHoursOfDay());
	}

	@Test
	public void testGetMinutesOfHour() {

		assertEquals(-2, new Duration(-120).getMinutesOfHour());
		assertEquals(-1, new Duration(-119).getMinutesOfHour());
		assertEquals(-1, new Duration(-60).getMinutesOfHour());

		assertEquals(0, new Duration(-59).getMinutesOfHour());
		assertEquals(0, new Duration(+0).getMinutesOfHour());
		assertEquals(0, new Duration(59).getMinutesOfHour());

		assertEquals(1, new Duration(60).getMinutesOfHour());
		assertEquals(1, new Duration(119).getMinutesOfHour());
		assertEquals(2, new Duration(120).getMinutesOfHour());
	}

	@Test
	public void testGetSecondsOfMinute() {

		assertEquals(-1, new Duration(-61).getSecondsOfMinute());
		assertEquals(0, new Duration(-60).getSecondsOfMinute());
		assertEquals(-59, new Duration(-59).getSecondsOfMinute());
		assertEquals(-1, new Duration(-1).getSecondsOfMinute());

		assertEquals(0, new Duration(+0).getSecondsOfMinute());

		assertEquals(1, new Duration(1).getSecondsOfMinute());
		assertEquals(59, new Duration(59).getSecondsOfMinute());
		assertEquals(0, new Duration(60).getSecondsOfMinute());
		assertEquals(1, new Duration(61).getSecondsOfMinute());
	}

	@Test
	public void testClamp() {

		assertEquals(0, new Duration(-23).clamp().getTotalSeconds());
		assertEquals(0, new Duration(-1).clamp().getTotalSeconds());
		assertEquals(0, new Duration(0).clamp().getTotalSeconds());
		assertEquals(1, new Duration(1).clamp().getTotalSeconds());
		assertEquals(23, new Duration(23).clamp().getTotalSeconds());
	}
}
