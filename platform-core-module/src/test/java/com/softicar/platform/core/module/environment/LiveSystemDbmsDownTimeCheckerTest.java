package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.date.weekday.AGWeekday;
import com.softicar.platform.core.module.test.AbstractSofticarDbTest;
import org.junit.Test;

public class LiveSystemDbmsDownTimeCheckerTest extends AbstractSofticarDbTest {

	private static final Day DOWN_TIME_DAY = Day.fromYMD(2019, 12, 15);
	private static final Day NON_DOWN_TIME_DAY = DOWN_TIME_DAY.getNext();
	private static final Time DOWN_TIME_BEGIN = new Time(2, 5, 0);
	private static final Time DOWN_TIME_END = new Time(2, 15, 0);
	private final AGLiveSystemConfiguration configuration;

	public LiveSystemDbmsDownTimeCheckerTest() {

		this.configuration = AGLiveSystemConfiguration.TABLE//
			.getOrCreate(AGCoreModuleInstance.getInstance())
			.setDbmsDownTimeWeekday(AGWeekday.getByWeekday(DOWN_TIME_DAY.getWeekday()))
			.setDbmsDownTimeBegin(DOWN_TIME_BEGIN)
			.setDbmsDownTimeEnd(DOWN_TIME_END)
			.save();
	}

	@Test
	public void testIsDownTimeWithMatchingDay() {

		assertIsNotDownTime(DOWN_TIME_DAY, 0, 0, 0);
		assertIsNotDownTime(DOWN_TIME_DAY, 2, 4, 59);
		assertIsDownTime(DOWN_TIME_DAY, 2, 5, 0);
		assertIsDownTime(DOWN_TIME_DAY, 2, 10, 0);
		assertIsDownTime(DOWN_TIME_DAY, 2, 15, 0);
		assertIsNotDownTime(DOWN_TIME_DAY, 2, 15, 1);
		assertIsNotDownTime(DOWN_TIME_DAY, 23, 59, 59);
	}

	@Test
	public void testIsDownTimeWithNonMatchingDay() {

		assertIsNotDownTime(NON_DOWN_TIME_DAY, 0, 0, 0);
		assertIsNotDownTime(NON_DOWN_TIME_DAY, 2, 4, 59);
		assertIsNotDownTime(NON_DOWN_TIME_DAY, 2, 5, 0);
		assertIsNotDownTime(NON_DOWN_TIME_DAY, 2, 10, 0);
		assertIsNotDownTime(NON_DOWN_TIME_DAY, 2, 15, 0);
		assertIsNotDownTime(NON_DOWN_TIME_DAY, 2, 15, 1);
		assertIsNotDownTime(NON_DOWN_TIME_DAY, 23, 59, 59);
	}

	// ------------------------------ private ------------------------------ //

	private void assertIsDownTime(Day day, int hours, int minutes, int seconds) {

		assertTrue(new LiveSystemDbmsDownTimeChecker(configuration, toDayTime(day, hours, minutes, seconds)).isDownTime());
	}

	private void assertIsNotDownTime(Day day, int hours, int minutes, int seconds) {

		assertFalse(new LiveSystemDbmsDownTimeChecker(configuration, toDayTime(day, hours, minutes, seconds)).isDownTime());
	}

	private DayTime toDayTime(Day day, int hours, int minutes, int seconds) {

		return new DayTime(day, new Time(hours, minutes, seconds));
	}
}
