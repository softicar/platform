package com.softicar.platform.common.core.thread.sleep;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.thread.sleeper.CurrentSleeper;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Utility class to sleep until a given {@link Instant}.
 *
 * @author Oliver Richers
 */
public class SleepUntil {

	private static final Duration MINIMUM_DURATION_TO_SLEEP = Duration.ofMillis(1);
	private final Instant target;

	public SleepUntil(Instant target) {

		this.target = Objects.requireNonNull(target);
	}

	/**
	 * Sleeps until the {@link CurrentClock} has reached the given target
	 * {@link Instant} or later.
	 */
	public void sleep() {

		while (now().isBefore(target)) {
			CurrentSleeper.get().sleep(getTimeToSleep().toMillis());
		}
	}

	private Duration getTimeToSleep() {

		return max(Duration.between(now(), target), MINIMUM_DURATION_TO_SLEEP);
	}

	private static Instant now() {

		return CurrentClock.get().instant();
	}

	private static Duration max(Duration left, Duration right) {

		return left.compareTo(right) >= 0? left : right;
	}
}
