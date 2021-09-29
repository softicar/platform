package com.softicar.platform.common.core.thread.sleeper;

import com.softicar.platform.common.core.clock.TestClock;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

/**
 * Implementation of {@link ISleeper} for unit tests.
 * <p>
 * This implementation simply forwards the given {@link TestClock}.
 * <p>
 * The {@link #sleep(long)} implementation in this class will <b>not</b> cause
 * any execution pause.
 *
 * @author Oliver Richers
 */
public class TestSleeper implements ISleeper {

	private final TestClock clock;
	private final List<Long> callParameters;

	public TestSleeper(TestClock clock) {

		this.clock = clock;
		this.callParameters = new ArrayList<>();
	}

	@Override
	public void sleep(long millis) {

		clock.addMillis(millis);
		callParameters.add(millis);
	}

	public void assertOneCall(long expectedMillis) {

		Assert.assertEquals("sleep calls", 1, callParameters.size());
		Assert.assertEquals("sleep call milliseconds", expectedMillis, callParameters.get(0).longValue());
	}
}
