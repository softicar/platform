package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.thread.sleeper.TestSleeper;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.AbstractTest;
import java.time.Instant;
import org.junit.Test;

public class ScheduledProgramEnqueuerDaemonTimerTest extends AbstractTest {

	private static final int SECONDS = 13;
	private static final DayTime CURRENT_DAYTIME = DayTime.fromYMD_HMS(2020, 1, 1, 20, 20, SECONDS);
	private final TestSleeper testSleeper;

	public ScheduledProgramEnqueuerDaemonTimerTest() {

		this.testSleeper = setupTestSleeper();
	}

	@Test
	public void testGetCurrentMinute() {

		setClock(CURRENT_DAYTIME);

		DayTime currentMinute = new ScheduledProgramEnqueuerDaemonTimer().getCurrentMinute();

		assertEquals(0, currentMinute.getSecond());
	}

	@Test
	public void testSleepUntilNextMinute() {

		setClock(CURRENT_DAYTIME);

		ScheduledProgramEnqueuerDaemonTimer timer = new ScheduledProgramEnqueuerDaemonTimer();

		DayTime initalMinute = timer.getCurrentMinute();
		timer.sleepUntilNextMinute();
		DayTime currentMinute = timer.getCurrentMinute();

		assertTrue(currentMinute.isAfter(initalMinute));
		testSleeper.assertOneCall((60 - SECONDS) * 1000);
	}

	private void setClock(DayTime dayTime) {

		testClock.setInstant(Instant.ofEpochMilli(dayTime.toMillis()));
	}
}
