package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;

public class DomDayTimeInputParserTest extends AbstractTest {

	public DomDayTimeInputParserTest() {

		// Explicitly set the current year to test date format shorthands.
		var clock = new TestClock();
		clock.setInstant(DayTime.fromYMD_HMS(1999, 1, 1, 0, 0, 0).toInstant());
		CurrentClock.set(clock);
	}

	@Test
	public void testParseOrThrowWithHoursAndMinutesAndSeconds() {

		assertParsedTime("2022-03-04 23:59:59", "2022-03-04 23:59:59");
		assertParsedTime("2022-03-04 11:22:33", "2022-03-04 11:22:33");
		assertParsedTime("2022-03-04 01:02:03", "2022-03-04 001:002:003");
		assertParsedTime("2022-03-04 01:02:03", "2022-03-04 01:02:03");
		assertParsedTime("2022-03-04 01:02:03", "2022-03-04 1:2:3");
		assertParsedTime("2022-03-04 01:02:03", "2022-03-04  1:2:3");
		assertParsedTime("2022-03-04 00:00:00", "2022-03-04 0:0:0");
	}

	@Test
	public void testParseOrThrowWithHoursAndMinutes() {

		assertParsedTime("2022-03-04 23:59:00", "2022-03-04 23:59");
		assertParsedTime("2022-03-04 11:22:00", "2022-03-04 11:22");
		assertParsedTime("2022-03-04 01:02:00", "2022-03-04 001:002");
		assertParsedTime("2022-03-04 01:02:00", "2022-03-04 01:02");
		assertParsedTime("2022-03-04 01:02:00", "2022-03-04 1:2");
		assertParsedTime("2022-03-04 01:02:00", "2022-03-04  1:2");
		assertParsedTime("2022-03-04 00:00:00", "2022-03-04 0:0");
	}

	@Test
	public void testParseOrThrowWithHoursOnly() {

		assertParsedTime("2022-03-04 23:00:00", "2022-03-04 23");
		assertParsedTime("2022-03-04 11:00:00", "2022-03-04 11");
		assertParsedTime("2022-03-04 01:00:00", "2022-03-04 001");
		assertParsedTime("2022-03-04 01:00:00", "2022-03-04 01");
		assertParsedTime("2022-03-04 01:00:00", "2022-03-04 1");
		assertParsedTime("2022-03-04 01:00:00", "2022-03-04  1");
		assertParsedTime("2022-03-04 00:00:00", "2022-03-04 0");
	}

	@Test
	public void testParseOrThrowWithVariousDateFormats() {

		// ISO date
		assertParsedTime("2022-03-04 23:59:59", "2022-03-04 23:59:59");

		// ISO date shorthands
		assertParsedTime("2022-03-04 23:59:59", "2022-3-4 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "22-3-4 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "22-3-4 23:59:59");

		// ISO date prolonged
		assertParsedTime("2022-03-04 23:59:59", "02022-003-004 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "022-03-04 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "0022-03-04 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "00022-03-04 23:59:59");

		// German date shorthands
		assertParsedTime("2022-03-04 23:59:59", "4.3.2022 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "4.3.22 23:59:59");
		assertParsedTime("1999-03-04 23:59:59", "4.3. 23:59:59");

		// German date prolonged
		assertParsedTime("2022-03-04 23:59:59", "004.003.02022 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "04.03.022 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "04.03.0022 23:59:59");
		assertParsedTime("2022-03-04 23:59:59", "04.03.00022 23:59:59");
		assertParsedTime("1999-03-04 23:59:59", "004.003. 23:59:59");
	}

	@Test
	public void testParseOrThrowWithEmptyInput() {

		assertTrue(parse("").isEmpty());
	}

	@Test
	public void testParseOrThrowWithInvalidInput() {

		// blank, non-empty input
		assertException(" ");
	}

	@Test
	public void testParseOrThrowWithMissingTimeInput() {

		assertParsedTime("2022-03-04 00:00:00", "2022-03-04");
	}

	@Test
	public void testParseOrThrowWithInvalidDateInput() {

		// missing date
		assertException("23:59:59");

		// invalid separation
		assertException("- 23:59:59");
		assertException("-03-04 23:59:59");
		assertException("--03-04 23:59:59");
		assertException("2022--04 23:59:59");
		assertException("2022-03- 23:59:59");
		assertException("2022- 23:59:59");
		assertException("2022-03-04- 23:59:59");
		assertException("-2022-03-04 23:59:59");

		// invalid spaces
		assertException(" 2022-03-04 23:59:59");
		assertException("2022 - 03 - 04 23:59:59");
		assertException("2022 03 04 23:59:59");

		// too many tokens
		assertException("2022-03-04-05 23:59:59");

		// too few tokens
		assertException("2022-03 23:59:59");
		assertException("2022 23:59:59");

		// letters
		assertException("x-y-z 23:59:59");

		// overflow (months)
		assertException("2022-13-04 23:59:59");

		// underflow (months)
		assertException("2022-00-04 23:59:59");

		// overflow (days)
		assertException("2019-02-29 23:59:59");
		assertException("2020-02-30 23:59:59");
		assertException("2022-03-32 23:59:59");
		assertException("2022-04-31 23:59:59");

		// underflow (days)
		assertException("2022-03-00 23:59:59");
	}

	@Test
	public void testParseOrThrowWithInvalidTimeInput() {

		// invalid separation
		assertException("2022-03-04 :");
		assertException("2022-03-04 ::");
		assertException("2022-03-04 :0:0");
		assertException("2022-03-04 0::0");
		assertException("2022-03-04 0:0:");
		assertException("2022-03-04 0:");
		assertException("2022-03-04 0:0:0:");

		// invalid spaces
		assertException("2022-03-04 22:33:44 ");
		assertException("2022-03-04 22 : 33 : 44");
		assertException("2022-03-04 22 33 44");

		// too many tokens
		assertException("2022-03-04 22:33:44:55");

		// letters
		assertException("2022-03-04 x:y:z");

		// negative values
		assertException("2022-03-04 -22:33:44");
		assertException("2022-03-04 22:-33:44");
		assertException("2022-03-04 22:33:-44");
		assertException("2022-03-04 -00:00:00");
		assertException("2022-03-04 00:-00:00");
		assertException("2022-03-04 00:00:-00");

		// overflow (hours)
		assertException("2022-03-04 24:00:00");
		assertException("2022-03-04 24:00:01");
		assertException("2022-03-04 25:00:00");

		// overflow (minutes)
		assertException("2022-03-04 00:60:00");
		assertException("2022-03-04 00:60:01");
		assertException("2022-03-04 00:61:00");

		// overflow (seconds)
		assertException("2022-03-04 00:00:60");
		assertException("2022-03-04 00:00:61");
	}

	private Optional<DayTime> parse(String input) {

		return new DomDayTimeInputParser(input).parseOrThrow();
	}

	private void assertParsedTime(String expected, String input) {

		assertEquals(expected, parse(input).get().toString());
	}

	private void assertException(String input) {

		assertExceptionMessage(CommonDateI18n.ILLEGAL_DATE_WITH_TIME_ARG1.toDisplay(input), () -> parse(input));
	}
}
