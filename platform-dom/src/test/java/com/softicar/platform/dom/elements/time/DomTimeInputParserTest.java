package com.softicar.platform.dom.elements.time;

import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;

public class DomTimeInputParserTest extends AbstractTest {

	@Test
	public void testParseOrThrowWithHoursAndMinutesAndSeconds() {

		assertParsedTime("23:59:59", "23:59:59");
		assertParsedTime("11:22:33", "11:22:33");
		assertParsedTime("01:02:03", "001:002:003");
		assertParsedTime("01:02:03", "01:02:03");
		assertParsedTime("01:02:03", "1:2:3");
		assertParsedTime("00:00:00", "0:0:0");
	}

	@Test
	public void testParseOrThrowWithHoursAndMinutes() {

		assertParsedTime("23:59:00", "23:59");
		assertParsedTime("11:22:00", "11:22");
		assertParsedTime("01:02:00", "001:002");
		assertParsedTime("01:02:00", "01:02");
		assertParsedTime("01:02:00", "1:2");
		assertParsedTime("00:00:00", "0:0");
	}

	@Test
	public void testParseOrThrowWithHoursOnly() {

		assertParsedTime("23:00:00", "23");
		assertParsedTime("11:00:00", "11");
		assertParsedTime("01:00:00", "001");
		assertParsedTime("01:00:00", "01");
		assertParsedTime("01:00:00", "1");
		assertParsedTime("00:00:00", "0");
	}

	@Test
	public void testParseOrThrowWithEmptyInput() {

		assertTrue(parse("").isEmpty());
	}

	@Test
	public void testParseOrThrowWithInvalidInput() {

		// blank, non-empty input
		assertException(" ");

		// invalid separation
		assertException(":");
		assertException("::");
		assertException(":0:0");
		assertException("0::0");
		assertException("0:0:");
		assertException("0:");
		assertException("0:0:0:");

		// spaces
		assertException(" 22:33:44");
		assertException("22:33:44 ");
		assertException("22 : 33 : 44");

		// too many tokens
		assertException("22:33:44:55");

		// letters
		assertException("x:y:z");

		// negative values
		assertException("-22:33:44");
		assertException("22:-33:44");
		assertException("22:33:-44");
		assertException("-00:00:00");
		assertException("00:-00:00");
		assertException("00:00:-00");

		// overflow (hours)
		assertException("24:00:00");
		assertException("24:00:01");
		assertException("25:00:00");

		// overflow (minutes)
		assertException("00:60:00");
		assertException("00:60:01");
		assertException("00:61:00");

		// overflow (seconds)
		assertException("00:00:60");
		assertException("00:00:61");
	}

	private Optional<Time> parse(String input) {

		return new DomTimeInputParser(input).parseOrThrow();
	}

	private void assertParsedTime(String expected, String input) {

		assertEquals(expected, parse(input).get().toString());
	}

	private void assertException(String input) {

		assertExceptionMessage(CommonDateI18n.ILLEGAL_TIME_SPECIFICATION_ARG1.toDisplay(input), () -> parse(input));
	}
}
