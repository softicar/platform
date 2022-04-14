package com.softicar.platform.common.date;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Tests the class {@link Period}.
 *
 * @author Oliver Richers
 */
public class PeriodTest extends AbstractTest {

	@Test
	public void equalPeriodsContainEachOther() {

		Period a = new Period(new DayTime(Day.today()), new DayTime(Day.today(), 23, 59, 59));
		Period b = new Period(new DayTime(Day.today()), new DayTime(Day.today(), 23, 59, 59));

		assertTrue(a.contains(b));
		assertTrue(b.contains(a));
	}
}
