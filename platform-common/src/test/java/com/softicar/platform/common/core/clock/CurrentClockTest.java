package com.softicar.platform.common.core.clock;

import com.softicar.platform.common.testing.AbstractTest;
import java.time.Clock;
import java.time.ZoneId;
import org.junit.Test;

public class CurrentClockTest extends AbstractTest {

	@Test
	public void testGet() {

		Clock clock1 = CurrentClock.get();
		Clock clock2 = CurrentClock.get();

		assertSame(clock1, clock2);
		assertEquals(ZoneId.systemDefault(), clock1.getZone());
	}

	@Test
	public void testSet() {

		Clock testClock = new TestClock();
		CurrentClock.set(testClock);

		Clock currentClock = CurrentClock.get();

		assertSame(testClock, currentClock);
	}
}
