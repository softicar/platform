package com.softicar.platform.common.testing;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.core.thread.sleeper.CurrentSleeper;
import com.softicar.platform.common.core.thread.sleeper.TestSleeper;
import org.junit.Rule;

/**
 * Recommended base class for all unit tests.
 * <p>
 * This class employs the {@link SingletonTestWatcher} as Junit {@link Rule}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTest extends Asserts {

	@Rule public SingletonTestWatcher SingletonTestWatcher = new SingletonTestWatcher();

	protected final TestClock testClock;

	public AbstractTest() {

		this.testClock = new TestClock();
		CurrentClock.set(testClock);
	}

	/**
	 * Creates a new {@link TestSleeper} and sets it as the
	 * {@link CurrentSleeper}.
	 *
	 * @return the {@link TestSleeper} (never <i>null</i>)
	 */
	protected TestSleeper setupTestSleeper() {

		TestSleeper sleeper = new TestSleeper(testClock);
		CurrentSleeper.set(sleeper);
		return sleeper;
	}
}
