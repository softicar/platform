package com.softicar.platform.common.core.clock;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;

/**
 * A controllable {@link Clock} useful for unit tests.
 * <p>
 * This {@link Clock} returns the same {@link Instant} as long as it is not
 * changed with {@link #setInstant(Instant)}.
 *
 * @author Oliver Richers
 */
public class TestClock extends Clock {

	private final ZoneId zone;
	private Instant instant;

	/**
	 * Initialized this clock with the current {@link Instant}.
	 * <p>
	 * The method {@link ZoneId#systemDefault()} is used to obtain the
	 * {@link ZoneId} to be used.
	 */
	public TestClock() {

		this(ZoneId.systemDefault(), Instant.now());
	}

	/**
	 * Initialized this clock with the given {@link Instant}.
	 * <p>
	 * The method {@link ZoneId#systemDefault()} is used to obtain the
	 * {@link ZoneId} to be used.
	 *
	 * @param instant
	 *            the {@link Instant} to use (never null)
	 */
	public TestClock(Instant instant) {

		this(ZoneId.systemDefault(), instant);
	}

	/**
	 * Initialized this clock with the given {@link ZoneId} and {@link Instant}.
	 *
	 * @param zone
	 *            the {@link ZoneId} to use (never null)
	 * @param instant
	 *            the {@link Instant} to use (never null)
	 */
	public TestClock(ZoneId zone, Instant instant) {

		this.zone = zone;
		this.instant = instant;
	}

	@Override
	public ZoneId getZone() {

		return zone;
	}

	@Override
	public Clock withZone(ZoneId zone) {

		return new TestClock(zone, instant);
	}

	@Override
	public Instant instant() {

		return instant;
	}

	/**
	 * Specifies the {@link Instant} to return by this {@link Clock}.
	 *
	 * @param instant
	 *            the instant to use (never null)
	 * @return this {@link Clock}
	 */
	public TestClock setInstant(Instant instant) {

		this.instant = instant;
		return this;
	}

	/**
	 * Adds the given milliseconds to the {@link Instant} to return by this
	 * {@link Clock}.
	 *
	 * @param millis
	 *            the milliseconds to add
	 * @return this {@link Clock}
	 */
	public TestClock addMillis(long millis) {

		this.instant = instant.plusMillis(millis);
		return this;
	}

	/**
	 * Adds the given {@link TemporalAmount} to the {@link Instant} to return by
	 * this {@link Clock}.
	 *
	 * @param temporalAmount
	 *            the temporal amount to add
	 * @return this {@link Clock}
	 */
	public TestClock add(TemporalAmount temporalAmount) {

		this.instant = instant.plus(temporalAmount);
		return this;
	}
}
