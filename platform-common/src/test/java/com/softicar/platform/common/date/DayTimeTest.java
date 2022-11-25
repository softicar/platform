package com.softicar.platform.common.date;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.testing.AbstractTest;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.Test;

/**
 * Tests the class {@link DayTime}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DayTimeTest extends AbstractTest {

	private static final Day SOME_DAY = Day.fromYMD(2014, 1, 1);

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void testNow() {

		long start = currentTimeMillisWithSecondsAccuracy();
		DayTime now = DayTime.now();
		long end = currentTimeMillisWithSecondsAccuracy();

		assertTrue(now.toMillis() >= start);
		assertTrue(now.toMillis() <= end);
	}

	@Test
	public void testNowWithTestClock() {

		final DayTime expectedDayTime = DayTime.fromYMD_HMS(2017, 7, 14, 4, 40, 0);
		final long millis = 1500000000000L;

		CurrentClock.set(new TestClock(ZoneOffset.UTC, Instant.ofEpochMilli(millis)));

		assertEquals(expectedDayTime, DayTime.now());
	}

	@Test
	public void testMaxWithCollection() {

		DayTime max = DayTime
			.max(Arrays.asList(DayTime.fromDayAndSeconds(SOME_DAY, 10), DayTime.fromDayAndSeconds(SOME_DAY, 30), DayTime.fromDayAndSeconds(SOME_DAY, 20)));

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 30), max);
	}

	@Test
	public void testMaxWithCollectionAndNullArgument() {

		DayTime max = DayTime.max(Arrays.asList(DayTime.fromDayAndSeconds(SOME_DAY, 10), DayTime.fromDayAndSeconds(SOME_DAY, 30), null));

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 30), max);
	}

	@Test
	public void testMaxWithCollectionAndOnlyNullArguments() {

		DayTime max = DayTime.max(Arrays.<DayTime> asList(null, null));

		assertNull(max);
	}

	@Test
	public void testMaxWithEmptyCollection() {

		DayTime max = DayTime.max(new ArrayList<>());

		assertNull(max);
	}

	@Test
	public void testMaxWithVarargs() {

		DayTime max = DayTime.max(DayTime.fromDayAndSeconds(SOME_DAY, 10), DayTime.fromDayAndSeconds(SOME_DAY, 30), DayTime.fromDayAndSeconds(SOME_DAY, 20));

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 30), max);
	}

	@Test
	public void testMaxWithVarargsAndNullArgument() {

		DayTime max = DayTime.max(DayTime.fromDayAndSeconds(SOME_DAY, 10), DayTime.fromDayAndSeconds(SOME_DAY, 30), null);

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 30), max);
	}

	@Test
	public void testMaxWithVarargsAndOnlyNullArguments() {

		DayTime max = DayTime.max(null, null);

		assertNull(max);
	}

	@Test
	public void testMaxWithEmptyVarargs() {

		DayTime max = DayTime.max();

		assertNull(max);
	}

	@Test
	public void testMinWithCollection() {

		DayTime min = DayTime
			.min(Arrays.asList(DayTime.fromDayAndSeconds(SOME_DAY, 30), DayTime.fromDayAndSeconds(SOME_DAY, 10), DayTime.fromDayAndSeconds(SOME_DAY, 20)));

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 10), min);
	}

	@Test
	public void testMinWithCollectionAndNullArgument() {

		DayTime min = DayTime.min(Arrays.asList(DayTime.fromDayAndSeconds(SOME_DAY, 30), DayTime.fromDayAndSeconds(SOME_DAY, 10), null));

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 10), min);
	}

	@Test
	public void testMinWithCollectionAndOnlyNullArguments() {

		DayTime min = DayTime.min(Arrays.<DayTime> asList(null, null));

		assertNull(min);
	}

	@Test
	public void testMinWithEmptyCollection() {

		DayTime min = DayTime.min(new ArrayList<>());

		assertNull(min);
	}

	@Test
	public void testMinWithVarargs() {

		DayTime min = DayTime.min(DayTime.fromDayAndSeconds(SOME_DAY, 30), DayTime.fromDayAndSeconds(SOME_DAY, 10), DayTime.fromDayAndSeconds(SOME_DAY, 20));

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 10), min);
	}

	@Test
	public void testMinWithVarargsAndNullArgument() {

		DayTime min = DayTime.min(DayTime.fromDayAndSeconds(SOME_DAY, 30), DayTime.fromDayAndSeconds(SOME_DAY, 10), null);

		assertEquals(DayTime.fromDayAndSeconds(SOME_DAY, 10), min);
	}

	@Test
	public void testMinWithVarargsAndOnlyNullArguments() {

		DayTime min = DayTime.min(null, null);

		assertNull(min);
	}

	@Test
	public void testMinWithEmptyVarargs() {

		DayTime min = DayTime.min();

		assertNull(min);
	}

	@Test
	public void testIsBetween() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 20);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 10);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 30);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenAtLowerBoundary() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 10);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 10);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 30);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenBelowLowerBoundary() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 5);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 10);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 30);

		assertFalse(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenAtUpperBoundary() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 30);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 10);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 30);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenAboveUpperBoundary() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 31);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 10);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 30);

		assertFalse(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenWithNegativePeriod() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 20);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 30);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 10);

		assertFalse(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenWithTrivialPeriod() {

		DayTime candidate = DayTime.fromDayAndSeconds(SOME_DAY, 20);
		DayTime first = DayTime.fromDayAndSeconds(SOME_DAY, 20);
		DayTime last = DayTime.fromDayAndSeconds(SOME_DAY, 20);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void toStringReturnsCorrectValue() {

		DayTime dayTime = DayTime.fromYMD_HMS(2000, 2, 6, 1, 13, 22);

		assertEquals(dayTime.toString(), "2000-02-06 01:13:22");
	}

	@Test
	public void plusZeroSecondsDoesNothing() {

		DayTime dayTime = DayTime.fromYMD_HMS(2000, 1, 1, 1, 0, 0);

		assertSame(dayTime, dayTime.plusSeconds(0));
		assertSame(dayTime, dayTime.plusSeconds(0.0));
	}

	@Test
	public void minusZeroSecondsDoesNothing() {

		DayTime dayTime = DayTime.fromYMD_HMS(2000, 1, 1, 1, 0, 0);

		assertSame(dayTime, dayTime.minusSeconds(0));
		assertSame(dayTime, dayTime.minusSeconds(0.0));
	}

	@Test
	public void plusSecondsAddsCorrectly() {

		DayTime dayTime = DayTime.fromYMD_HMS(2000, 1, 1, 1, 0, 0);

		assertEquals("2000-01-01 01:00:01", dayTime.plusSeconds(1).toString());
		assertEquals("2000-01-01 01:00:59", dayTime.plusSeconds(59).toString());
		assertEquals("2000-01-01 01:01:00", dayTime.plusSeconds(60).toString());
		assertEquals("2000-01-01 01:01:01", dayTime.plusSeconds(61).toString());
		assertEquals("2000-01-01 02:00:00", dayTime.plusSeconds(60 * 60).toString());
	}

	@Test
	public void minusSecondsSubtractsCorrectly() {

		DayTime dayTime = DayTime.fromYMD_HMS(2000, 1, 1, 1, 0, 0);

		assertEquals("2000-01-01 00:59:59", dayTime.minusSeconds(1).toString());
		assertEquals("2000-01-01 00:59:01", dayTime.minusSeconds(59).toString());
		assertEquals("2000-01-01 00:59:00", dayTime.minusSeconds(60).toString());
		assertEquals("2000-01-01 00:58:59", dayTime.minusSeconds(61).toString());
		assertEquals("2000-01-01 00:00:00", dayTime.minusSeconds(60 * 60).toString());
		assertEquals("1999-12-30 01:00:00", dayTime.minusSeconds(2 * 24 * 3600).toString());
		assertEquals("1999-12-29 20:37:22", dayTime.minusSeconds(2 * 24 * 3600 + 4 * 3600 + 22 * 60 + 38).toString());
	}

	@Test
	public void fromDayAndSeconds() {

		assertEquals(0, DayTime.fromDayAndSeconds(SOME_DAY, 0).getSecondsOfDay());
		assertEquals(1, DayTime.fromDayAndSeconds(SOME_DAY, 1).getSecondsOfDay());
		assertEquals(86398, DayTime.fromDayAndSeconds(SOME_DAY, 86398).getSecondsOfDay());
		assertEquals(86399, DayTime.fromDayAndSeconds(SOME_DAY, 86399).getSecondsOfDay());
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromDayAndSecondsThrowsOnNegativeSeconds() {

		DayTime.fromDayAndSeconds(SOME_DAY, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromDayAndSecondsThrowsOnSecondsOverflow() {

		DayTime.fromDayAndSeconds(SOME_DAY, 86400);
	}

	@Test
	public void getTimeAsStringYYYYMMDD_HHMM() {

		assertEquals("20221125_1010", DayTime.fromYMD_HMS(2022, 11, 25, 10, 10, 25).getTimeAsStringYYYYMMDD_HHMM());
		assertEquals("20220102_0304", DayTime.fromYMD_HMS(2022, 1, 2, 3, 4, 5).getTimeAsStringYYYYMMDD_HHMM());
	}

	@Test
	public void testEquals() {

		assertEquals(//
			new DayTime(Day.fromYMD(2015, 9, 28), new Time(123)),
			new DayTime(Day.fromYMD(2015, 9, 28), new Time(123)));

		assertNotEquals(//
			new DayTime(Day.fromYMD(2015, 9, 28), new Time(123)),
			new DayTime(Day.fromYMD(2015, 9, 29), new Time(123)));

		assertNotEquals(//
			new DayTime(Day.fromYMD(2015, 9, 28), new Time(123)),
			new DayTime(Day.fromYMD(2015, 9, 28), new Time(124)));
	}

	@Test
	public void testHashCode() {

		Day day = Day.fromYMD(2015, 9, 28);
		assertEquals(new DayTime(day, new Time(123)).hashCode(), new DayTime(day, new Time(123)).hashCode());
	}

	private long currentTimeMillisWithSecondsAccuracy() {

		long millis = System.currentTimeMillis();
		return millis - millis % 1000;
	}
}
