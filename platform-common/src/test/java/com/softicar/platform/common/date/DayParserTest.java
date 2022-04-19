package com.softicar.platform.common.date;

import com.softicar.platform.common.testing.AbstractTest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.Test;

public class DayParserTest extends AbstractTest {

	private final Collection<String> formats;
	private final Collection<String> yearlessFormats;

	public DayParserTest() {

		this.formats = new ArrayList<>();
		this.yearlessFormats = new ArrayList<>();

		formats.add("d.M.yy");
		formats.add("M/d/yy");
		formats.add("yy-M-d");

		formats.add("dd.MM.yyyy");
		formats.add("MM/dd/yyyy");
		formats.add("yyyy-MM-dd");

		yearlessFormats.add("d.M");
		yearlessFormats.add("d.M.");
		yearlessFormats.add("M/d");
		yearlessFormats.add("M/d/");

		yearlessFormats.add("dd.MM");
		yearlessFormats.add("dd.MM.");
		yearlessFormats.add("MM/dd");
		yearlessFormats.add("MM/dd/");
	}

	@Test
	public void testWithNormalFormats() {

		test(formats, Day.fromYMD(2019, 1, 1));
		test(formats, Day.fromYMD(2020, 7, 12));
		test(formats, Day.fromYMD(2021, 12, 31));
	}

	@Test
	public void testWithYearlessFormats() {

		int thisYear = Day.today().getYear().getAbsoluteIndex();
		test(yearlessFormats, Day.fromYMD(thisYear, 1, 1));
		test(yearlessFormats, Day.fromYMD(thisYear, 7, 12));
		test(yearlessFormats, Day.fromYMD(thisYear, 12, 31));
	}

	@Test
	public void testWithIllegalFormats() {

		assertFalse(new DayParser("xxx").parse().isPresent());
		assertFalse(new DayParser("x.y.z").parse().isPresent());
		assertFalse(new DayParser("1.2.3.4").parse().isPresent());
		assertFalse(new DayParser("/1/3/").parse().isPresent());
	}

	private static void test(Collection<String> formats, Day day) {

		for (String format: formats) {
			String text = new SimpleDateFormat(format).format(day.toDate());

			Optional<Day> parsedDay = new DayParser(text).parse();

			assertTrue("failed to parse " + text, parsedDay.isPresent());
			assertEquals("failed to parse " + text, day, parsedDay.get());
		}
	}
}
