package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.date.DayTime;
import java.time.Duration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OneTimePasswordTest extends Assert {

	private final TestClock clock;

	public OneTimePasswordTest() {

		this.clock = new TestClock();
	}

	@Before
	public void before() {

		CurrentClock.set(clock);
	}

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void testIsExpiredWithZeroTimePassed() {

		OneTimePassword password = new OneTimePassword(1, "", DayTime.now());

		assertFalse(password.isExpired());
	}

	@Test
	public void testIsExpiredWithMaximumTimePassed() {

		OneTimePassword password = new OneTimePassword(1, "", DayTime.now());
		clock.add(Duration.ofSeconds(OneTimePassword.MAXIMUM_AGE_IN_SECONDS));

		assertFalse(password.isExpired());
	}

	@Test
	public void testIsExpiredWithMoreThanMaximumTimePassed() {

		OneTimePassword password = new OneTimePassword(1, "", DayTime.now());
		clock.add(Duration.ofSeconds(OneTimePassword.MAXIMUM_AGE_IN_SECONDS + 1));

		assertTrue(password.isExpired());
	}
}
