package com.softicar.platform.common.core.clock;

import com.softicar.platform.common.core.singleton.Singleton;
import java.time.Clock;
import java.util.Objects;

/**
 * Provides a {@link ThreadLocal} to define the default {@link Clock} to use.
 *
 * @author Oliver Richers
 */
public class CurrentClock {

	private static final Singleton<Clock> CURRENT_CLOCK = new Singleton<>(Clock::systemDefaultZone);

	/**
	 * Returns the default {@link Clock} for the current {@link Thread}.
	 * <p>
	 * If no clock has been defined explicitly,
	 * {@link Clock#systemDefaultZone()} will be returned.
	 *
	 * @return the default clock (never null)
	 */
	public static Clock get() {

		return CURRENT_CLOCK.get();
	}

	/**
	 * Defines the {@link Clock} to be returned by {@link #get()}.
	 *
	 * @param clock
	 *            the clock to use (never null)
	 */
	public static void set(Clock clock) {

		CURRENT_CLOCK.set(Objects.requireNonNull(clock));
	}

	/**
	 * Resets this to the default {@link Clock}.
	 */
	public static void reset() {

		CURRENT_CLOCK.set(null);
	}
}
