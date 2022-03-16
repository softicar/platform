package com.softicar.platform.common.core.thread.sleeper;

import com.softicar.platform.common.core.threading.InterruptedRuntimeException;

/**
 * Default implementation of {@link ISleeper}, calling {@link Thread#sleep}.
 *
 * @author Oliver Richers
 */
public class DefaultSleeper implements ISleeper {

	@Override
	public void sleep(long millis) {

		try {
			Thread.sleep(millis);
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}
}
