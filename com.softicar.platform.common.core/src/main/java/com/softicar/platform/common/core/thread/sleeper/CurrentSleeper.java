package com.softicar.platform.common.core.thread.sleeper;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import java.util.Objects;

/**
 * Provides a {@link ThreadLocal} to define the {@link ISleeper} to use.
 * <p>
 * This class can be used in unit tests to control the behavior of
 * {@link Sleep#sleep}.
 *
 * @author Oliver Richers
 */
public class CurrentSleeper {

	private static final Singleton<ISleeper> CURRENT_SLEEPER = new Singleton<>(DefaultSleeper::new);

	/**
	 * Returns the default {@link ISleeper} for the current {@link Thread}.
	 * <p>
	 * If no {@link ISleeper} has been defined explicitly, an instance of
	 * {@link DefaultSleeper} will be returned.
	 *
	 * @return the current {@link ISleeper} (never <i>null</i>)
	 */
	public static ISleeper get() {

		return CURRENT_SLEEPER.get();
	}

	/**
	 * Defines the {@link ISleeper} to be returned by {@link #get()}.
	 *
	 * @param sleeper
	 *            the {@link ISleeper} to use (never <i>null</i>)
	 */
	public static void set(ISleeper sleeper) {

		CURRENT_SLEEPER.set(Objects.requireNonNull(sleeper));
	}

	/**
	 * Resets this to the {@link DefaultSleeper}.
	 */
	public static void reset() {

		CURRENT_SLEEPER.set(null);
	}
}
