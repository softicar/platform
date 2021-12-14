package com.softicar.platform.common.date;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.string.Padding;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents a day.
 *
 * @author Oliver Richers
 */
public final class Day extends DateItem<Day> {

	// -------------------------------- INDEX FUNCTIONS -------------------------------- //

	/**
	 * Returns the day with the specified index.
	 * <p>
	 * The day with the index 0 is the first day of January of the year 0.
	 *
	 * @param index
	 *            the absolute index of the day
	 * @return the day with the specified index
	 */
	public static Day get(int index) {

		return new Day(index);
	}

	/**
	 * Returns the absolute index of this day.
	 * <p>
	 * The following holds true: Day.get(x).getAbsoluteIndex() == x
	 */
	@Override
	public int getAbsoluteIndex() {

		return m_index;
	}

	/**
	 * Returns the day in the specified distance from this day.
	 */
	@Override
	public Day getRelative(int distance) {

		return get(m_index + distance);
	}

	// -------------------------------- YEAR FUNCTIONS -------------------------------- //

	/**
	 * Returns the year containing this day.
	 *
	 * @return the containing year
	 */
	public Year getYear() {

		return Year.get(ISOCalendar.getYearWithDay(m_index));
	}

	/**
	 * Returns the index of this day within its containing year.
	 *
	 * @return the index in the range [1,365] or [1,366] depending on the type
	 *         of the year
	 */
	public int getIndexWithinYear() {

		return ISOCalendar.getDayIndexWithinYear(m_index);
	}

	// -------------------------------- MONTH FUNCTIONS -------------------------------- //

	/**
	 * Returns the month containing this day.
	 *
	 * @return the containing month
	 */
	public Month getMonth() {

		return Month.get(ISOCalendar.getMonthWithDay(m_index));
	}

	/**
	 * Returns the index of this day within its containing month.
	 *
	 * @return the index in the range [1,31]
	 */
	public int getIndexWithinMonth() {

		return ISOCalendar.getDayIndexWithinMonth(m_index);
	}

	// -------------------------------- WEEK FUNCTIONS -------------------------------- //

	/**
	 * Returns the week containing this day.
	 *
	 * @return the containing week
	 */
	public Week getWeek() {

		return Week.get(ISOCalendar.getWeekWithDay(m_index));
	}

	/**
	 * Returns the index of this day within its containing week.
	 *
	 * @return the index in the range [1,7]
	 */
	public int getIndexWithinWeek() {

		return ISOCalendar.getDayIndexWithinWeek(m_index);
	}

	/**
	 * Returns the next day from this day, that is of the given targetWeekDay.
	 * Special case: If this day is of the given targetWeekDay, the returned day
	 * is exactly one week later.
	 *
	 * @param targetWeekDay
	 *            desired week day.
	 * @return next day that is of the given targektWeekDay.
	 */
	public Day getNextWeekDay(Weekday targetWeekDay) {

		Day targetDay = new Day(this.m_index).getNext();
		while (!targetDay.getWeekday().equals(targetWeekDay)) {
			targetDay = targetDay.getNext();
		}

		return targetDay;
	}

	/**
	 * Returns the weekday name of this day.
	 */
	public Weekday getWeekday() {

		return Weekday.get(getIndexWithinWeek());
	}

	public boolean isMonday() {

		return getWeek().getMonday().equals(this);
	}

	public boolean isTuesday() {

		return getWeek().getTuesday().equals(this);
	}

	public boolean isWednesday() {

		return getWeek().getWednesday().equals(this);
	}

	public boolean isThursday() {

		return getWeek().getThursday().equals(this);
	}

	public boolean isFriday() {

		return getWeek().getFriday().equals(this);
	}

	public boolean isSaturday() {

		return getWeek().getSaturday().equals(this);
	}

	public boolean isSunday() {

		return getWeek().getSunday().equals(this);
	}

	// -------------------------------- 1970 FUNCTIONS -------------------------------- //

	/**
	 * Returns the distance of this day since the first of January 1970.
	 *
	 * @return the distance from 1970-01-01
	 */
	public int getIndexSince1970() {

		return m_index - DAY_INDEX_1970;
	}

	public static Day get1970() {

		return new Day(DAY_INDEX_1970);
	}

	// -------------------------------- FACTORY FUNCTIONS -------------------------------- //

	/**
	 * Returns the {@link Day} for the given triplet of year, month and day of
	 * the month.
	 *
	 * @param year
	 *            the absolute number of the year
	 * @param month
	 *            the number of the month [1,12]
	 * @param day
	 *            the number of the day [1,31]
	 * @return the respective {@link Day} (never <i>null</i>)
	 */
	public static Day fromYMD(int year, int month, int day) {

		int absoluteMonth = ISOCalendar.getMonthOfYear(year, month);
		int absoluteDay = ISOCalendar.getDayOfMonth(absoluteMonth, day);
		return Day.get(absoluteDay);
	}

	/**
	 * Returns the {@link Day} for the given triplet of year, month and day of
	 * the month.
	 *
	 * @param year
	 *            the absolute number of the year
	 * @param month
	 *            the number of the month [1,12]
	 * @param day
	 *            the number of the day [1,n], where <i>n</i> is the number of
	 *            days in the month
	 * @return the respective {@link Day} (never <i>null</i>)
	 * @throws IllegalDateSpecificationException
	 *             if either the month number violates the range [1,12] or if
	 *             the day number violates the range [1,n]
	 */
	public static Day fromYMDChecked(int year, int month, int day) {

		if (!ISOCalendar.isValidYMD(year, month, day)) {
			throw new IllegalDateSpecificationException(year, month, day);
		}
		return fromYMD(year, month, day);
	}

	/**
	 * The same as {@link #fromYMD(int, int, int)}, just with a {@link Year} as
	 * first parameter.
	 */
	public static Day fromYMD(Year year, int month, int day) {

		return fromYMD(year.getAbsoluteIndex(), month, day);
	}

	/**
	 * Returns the day represented by the specified date object.
	 *
	 * @param millis
	 *            the current milliseconds since midnight, January 1, 1970 UTC
	 * @return the matching day
	 */
	public static Day fromMillis(long millis) {

		Calendar cal = ISOCalendar.getNewCalendar();
		cal.setTimeInMillis(millis);
		return fromYMD(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Returns the day represented by the specified date object.
	 *
	 * @param date
	 *            the date object or null
	 * @return the matching day; null if date was null
	 */
	public static Day fromDate(Date date) {

		if (date != null) {
			return fromMillis(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * Parses the specified text into a day according to the specified format.
	 *
	 * @param format
	 *            the format of the text
	 * @param text
	 *            the textual representation of the day
	 * @return format.parseDay(text)
	 */
	public static Day parse(DateFormat format, String text) {

		return format.parseDay(text);
	}

	/**
	 * Returns the current {@link Day}, according to {@link CurrentClock}.
	 *
	 * @return today the current {@link Day} (never <i>null</i>)
	 */
	public static Day today() {

		return Day.fromMillis(CurrentClock.get().millis());
	}

	// -------------------------------- CONVERTING FUNCTIONS -------------------------------- //

	public long toMillis() {

		Calendar cal = ISOCalendar.getNewCalendar();
		cal.set(Calendar.YEAR, getYear().getAbsoluteIndex());
		cal.set(Calendar.MONTH, getMonth().getIndexWithinYear() - 1);
		cal.set(Calendar.DAY_OF_MONTH, getIndexWithinMonth());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public DayTime getBegin() {

		return new DayTime(this, 0, 0, 0);
	}

	public DayTime getEnd() {

		return new DayTime(this, 23, 59, 59);
	}

	public DayTime toDayTime() {

		return new DayTime(this);
	}

	public DayTime toDayTime(int seconds) {

		return DayTime.fromDayAndSeconds(this, seconds);
	}

	public Date toDate() {

		return new Date(toMillis());
	}

	// -------------------------------- TO STRING FUNCTIONS -------------------------------- //

	public String format(String formatString) {

		return DateUtils.format(this, formatString);
	}

	/**
	 * Returns the date in ISO-Format: YYYY-MM-DD
	 */
	@Override
	public final String toString() {

		return toISOString();
	}

	/**
	 * Returns the date in German format: DD.MM.YYYY
	 */
	public final String toGermanString() {

		return String.format("%02d.%02d.%04d", getIndexWithinMonth(), getMonth().getIndexWithinYear(), getYear().getAbsoluteIndex());
	}

	/**
	 * Returns the day in ISO-format: YYYY-MM-DD
	 */
	public final String toISOString() {

		return String.format("%04d-%02d-%02d", getYear().getAbsoluteIndex(), getMonth().getIndexWithinYear(), getIndexWithinMonth());
	}

	/**
	 * Returns the day with index of day within month and index of month.
	 */
	public final String toDMString() {

		return String.format("%d.%d.", getIndexWithinMonth(), getMonth().getIndexWithinYear());
	}

	public final String toHumanString() {

		switch (Day.today().getDistance(this)) {
		case -2:
			return CommonDateI18n._2_DAYS_AGO.toString();
		case -1:
			return CommonDateI18n.YESTERDAY.toString();
		case 0:
			return CommonDateI18n.TODAY.toString();
		case 1:
			return CommonDateI18n.TOMORROW.toString();
		default:
			return toString();
		}
	}

	/**
	 * Returns a string representation in the form YYWWD.
	 * <p>
	 * The returned D will be in the range 1 through 7 for Monday through
	 * Sunday, respectively.
	 *
	 * @return the day as a string YYWWD
	 */
	public final String toYYWWDString() {

		return getWeek().toYYWWString() + getIndexWithinWeek();
	}

	public final String toDDString() {

		return Padding.padLeft("" + getIndexWithinMonth(), '0', 2);
	}

	public final String toYYMMDDString() {

		return getMonth().toYYMMString() + toDDString();
	}

	// -------------------------------- PRIVATE -------------------------------- //

	private Day(int index) {

		m_index = index;
	}

	private final int m_index; // absolute index of this day
	private static final int DAY_INDEX_1970 = 719528;
}
