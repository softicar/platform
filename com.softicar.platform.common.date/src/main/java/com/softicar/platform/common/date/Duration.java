package com.softicar.platform.common.date;

import java.util.Objects;

/**
 * Represents the distance between two {@link DayTime} objects.
 *
 * @author Oliver Richers
 */
public class Duration {

	private static final long HOURS_PER_DAY = 24;
	private static final long MINUTES_PER_HOUR = 60;
	private static final long MINUTES_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR;
	private static final long SECONDS_PER_MINUTE = 60;
	private static final long SECONDS_PER_HOUR = MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
	private static final long SECONDS_PER_DAY = MINUTES_PER_DAY * SECONDS_PER_MINUTE;

	private final long totalSeconds;

	public Duration(DayTime begin, DayTime end) {

		this(toSeconds(end) - toSeconds(begin));
	}

	public Duration(long seconds) {

		this.totalSeconds = seconds;
	}

	@Override
	public String toString() {

		int days = getDays();
		int hours = getHoursOfDay();
		int minutes = getMinutesOfHour();
		int seconds = getSecondsOfMinute();
		if (days != 0) {
			return CommonDateI18n.ARG1D_ARG2H_ARG3MIN_ARG4S.toDisplay(days, hours, minutes, seconds).toString();
		} else if (hours != 0) {
			return CommonDateI18n.ARG1H_ARG2MIN_ARG3S.toDisplay(hours, minutes, seconds).toString();
		} else if (minutes != 0) {
			return CommonDateI18n.ARG1MIN_ARG2S.toDisplay(minutes, seconds).toString();
		} else {
			return CommonDateI18n.ARG1_SECONDS.toDisplay(seconds).toString();
		}
	}

	@Override
	public boolean equals(Object object) {

		if (object == this) {
			return true;
		} else if (object instanceof Duration) {
			return totalSeconds == ((Duration) object).totalSeconds;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(totalSeconds);
	}

	public int getDays() {

		return (int) (totalSeconds / SECONDS_PER_DAY);
	}

	public int getHoursOfDay() {

		return (int) (totalSeconds % SECONDS_PER_DAY / SECONDS_PER_HOUR);
	}

	public int getMinutesOfHour() {

		return (int) (totalSeconds % SECONDS_PER_HOUR / SECONDS_PER_MINUTE);
	}

	public int getSecondsOfMinute() {

		return (int) (totalSeconds % SECONDS_PER_MINUTE);
	}

	public long getTotalSeconds() {

		return totalSeconds;
	}

	/**
	 * Clamps this duration to zero if it is negative.
	 *
	 * @return zero duration if this duration is negative,otherwise this
	 *         duration is returned
	 */
	public Duration clamp() {

		return totalSeconds < 0? new Duration(0) : this;
	}

	private static long toSeconds(DayTime dayTime) {

		Day day = dayTime.getDay();
		return day.getAbsoluteIndex() * SECONDS_PER_DAY + dayTime.getSecondsOfDay();
	}
}
