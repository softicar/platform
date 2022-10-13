package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class TimeParserTest extends AbstractTest {

	@Test
	public void testWithHoursOnly() {

		assertParsedTime("10:00:00", "10");
		assertParsedTime("05:00:00", "5");
		assertParsedTime("20:00:00", "20");
	}

	@Test
	public void testWithHoursAndMinutes() {

		assertParsedTime("10:30:00", "10:30");
		assertParsedTime("05:25:00", "5:25");
		assertParsedTime("20:05:00", "20:5");
	}

	@Test
	public void testWithHoursMinutesAndSeconds() {

		assertParsedTime("10:30:15", "10:30:15");
		assertParsedTime("05:25:05", "5:25:5");
		assertParsedTime("20:05:20", "20:5:20");
	}

	@Test
	public void testWithInvalidTimes() {

		assertExceptionForTime("::");
		assertExceptionForTime("x:y:z");
		assertExceptionForTime("-1:0:0");
		assertExceptionForTime("24:0:0");
		assertExceptionForTime("0:60:0");
		assertExceptionForTime("0:0:61");
		assertExceptionForTime("0:60:60");
		assertExceptionForTime("0:");
		assertExceptionForTime("0:0:");
		assertExceptionForTime("0:0:0:");
		assertExceptionForTime("000");
		assertExceptionForTime("0:000");
		assertExceptionForTime("0:0:000");
	}

	private void assertParsedTime(String expected, String time) {

		assertEquals(expected, new TimeParser(time).parseOrThrow().get().toString());
	}

	private void assertExceptionForTime(String time) {

		try {
			new TimeParser(time).parseOrThrow();
		} catch (Exception exception) {
			//TODO
			assertEquals(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1.toDisplay(time).toString(), exception.getMessage());
		}
	}
}
