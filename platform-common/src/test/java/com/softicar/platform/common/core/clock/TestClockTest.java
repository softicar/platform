package com.softicar.platform.common.core.clock;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.Assert;
import org.junit.Test;

public class TestClockTest extends Assert {

	private static final ZoneId BERLIN_ZONE = ZoneId.of("Europe/Berlin");
	private static final ZoneId SYDNEY_ZONE = ZoneId.of("Australia/Sydney");
	private static final Instant SOME_INSTANT = Instant.ofEpochMilli(1000000);

	@Test
	public void testConstructor() {

		Instant start = Instant.now();
		TestClock clock = new TestClock();
		Instant end = Instant.now();

		assertEquals(ZoneId.systemDefault(), clock.getZone());
		assertTrue(!clock.instant().isBefore(start));
		assertTrue(!clock.instant().isAfter(end));
	}

	@Test
	public void testConstructorWithInstant() {

		TestClock clock = new TestClock(SOME_INSTANT);

		assertEquals(ZoneId.systemDefault(), clock.getZone());
		assertEquals(SOME_INSTANT, clock.instant());
	}

	@Test
	public void testConstructorWithZoneAndInstant() {

		TestClock clock = new TestClock(BERLIN_ZONE, SOME_INSTANT);

		assertEquals(BERLIN_ZONE, clock.getZone());
		assertEquals(SOME_INSTANT, clock.instant());
	}

	@Test
	public void testSetInstant() {

		TestClock clock = new TestClock().setInstant(SOME_INSTANT);

		assertEquals(SOME_INSTANT, clock.instant());
	}

	@Test
	public void testSetWithZone() {

		TestClock clock = new TestClock(BERLIN_ZONE, SOME_INSTANT);
		Clock copy = clock.withZone(SYDNEY_ZONE);

		assertEquals(SYDNEY_ZONE, copy.getZone());
		assertEquals(SOME_INSTANT, copy.instant());
	}
}
