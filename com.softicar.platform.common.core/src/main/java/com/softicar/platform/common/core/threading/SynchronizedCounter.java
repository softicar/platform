package com.softicar.platform.common.core.threading;

/**
 * An {@link Integer} counter that is thread-safe.
 * 
 * @author Oliver Richers
 */
public class SynchronizedCounter {

	private int value = 0;

	/**
	 * Increments this request counter and returns the previous value.
	 * 
	 * @return the counter value before the increment, starting from zero
	 */
	public synchronized int increment() {

		return value++;
	}
}
