package com.softicar.platform.common.date;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class TimeTest extends AbstractTest {

	private final Time time;

	public TimeTest() {

		this.time = new Time(15, 47, 17);
	}

	@Test
	public void testParseTime() {

		assertEquals(new Time(0, 0, 0), Time.parseTime("0:0:0"));
		assertEquals(new Time(1, 2, 3), Time.parseTime("1:2:3"));
		assertEquals(new Time(1, 2, 3), Time.parseTime("01:02:03"));
		assertEquals(new Time(11, 22, 33), Time.parseTime("11:22:33"));
		assertEquals(new Time(23, 59, 59), Time.parseTime("23:59:59"));
	}

	@Test
	public void testLimits() {

		// test minimum and maximum
		DevNull.swallow(new Time(0, 0, 0));
		DevNull.swallow(new Time(23, 59, 59));

		// test out of limit
		assertExceptionCustom(IllegalArgumentException.class, () -> new Time(-1, 0, 0));
		assertExceptionCustom(IllegalArgumentException.class, () -> new Time(24, 0, 0));
		assertExceptionCustom(IllegalArgumentException.class, () -> new Time(0, -1, 0));
		assertExceptionCustom(IllegalArgumentException.class, () -> new Time(0, 60, 0));
		assertExceptionCustom(IllegalArgumentException.class, () -> new Time(0, -1, 0));
		assertExceptionCustom(IllegalArgumentException.class, () -> new Time(0, 60, 0));
	}

	private void assertExceptionCustom(Class<? extends Exception> expectedExceptionClass, INullaryVoidFunction action) {

		try {
			action.apply();
			fail(String.format("Expected exception of type %s.", expectedExceptionClass.getSimpleName()));
		} catch (Exception exception) {
			if (!expectedExceptionClass.isInstance(exception)) {
				fail(String.format("Expected exception of type %s but got %s.", expectedExceptionClass.getSimpleName(), exception.getClass().getSimpleName()));
			}
		}
	}

	@Test
	public void testGetHour() {

		assertEquals(15, time.getHour());
	}

	@Test
	public void testGetMinute() {

		assertEquals(47, time.getMinute());
	}

	@Test
	public void testGetSecond() {

		assertEquals(17, time.getSecond());
	}

	@Test
	public void testGetTimeInSeconds() {

		assertEquals(15 * 3600 + 47 * 60 + 17, time.getTimeInSeconds());
	}

	@Test
	public void testSecondsConstructor() {

		int timeInSeconds = 23 * 3600 + 13 * 60 + 55;
		Time time = new Time(timeInSeconds);
		assertEquals(23, time.getHour());
		assertEquals(13, time.getMinute());
		assertEquals(55, time.getSecond());
		assertEquals(timeInSeconds, time.getTimeInSeconds());
	}

	@Test
	public void testEquals() {

		assertEquals(new Time(1234), new Time(1234));
		assertNotEquals(new Time(1234), new Time(12345));
	}

	@Test
	public void testHashCode() {

		assertEquals(new Time(1234).hashCode(), new Time(1234).hashCode());
	}

	@Test
	public void testToString() {

		assertEquals("15:47:17", time.toString());
	}

	@Test
	public void testToIsoFormat() {

		assertEquals("15:47:17", time.toIsoFormat());

		assertEquals("00:00:00", new Time(0, 0, 0).toIsoFormat());
		assertEquals("01:02:03", new Time(1, 2, 3).toIsoFormat());
		assertEquals("23:59:59", new Time(23, 59, 59).toIsoFormat());
	}

	@Test
	public void testCompareTo() {

		assertEquals(0, new Time(111).compareTo(new Time(111)));
		assertEquals(1, new Time(222).compareTo(new Time(111)));
		assertEquals(-1, new Time(111).compareTo(new Time(222)));
	}
}
