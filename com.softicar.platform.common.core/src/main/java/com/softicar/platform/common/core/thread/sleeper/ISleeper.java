package com.softicar.platform.common.core.thread.sleeper;

/**
 * Interface for various {@link Thread} sleep implementations.
 *
 * @author Oliver Richers
 */
public interface ISleeper {

	/**
	 * Suspends the current execution for the given amount of milliseconds.
	 *
	 * @param millis
	 *            the number of milliseconds to sleep (must not be negative)
	 */
	void sleep(long millis);
}
