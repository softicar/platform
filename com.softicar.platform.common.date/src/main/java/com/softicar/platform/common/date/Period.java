package com.softicar.platform.common.date;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Represents a duration between two {@link Timestamp} instances.
 * <p>
 * Any {@link Period} is immutable.
 * <p>
 * TODO This class needs a cleanup, or consider to remove it all together.
 *
 * @author Oliver Richers
 */
public class Period implements Comparable<Period> {

	public static final Period DEFAULT = new Period();
	private final Timestamp begin;
	private final Timestamp end;

	// ------------------ constructors ------------------ //

	public Period() {

		this(0L, 0L);
	}

	public Period(long begin, long end) {

		if (begin > end) {
			throw new IllegalArgumentException(
				String.format("Begin of period must be less or equal to end but was (%s, %s).", DayTime.fromMillis(begin), DayTime.fromMillis(end)));
		}

		this.begin = new Timestamp(begin);
		this.end = new Timestamp(end);
	}

	public Period(Day day) {

		this(day, day);
	}

	public Period(Day firstDay, Day lastDay) {

		this(firstDay.getBegin(), lastDay.getEnd());
	}

	public Period(DayTime point) {

		this(point, point);
	}

	public Period(DayTime begin, DayTime end) {

		this(begin.toDate(), end.toDate());
	}

	/**
	 * Constructs a period that is a point in time.
	 *
	 * @param point
	 *            a point in time
	 */
	public Period(Date point) {

		this(point, point);
	}

	public Period(Date begin, Date end) {

		this(begin.getTime(), end.getTime());
	}

	public Period(Timestamp begin, Timestamp end) {

		this(begin.getTime(), end.getTime());
	}

	public Period(Week week) {

		this(week, week);
	}

	public Period(Week firstWeek, Week lastWeek) {

		this(firstWeek.getMonday(), lastWeek.getSunday());
	}

	public Period(Month month) {

		this(month.getDays().getFirst(), month.getDays().getLast());
	}

	public Period(Year year) {

		this(year.getDays().getFirst(), year.getDays().getLast());
	}

	public Timestamp getBegin() {

		return begin;
	}

	public Timestamp getEnd() {

		return end;
	}

	public DayTime getBeginDayTime() {

		return new DayTime(getBegin());
	}

	public DayTime getEndDayTime() {

		return new DayTime(getEnd());
	}

	/**
	 * @return true if begin equals end
	 */
	public boolean isPointInTime() {

		return begin.equals(end);
	}

	/**
	 * @return end - begin in milliseconds
	 */
	public long getSize() {

		return end.getTime() - begin.getTime();
	}

	/**
	 * Returns duration of period in seconds.
	 */
	public double getDuration() {

		return getSize() / 1000.0;
	}

	/**
	 * Returns duration of period in days.
	 */
	public Long getDurationInDays() {

		return Math.round(getDuration() / 60 / 60 / 24);
	}

	/**
	 * Returns duration of period as a DayTime object.
	 */
	public DayTime getDurationDayTime() {

		return DayTime.fromDayAndSeconds(Day.get1970(), (int) Math.round(getDuration()));
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Period) {
			return compareTo((Period) object) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(begin, end);
	}

	public List<Day> getContainedDays() {

		List<Day> days = new ArrayList<>();

		Day begin = getBeginDayTime().getDay();
		Day end = getEndDayTime().getDay();

		for (Day day = begin; day.compareTo(end) <= 0; day = day.getNext()) {
			days.add(day);
		}

		return days;
	}

	public Set<Period> getContainedWeeksAsPeriods() {

		Set<Period> weeks = new TreeSet<>();

		for (Period p: getContainedDaysAsPeriods()) {
			weeks.add(new Period(p.getBeginDayTime().getDay().getWeek()));
		}

		return weeks;
	}

	public Set<Week> getContainedWeeks() {

		Set<Week> weeks = new TreeSet<>();

		for (Period p: getContainedWeeksAsPeriods()) {
			weeks.add(Week.fromPeriod(p));
		}

		return weeks;
	}

	public Vector<Period> getContainedDaysAsPeriods() {

		Vector<Period> days = new Vector<>();

		Day begin = getBeginDayTime().getDay();
		Day end = getEndDayTime().getDay();

		for (Day day = begin; day.compareTo(end) <= 0; day = day.getNext()) {
			days.add(new Period(new DayTime(day, 0, 0, 0), new DayTime(day, 23, 59, 59)));
		}

		return days;
	}

	/**
	 * If begin and end of period p equal begin and end of this period this
	 * method returns true, false otherwise. If period p equals null this method
	 * will return false
	 *
	 * @param p
	 * @return true, if this period equals period p
	 */
	public boolean equals(Period p) {

		if (p == null) {
			return false;
		}
		return begin.equals(p.begin) && end.equals(p.end);
	}

	@Override
	public int compareTo(Period p) {

		int b = begin.compareTo(p.begin);
		return b != 0? b : end.compareTo(p.end);
	}

	public static Period min(Period a, Period b) {

		return a.compareTo(b) <= 0? a : b;
	}

	public static Period max(Period a, Period b) {

		return a.compareTo(b) > 0? a : b;
	}

	@Override
	public String toString() {

		return "(" + begin + " -> " + end + ")";
	}

	/**
	 * Returns true if Period p is totally contained within this period.
	 *
	 * @param p
	 * @return true if p is contained
	 */
	public boolean contains(Period p) {

		return getBeginDayTime().compareTo(p.getBeginDayTime()) <= 0 && getEndDayTime().compareTo(p.getEndDayTime()) >= 0;
	}

	/**
	 * Checks if Period p intersects this period in some parts.
	 *
	 * @param p
	 * @return true if p intersects this.
	 */
	public boolean intersects(Period p) {

		return getBeginDayTime().compareTo(p.getBeginDayTime()) <= 0 && getEndDayTime().compareTo(p.getBeginDayTime()) >= 0
				|| getBeginDayTime().compareTo(p.getEndDayTime()) >= 0 && getBeginDayTime().compareTo(p.getEndDayTime()) <= 0;
	}

	/**
	 * Checks if a period is only defined within one day.
	 *
	 * @return true if begin and end daytime lays within the same day.
	 */
	public boolean isOneDay() {

		return getBeginDayTime().getDay().equals(getEndDayTime().getDay());
	}

	public boolean isWeek() {

		Week beginWeek = getBeginDayTime().getDay().getWeek();

		return beginWeek.getMonday().equals(getBeginDayTime().getDay()) && beginWeek.getSunday().equals(getEndDayTime().getDay());
	}

	public Period getModifiedPeriodByWeeks(int weeks) {

		DayTime begin = getBeginDayTime();
		DayTime end = getEndDayTime();

		begin = begin.plusDays(weeks * 7);
		end = end.plusDays(weeks * 7);

		Period modifiedPeriod = new Period(begin, end);

		return modifiedPeriod;
	}

	public Period getModifiedPeriodByDays(int days) {

		DayTime begin = getBeginDayTime();
		DayTime end = getEndDayTime();

		begin = begin.plusDays(days);
		end = end.plusDays(days);

		Period modifiedPeriod = new Period(begin, end);

		return modifiedPeriod;
	}

	public String getHumanString() {

		return getBeginDayTime().getDay().toString() + " -- " + getEndDayTime().getDay().toString();
	}

	/**
	 * @param p
	 * @return the intersection of this Period with the given period, or null if
	 *         there is no intersection!
	 */
	public Period getIntersection(Period p) {

		final long a = getBegin().getTime();
		final long b = getEnd().getTime();

		final long c = p.getBegin().getTime();
		final long d = p.getEnd().getTime();

		if (c <= a && b <= d) {
			return new Period(a, b);
		} else if (a <= c && d <= b) {
			return new Period(c, d);
		} else if (c <= b && b <= d) {
			return new Period(c, b);
		} else if (c <= a && a <= d) {
			return new Period(a, d);
		} else if (a <= c && d <= b) {
			return new Period(c, d);
		} else if (c <= a && b <= d) {
			return new Period(a, b);
		} else if (b == c) {
			return new Period(b, b);
		} else if (a == d) {
			return new Period(a, a);
		} else {
			return null;
		}
	}

	/**
	 * Tests if two periods of time overlap
	 *
	 * @param periodOne
	 * @param periodTwo
	 * @return false if periodOne and periodTwo don't overlap
	 */
	public static boolean overlap(Period periodOne, Period periodTwo) {

		return overlap(periodOne, periodTwo, 0);
	}

	/**
	 * Tests if two periods of time overlap.
	 *
	 * @param periodOne
	 * @param periodTwo
	 * @param milliseconds
	 *            - the number of milliseconds the two periods actually CAN
	 *            overlap without changing the output value of this method to
	 *            true
	 * @return false if periodOne and periodTwo don't overlap
	 */
	public static boolean overlap(Period periodOne, Period periodTwo, long milliseconds) {

		long a = periodOne.getBegin().getTime();
		long b = periodOne.getEnd().getTime();
		long c = periodTwo.getBegin().getTime();
		long d = periodTwo.getEnd().getTime();

		if (b < c + milliseconds || a > d - milliseconds) {
			return false;
		}
		return true;
	}
}
