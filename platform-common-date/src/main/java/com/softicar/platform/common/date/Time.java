package com.softicar.platform.common.date;

import java.util.Objects;

/**
 * Represents the time of the day in hours, minutes and seconds.
 *
 * @author Oliver Richers
 * @author Semsudin Mekanic
 */
public class Time implements Comparable<Time> {

	private static final int MAXIMUM_SECONDS_PER_DAY = 24 * 60 * 60;
	private final int time;

	public Time() {

		this(0);
	}

	public Time(int hour, int minute, int second) {

		if (hour < 0 || hour >= 24) {
			throw new IllegalArgumentException(String.format("Illegal value %s for hour. Must be in the range [0, 23].", hour));
		}
		if (minute < 0 || minute >= 60) {
			throw new IllegalArgumentException(String.format("Illegal value %s for minute. Must be in the range [0, 59].", minute));
		}
		if (second < 0 || second >= 60) {
			throw new IllegalArgumentException(String.format("Illegal value %s for second. Must be in the range [0, 59].", second));
		}

		this.time = hour * 3600 + minute * 60 + second;
	}

	public Time(int seconds) {

		if (seconds < 0 || seconds >= MAXIMUM_SECONDS_PER_DAY) {
			throw new IllegalArgumentException(
				String.format("Illegal value %s for seconds of day. Must be in the range [0, %s].", seconds, MAXIMUM_SECONDS_PER_DAY - 1));
		}

		this.time = seconds;
	}

	public static Time parseTime(String timeString) {

		String[] parts = timeString.split(":");
		if (parts.length == 3) {
			return new Time(//
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1]),
				Integer.parseInt(parts[2]));
		} else {
			throw new IllegalArgumentException(String.format("Illegal time string '%s'. Expected to have 3 parts separated by colon.", timeString));
		}
	}

	public int getTimeInSeconds() {

		return time;
	}

	public int getHour() {

		return time / 3600;
	}

	public int getMinute() {

		return (time / 60) % 60;
	}

	public int getSecond() {

		return time % 60;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Time) {
			return time == ((Time) object).time;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(time);
	}

	@Override
	public String toString() {

		return toIsoFormat();
	}

	public String toIsoFormat() {

		return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
	}

	@Override
	public int compareTo(Time other) {

		return Integer.compare(time, other.time);
	}
}
