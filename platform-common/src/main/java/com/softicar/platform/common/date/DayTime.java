package com.softicar.platform.common.date;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.string.Padding;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a day plus a time within this day.
 *
 * @author Oliver Richers
 */
public class DayTime implements Comparable<DayTime>, IDisplayable {

	private static final int MAXIMUM_SECONDS = 24 * 3600 - 1;
	private Day day;
	private int secondsOfDay;

	// -------------------- Constructors -------------------- //

	/**
	 * Creates a new {@link DayTime} object for the given day at midnight.
	 *
	 * @param day
	 *            the day (never null)
	 */
	public DayTime(Day day) {

		this.day = day;
		this.secondsOfDay = 0;
	}

	/**
	 * Creates a new {@link DayTime} object for the given day and time.
	 *
	 * @param day
	 *            the day (never null)
	 * @param time
	 *            the time of the day (never null)
	 */
	public DayTime(Day day, Time time) {

		this.day = day;
		this.secondsOfDay = time.getTimeInSeconds();
	}

	/**
	 * Creates a new {@link DayTime} object for the given day at the given time.
	 *
	 * @param day
	 *            the day (never null)
	 * @param hour
	 *            the hour
	 * @param minute
	 *            the minute
	 * @param second
	 *            the second
	 */
	public DayTime(Day day, int hour, int minute, int second) {

		this.day = day;
		this.secondsOfDay = (hour * 60 + minute) * 60 + second;
	}

	/**
	 * Creates a new {@link DayTime} object from the given {@link Date} object.
	 *
	 * @param date
	 *            the date (never null)
	 */
	public DayTime(Date date) {

		init(date);
	}

	private DayTime(Day day, int seconds) {

		this.day = day;
		this.secondsOfDay = seconds;

		if (seconds < 0 || seconds > MAXIMUM_SECONDS) {
			throw new IllegalArgumentException(String.format("Seconds must be in the range [0, %s].", MAXIMUM_SECONDS));
		}
	}

	private DayTime(long millis) {

		init(millis);
	}

	// -------------------- Initialization -------------------- //

	private void init(Date date) {

		Calendar cal = ISOCalendar.getNewCalendar();
		cal.setTime(date);
		init(cal);
	}

	private void init(long millis) {

		Calendar cal = ISOCalendar.getNewCalendar();
		cal.setTimeInMillis(millis);
		init(cal);
	}

	private void init(Calendar cal) {

		this.day = Day.fromYMD(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		this.secondsOfDay = 3600 * cal.get(Calendar.HOUR_OF_DAY) + 60 * cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
	}

	// -------------------- Factory Methods -------------------- //

	/**
	 * Creates a new {@link DayTime} object from the specified {@link Date}.
	 * <p>
	 * If the given {@link Date} is <i>null</i>, this method returns
	 * <i>null</i>.
	 *
	 * @param date
	 *            the {@link Date} or <i>null</i>
	 * @return new {@link DayTime} representing the given {@link Date}, or
	 *         <i>null</i> if <i>null</i> was given
	 */
	public static DayTime fromDate(Date date) {

		return date != null? new DayTime(date) : null;
	}

	/**
	 * Creates a new {@link DayTime} object from the specified {@link Instant}.
	 * <p>
	 * If the given {@link Instant} is <i>null</i>, this method returns
	 * <i>null</i>.
	 *
	 * @param instant
	 *            the {@link Instant} or <i>null</i>
	 * @return new {@link DayTime} representing the given {@link Instant}, or
	 *         <i>null</i> if <i>null</i> was given
	 */
	public static DayTime fromInstant(Instant instant) {

		return instant != null? fromMillis(instant.toEpochMilli()) : null;
	}

	public static DayTime fromMillis(long millis) {

		return new DayTime(millis);
	}

	public static DayTime fromYMD_HMS(int year, int month, int day, int hour, int minute, int second) {

		return new DayTime(Day.fromYMD(year, month, day), hour, minute, second);
	}

	public static DayTime fromDayAndSeconds(Day day, int seconds) {

		return new DayTime(day, seconds);
	}

	public static DayTime get1970() {

		return new DayTime(Day.get1970());
	}

	/**
	 * Uses {@link CurrentClock} to determine the current {@link DayTime}.
	 *
	 * @return the current {@link DayTime} (never <i>null</i>)
	 */
	public static DayTime now() {

		return new DayTime(CurrentClock.get().millis());
	}

	/**
	 * @return DayTime object for today at 00:00:00 o'clock
	 */
	public static DayTime today() {

		return new DayTime(Day.today());
	}

	/**
	 * Identifies and returns the {@link DayTime} which represents the latest
	 * point in time, among the given {@link DayTime} instances.
	 * <p>
	 * Returns <i>null</i> if the given array is empty.
	 *
	 * @param dayTimes
	 *            the {@link DayTime} instances among which the latest one shall
	 *            be identified (never null)
	 * @return the latest given {@link DayTime} (may be null)
	 */
	public static DayTime max(DayTime...dayTimes) {

		return max(Arrays.asList(dayTimes));
	}

	/**
	 * Identifies and returns the {@link DayTime} which represents the latest
	 * point in time, among the given {@link DayTime} instances.
	 * <p>
	 * Returns <i>null</i> if the given {@link Collection} is empty.
	 *
	 * @param dayTimes
	 *            the {@link DayTime} instances among which the latest one shall
	 *            be identified (never null)
	 * @return the latest given {@link DayTime} (may be null)
	 */
	public static DayTime max(Collection<DayTime> dayTimes) {

		return dayTimes.stream().filter(Objects::nonNull).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
	}

	/**
	 * Identifies and returns the {@link DayTime} which represents the earliest
	 * point in time, among the given {@link DayTime} instances.
	 * <p>
	 * Returns <i>null</i> if the given array is empty.
	 *
	 * @param dayTimes
	 *            the {@link DayTime} instances among which the earliest one
	 *            shall be identified (never null)
	 * @return the earliest given {@link DayTime} (may be null)
	 */
	public static DayTime min(DayTime...dayTimes) {

		return min(Arrays.asList(dayTimes));
	}

	/**
	 * Identifies and returns the {@link DayTime} which represents the earliest
	 * point in time, among the given {@link DayTime} instances.
	 * <p>
	 * Returns <i>null</i> if the given {@link Collection} is empty.
	 *
	 * @param dayTimes
	 *            the {@link DayTime} instances among which the earliest one
	 *            shall be identified (never null)
	 * @return the earliest given {@link DayTime} (may be null)
	 */
	public static DayTime min(Collection<DayTime> dayTimes) {

		return dayTimes.stream().filter(Objects::nonNull).sorted().findFirst().orElse(null);
	}

	// -------------------- Getters -------------------- //

	/**
	 * Returns the day of this {@link DayTime} object.
	 *
	 * @return the day (never null)
	 */
	public Day getDay() {

		return day;
	}

	/**
	 * Returns the hour of the day of this {@link DayTime} object.
	 *
	 * @return the hour of the day in range [0,23]
	 */
	public int getHour() {

		return secondsOfDay / 3600;
	}

	/**
	 * Returns the minute of the hour of this {@link DayTime} object.
	 *
	 * @return the minute of the hour in range [0,59]
	 */
	public int getMinute() {

		return secondsOfDay / 60 % 60;
	}

	/**
	 * Returns the second of the minute of this {@link DayTime} object.
	 *
	 * @return the second of the minute in range [0,59]
	 */
	public int getSecond() {

		return secondsOfDay % 60;
	}

	public Time getTime() {

		return new Time(secondsOfDay);
	}

	/**
	 * Returns the second of the day of this {@link DayTime} object.
	 *
	 * @return the second of the day in range [0,86399]
	 */
	public int getSecondsOfDay() {

		return secondsOfDay;
	}

	// -------------------- Comparison -------------------- //

	/**
	 * Compares the {@link DayTime} object to the other object.
	 * <p>
	 * If this {@link DayTime} lies before the other, this returns a negative
	 * integer. If both {@link DayTime} objects are equal, this returns <i>0</i>
	 * . Otherwise this {@link DayTime} lies after the other and a positive
	 * integer is returned. This is equivalent to a lexicographical comparison
	 * of the year, month, day, hour, minute, second and millisecond, in the
	 * order.
	 */
	@Override
	public int compareTo(DayTime other) {

		int dayCompare = day.compareTo(other.day);
		if (dayCompare != 0) {
			return dayCompare;
		} else {
			return secondsOfDay - other.secondsOfDay;
		}
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DayTime) {
			DayTime other = (DayTime) object;
			return Objects.equals(day, other.day) && Objects.equals(secondsOfDay, other.secondsOfDay);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(day, secondsOfDay);
	}

	public boolean isAfter(DayTime other) {

		return compareTo(other) > 0;
	}

	public boolean isAfterOrEqual(DayTime other) {

		return compareTo(other) >= 0;
	}

	public boolean isBefore(DayTime other) {

		return compareTo(other) < 0;
	}

	public boolean isBeforeOrEqual(DayTime other) {

		return compareTo(other) <= 0;
	}

	/**
	 * Determines whether this {@link DayTime} lies in the time period which is
	 * spanned by the given {@link DayTime} instances (inclusive).
	 * <p>
	 * Returns <i>false</i> if the second given {@link DayTime} is before the
	 * first given {@link DayTime}.
	 *
	 * @param first
	 *            the first {@link DayTime} of the period (never null)
	 * @param last
	 *            the last {@link DayTime} of the period (never null)
	 * @return <i>true</i> this {@link DayTime} is in the given period;
	 *         <i>false</i> otherwise
	 */
	public boolean isBetween(DayTime first, DayTime last) {

		return isAfterOrEqual(first) && isBeforeOrEqual(last);
	}

	// -------------------- Conversion -------------------- //

	public Date toDate() {

		return new Date(toMillis());
	}

	public Instant toInstant() {

		return Instant.ofEpochMilli(toMillis());
	}

	public long toMillis() {

		Calendar cal = ISOCalendar.getNewCalendar();
		cal.clear();
		cal.set(Calendar.YEAR, getDay().getYear().getAbsoluteIndex());
		cal.set(Calendar.MONTH, getDay().getMonth().getIndexWithinYear() - 1);
		cal.set(Calendar.DAY_OF_MONTH, getDay().getIndexWithinMonth());
		cal.set(Calendar.HOUR_OF_DAY, getHour());
		cal.set(Calendar.MINUTE, getMinute());
		cal.set(Calendar.SECOND, getSecond());
		return cal.getTimeInMillis();
	}

	// -------------------- Arithmetic -------------------- //

	public DayTime plusDays(int days) {

		return new DayTime(day.getRelative(days), secondsOfDay);
	}

	public DayTime minusDays(int days) {

		return plusDays(-days);
	}

	public Duration getDuration(DayTime other) {

		return new Duration(this, other);
	}

	/**
	 * @param beginDayTime
	 * @return difference in seconds
	 */
	public double minus(DayTime beginDayTime) {

		return (toDate().getTime() - beginDayTime.toDate().getTime()) / 1000.0;
	}

	public DayTime getYesterday() {

		return plusDays(-1);
	}

	public DayTime getLastWeek() {

		return plusDays(-7);
	}

	public DayTime getTomorrow() {

		return plusDays(1);
	}

	public DayTime plusSecondsInUtc(double interval) {

		long time = toDate().getTime() + (long) (interval * 1000 + 0.5);
		return new DayTime(new Date(time));
	}

	public DayTime plusSeconds(double seconds) {

		return plusSeconds(Math.round(seconds));
	}

	public DayTime plusSeconds(long seconds) {

		if (seconds != 0) {
			long tmp = secondsOfDay + seconds;
			int time = (int) (tmp % ISOCalendar.SECONDS_PER_DAY);
			int days = (int) (tmp / ISOCalendar.SECONDS_PER_DAY);
			if (time < 0) {
				// days was rounded towards zero, but we want to round away from zero
				--days;
				time += ISOCalendar.SECONDS_PER_DAY;
			}

			if (days != 0) {
				return new DayTime(day.getRelative(days), time);
			} else {
				return new DayTime(day, time);
			}
		} else {
			return this;
		}
	}

	public DayTime minusSeconds(double seconds) {

		return minusSeconds(Math.round(seconds));
	}

	public DayTime minusSeconds(long seconds) {

		return plusSeconds(-seconds);
	}

	public DayTime truncateSeconds() {

		return minusSeconds(getSecond());
	}

	// -------------------- Formatting -------------------- //

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create(toLocalizedString());
	}

	public String format(String formatString) {

		return DateUtils.format(this, formatString);
	}

	/**
	 * Returns the hour, minutes and seconds of the time as string.
	 *
	 * @return time as string HH:MM:SS
	 */
	public String getTimeAsString() {

		return Padding.padLeft("" + getHour(), '0', 2) + ":" + Padding.padLeft("" + getMinute(), '0', 2) + ":" + Padding.padLeft("" + getSecond(), '0', 2);
	}

	/**
	 * Returns the hour and minute of the time as string.
	 *
	 * @return time as string HH:MM
	 */
	public String getTimeAsStringHM() {

		return Padding.padLeft("" + getHour(), '0', 2) + ":" + Padding.padLeft("" + getMinute(), '0', 2);
	}

	/**
	 * Returns the hour and minute of the time as string.
	 *
	 * @return time as string HHMM
	 */
	public String getTimeAsStringHHMM() {

		return Padding.padLeft("" + getHour(), '0', 2) + Padding.padLeft("" + getMinute(), '0', 2);
	}

	/**
	 * Returns the year, month, day, hour and minute of the time as string.
	 *
	 * @return time as string YYYYMMDD_HHMM
	 */
	public String getTimeAsStringYYYYMMDD_HHMM() {

		return "" + getDay().getYear().getAbsoluteIndex() + Padding.padLeft("" + getDay().getMonth().getIndexWithinYear(), '0', 2)
				+ Padding.padLeft("" + getDay().getIndexWithinMonth(), '0', 2) + Padding.padLeft("_" + getHour(), '0', 2)
				+ Padding.padLeft("" + getMinute(), '0', 2);
	}

	/**
	 * Returns the year, month, day, hour and minute of the time as string.
	 *
	 * @return time as string YYYYMMDDHHMM
	 */
	public String getTimeAsStringYYYYMMDDHHMM() {

		return "" + getDay().getYear().getAbsoluteIndex() + Padding.padLeft("" + getDay().getMonth().getIndexWithinYear(), '0', 2)
				+ Padding.padLeft("" + getDay().getIndexWithinMonth(), '0', 2) + Padding.padLeft("" + getHour(), '0', 2)
				+ Padding.padLeft("" + getMinute(), '0', 2);
	}

	/**
	 * Returns the year, month, day, hour and minute of the time as string.
	 *
	 * @return time as string YYYYMMDD:HHMM
	 */
	public String getTimeAsStringForEdiInterchange() {

		return "" + getDay().getYear().toYYString() + Padding.padLeft("" + getDay().getMonth().getIndexWithinYear(), '0', 2)
				+ Padding.padLeft("" + getDay().getIndexWithinMonth(), '0', 2) + Padding.padLeft(":" + getHour(), '0', 2)
				+ Padding.padLeft("" + getMinute(), '0', 2);
	}

	@Override
	public String toString() {

		return day + " " + getTimeAsString();
	}

	public final String toLocalizedString() {

		return day.toLocalizedString() + " " + getTimeAsString();
	}

	public String toGermanString() {

		return day.toGermanString() + " " + getTimeAsStringHM();
	}

	/**
	 * @return string in the form: hh:mm
	 */
	public String toStringHHMM(String separator) {

		return (getHour() < 10? "0" + getHour() : getHour()) + separator + (getMinute() < 10? "0" + getMinute() : getMinute());
	}

	/**
	 * @return string in the form: yyyymmddhhmm
	 */
	public String toStringYYYYMMDDHHMM() {

		return format("%y%%m%%dom%%h%%min%");
	}
}
