package com.softicar.platform.core.module.cron;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CronExpressionTest extends AbstractTest {

	@Test
	public void testMatches() {

		CronExpression cron = CronParser.parse("0 6-18/2 1,15 JAN,JUL *");

		assertTrue(cron.matches(DayTime.fromYMD_HMS(2021, 7, 1, 18, 0, 23)));
		assertTrue(cron.matches(DayTime.fromYMD_HMS(2021, 1, 15, 6, 0, 0)));
		assertTrue(cron.matches(DayTime.fromYMD_HMS(2021, 1, 15, 8, 0, 45)));

		assertFalse(cron.matches(DayTime.fromYMD_HMS(2021, 1, 1, 5, 59, 59)));
		assertFalse(cron.matches(DayTime.fromYMD_HMS(2021, 1, 1, 7, 30, 20)));
		assertFalse(cron.matches(DayTime.fromYMD_HMS(2021, 2, 15, 7, 0, 45)));
	}

	@Test
	public void testMatchesRange() {

		CronExpression cron = CronParser.parse("* 23-1 * DEC-FEB *");

		assertTrue(cron.matches(DayTime.fromYMD_HMS(2021, 12, 1, 0, 0, 1)));
		assertTrue(cron.matches(DayTime.fromYMD_HMS(2021, 1, 15, 23, 2, 0)));
		assertTrue(cron.matches(DayTime.fromYMD_HMS(2021, 2, 15, 1, 50, 45)));

		assertFalse(cron.matches(DayTime.fromYMD_HMS(2021, 3, 1, 1, 59, 59)));
		assertFalse(cron.matches(DayTime.fromYMD_HMS(2021, 11, 1, 0, 30, 20)));
		assertFalse(cron.matches(DayTime.fromYMD_HMS(2021, 2, 15, 7, 0, 45)));
	}

	@Test
	public void testSunday() {

		CronExpression sunday0 = CronParser.parse("* * * * 0");
		CronExpression sunday7 = CronParser.parse("* * * * 7");

		DayTime someSunday = DayTime.fromYMD_HMS(2021, 1, 3, 0, 0, 0);
		assertTrue(sunday0.matches(someSunday));
		assertTrue(sunday7.matches(someSunday));

		DayTime someMonday = DayTime.fromYMD_HMS(2021, 1, 4, 0, 0, 0);
		assertFalse(sunday0.matches(someMonday));
		assertFalse(sunday7.matches(someMonday));
	}
}
