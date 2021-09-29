package com.softicar.platform.common.date;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.string.Padding;

/**
 * Represents a week.
 * <p>
 * This is not just an index of [1,52] within any year but an absolute week,
 * i.e. including the actual year.
 *
 * @author Oliver Richers
 */
public final class Week extends DateItem<Week> {

	// ******************************************************************************** //
	// * standard functions                                                           * //
	// ******************************************************************************** //

	/**
	 * Returns the week with the specified index.
	 *
	 * @param index
	 *            the absolute index of the week
	 * @return the week with the specified index
	 */
	public static Week get(int index) {

		return new Week(index);
	}

	/**
	 * Returns the index of this week. The following holds true:
	 * Week.get(x).getAbsoluteIndex() == x
	 *
	 * @return the absolute index
	 */
	@Override
	public int getAbsoluteIndex() {

		return m_index;
	}

	/**
	 * Returns the week in the specified instance from this week.
	 *
	 * @param distance
	 *            the signed distance in weeks
	 * @return the week with the specified distance
	 */
	@Override
	public Week getRelative(int distance) {

		return Week.get(m_index + distance);
	}

	/**
	 * Returns the current week.
	 *
	 * @return current week
	 */
	public static Week getCurrentWeek() {

		return Day.today().getWeek();
	}

	// ******************************************************************************** //
	// * year functions                                                               * //
	// ******************************************************************************** //

	/**
	 * Returns the year that this week belongs to.
	 *
	 * @return the year of the week
	 */
	public Year getYear() {

		return Year.get(ISOCalendar.getYearWithWeek(m_index));
	}

	/**
	 * Returns the index of this week within its containing year.
	 *
	 * @return the index of the week [1,52] or [1,53]
	 */
	public int getIndexWithinYear() {

		return ISOCalendar.getWeekIndexWithinYear(m_index);
	}

	// ******************************************************************************** //
	// * month functions                                                              * //
	// ******************************************************************************** //

	/**
	 * Returns the month of this week.
	 *
	 * @return the month containing the most days of this week
	 */
	public Month getMonth() {

		return Month.get(ISOCalendar.getMonthWithWeek(m_index));
	}

	/**
	 * Returns the index of this week within its containing month.
	 *
	 * @return the relative index [1,5]
	 */
	public int getIndexWithinMonth() {

		return ISOCalendar.getWeekIndexWithinMonth(m_index);
	}

	// ******************************************************************************** //
	// * day functions                                                                * //
	// ******************************************************************************** //

	/**
	 * Returns a range over all days of this week.
	 *
	 * @return range over all days
	 */
	public DateItemRange<Day> getDays() {

		int[] days = ISOCalendar.getFirstAndLastDayOfWeek(m_index);
		return new DateItemRange<>(Day.get(days[0]), Day.get(days[1]));
	}

	/**
	 * Returns the day with the specified name.
	 *
	 * @param weekday
	 *            the weekday name
	 * @return the matching day
	 */
	public Day getDay(Weekday weekday) {

		return getDays().get(weekday.getIndex());
	}

	/**
	 * Returns the first day of the week.
	 *
	 * @return Monday of this week
	 */
	public Day getMonday() {

		return getDay(Weekday.MONDAY);
	}

	/**
	 * Returns the second day of the week.
	 *
	 * @return Tuesday of this week
	 */
	public Day getTuesday() {

		return getDay(Weekday.TUESDAY);
	}

	/**
	 * Returns the third day of the week.
	 *
	 * @return Wednesday of this week
	 */
	public Day getWednesday() {

		return getDay(Weekday.WEDNESDAY);
	}

	/**
	 * Returns the fourth day of the week.
	 *
	 * @return Thursday of this week
	 */
	public Day getThursday() {

		return getDay(Weekday.THURSDAY);
	}

	/**
	 * Returns the fifth day of the week.
	 *
	 * @return Friday of this week
	 */
	public Day getFriday() {

		return getDay(Weekday.FRIDAY);
	}

	/**
	 * Returns the sixth day of the week.
	 *
	 * @return Saturday of this week
	 */
	public Day getSaturday() {

		return getDay(Weekday.SATURDAY);
	}

	/**
	 * Returns the seventh day of the week.
	 *
	 * @return Sunday of this week
	 */
	public Day getSunday() {

		return getDay(Weekday.SUNDAY);
	}

	/**
	 * Returns a string representation of this week
	 *
	 * @return a string in the form 2006-W45
	 */
	@Override
	public String toString() {

		return getYear() + "-W" + Padding.padLeft("" + getIndexWithinYear(), '0', 2);
	}

	public String toYYWWString() {

		return getYear().toYYString() + toWWString();
	}

	public String toYYYYWWString() {

		return getYear().toString() + toWWString();
	}

	/**
	 * Returns a string in the format YYYY-WW for example 2010-40
	 *
	 * @return Week in string format: YYYY-WW
	 */
	public String toYYYYMinusWWString() {

		return getYear().toString() + "-" + toWWString();
	}

	public String toWWString() {

		String tmp = "" + getIndexWithinYear();
		return Padding.padLeft(tmp, '0', 2);
	}

	public static Week fromPeriod(Period p) {

		Day beginDay = p.getBeginDayTime().getDay();
		Day endDay = p.getEndDayTime().getDay();
		Week week = beginDay.getWeek();
		if (!week.getMonday().equals(beginDay) || !week.getSunday().equals(endDay)) {
			throw new SofticarDeveloperException("Trying to convert the not week representating period '%s' into a week.", p.toString());
		} else {
			return week;
		}
	}

	// ******************************************************************************** //
	// * misc                                                                         * //
	// ******************************************************************************** //

	public String getDisplayName() {

		return String.format("KW%d", getIndexWithinYear());
	}

	// ******************************************************************************** //
	// * private stuff                                                                * //
	// ******************************************************************************** //

	private Week(int index) {

		m_index = index;
	}

	private final int m_index; // absolute index of this week
}
