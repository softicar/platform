package com.softicar.platform.common.testing;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.thread.sleeper.CurrentSleeper;
import com.softicar.platform.common.core.thread.sleeper.TestSleeper;
import com.softicar.platform.common.date.ISOCalendar;
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
		ISOCalendar.setDefaultTimeZone();
	}

	/**
	 * Creates a new {@link IStaticObject} instance that can be used as test
	 * marker.
	 *
	 * @return a new instance of {@link IStaticObject} (never <i>null</i>)
	 */
	public static IStaticObject newMarker() {

		return new IStaticObject() { /* nothing to add */ };
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
