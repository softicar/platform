package com.softicar.platform.common.date;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Tests the class {@link Year}.
 *
 * @author Oliver Richers
 */
public class YearTest extends AbstractTest {

	@Test
	public void getAbsoluteIndex() {

		assertEquals(2006, Year.get(2006).getAbsoluteIndex());
	}

	@Test
	public void firstDayIsFirstJanuary() {

		assertEquals(Day.fromYMD(2006, 1, 1), Year.get(2006).getDays().getFirst());
	}

	@Test
	public void lastDayIsLastDecember() {

		assertEquals(Day.fromYMD(2006, 12, 31), Year.get(2006).getDays().getLast());
	}

	@Test
	public void mondayOfFirstWeekIsCorrect() {

		assertEquals(Day.fromYMD(2003, 12, 29), Year.get(2004).getWeeks().getFirst().getMonday());
		assertEquals(Day.fromYMD(2006, 1, 2), Year.get(2006).getWeeks().getFirst().getMonday());
		assertEquals(Day.fromYMD(2007, 12, 31), Year.get(2008).getWeeks().getFirst().getMonday());
		assertEquals(Day.fromYMD(2011, 1, 3), Year.get(2011).getWeeks().getFirst().getMonday());
	}

	@Test
	public void sundayOfLastWeekIsCorrect() {

		assertEquals(Day.fromYMD(2003, 12, 28), Year.get(2003).getWeeks().getLast().getSunday());
		assertEquals(Day.fromYMD(2006, 1, 1), Year.get(2005).getWeeks().getLast().getSunday());
		assertEquals(Day.fromYMD(2007, 12, 30), Year.get(2007).getWeeks().getLast().getSunday());
		assertEquals(Day.fromYMD(2011, 1, 2), Year.get(2010).getWeeks().getLast().getSunday());
	}
}
