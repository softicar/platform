package com.softicar.platform.common.core.thread.sleep;

import com.softicar.platform.common.core.thread.sleeper.CurrentSleeper;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Facade for {@link Thread} sleeping.
 *
 * @author Oliver Richers
 */
public class Sleep {

	/**
	 * Sleeps for the given amount of milliseconds.
	 *
	 * @param millis
	 *            the milliseconds to sleep
	 */
	public static void sleep(long millis) {

		CurrentSleeper.get().sleep(millis);
	}

	/**
	 * Sleeps for the given {@link Duration}.
	 *
	 * @param duration
	 *            the {@link Duration} to sleep (never <i>null</i>)
	 */
	public static void sleep(Duration duration) {

		sleep(Objects.requireNonNull(duration).toMillis());
	}

	/**
	 * Sleeps until the given target {@link Instant}.
	 *
	 * @param target
	 *            the target {@link Instant} (never <i>null</i>)
	 */
	public static void sleepUntil(Instant target) {

		new SleepUntil(target).sleep();
	}
}
