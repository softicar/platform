package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.date.DayTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Handles the timing of the {@link ScheduledProgramEnqueuerDaemon}.
 *
 * @author Oliver Richers
 */
class ScheduledProgramEnqueuerDaemonTimer {

	private Instant currentMinute;

	public ScheduledProgramEnqueuerDaemonTimer() {

		this.currentMinute = currentMinute();
	}

	public DayTime getCurrentMinute() {

		return DayTime.fromInstant(currentMinute);
	}

	public void sleepUntilNextMinute() {

		Sleep.sleepUntil(nextMinute());
		this.currentMinute = currentMinute();
	}

	private Instant currentMinute() {

		return CurrentClock.get().instant().truncatedTo(ChronoUnit.MINUTES);
	}

	private Instant nextMinute() {

		return currentMinute.plus(1, ChronoUnit.MINUTES);
	}
}
