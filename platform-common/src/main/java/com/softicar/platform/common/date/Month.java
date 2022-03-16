package com.softicar.platform.common.date;

import com.softicar.platform.common.string.Padding;

/**
 * Represents a month.
 * <p>
 * This is not just an index of [1,12] within any year but an absolute month,
 * i.e. including the actual year.
 *
 * @author Oliver Richers
 */
public final class Month extends DateItem<Month> {

	// ******************************************************************************** //
	// * standard functions                                                           * //
	// ******************************************************************************** //

	/**
	 * Returns the month with the specified absolute index. The month with the
	 * index 0 is January of the year 0.
	 *
	 * @param index
	 *            the absolute index of the month
	 * @return the month with the specified index
	 */
	public static Month get(int index) {

		return new Month(index);
	}

	/**
	 * Returns the absolute index of this month which is its distance from
	 * January of the year 0.
	 */
	@Override
	public int getAbsoluteIndex() {

		return m_index;
	}

	/**
	 * Returns the month in the specified distance from this month.
	 */
	@Override
	public Month getRelative(int distance) {

		return Month.get(m_index + distance);
	}

	// ******************************************************************************** //
	// * year functions                                                               * //
	// ******************************************************************************** //

	/**
	 * Returns the year containing this month.
	 */
	public Year getYear() {

		return Year.get(ISOCalendar.getYearWithMonth(m_index));
	}

	/**
	 * Returns the index of this month within its containing year.
	 *
	 * @return the relative index [1,12]
	 */
	public int getIndexWithinYear() {

		return ISOCalendar.getMonthIndexWithinYear(m_index);
	}

	// ******************************************************************************** //
	// * week functions                                                               * //
	// ******************************************************************************** //

	/**
	 * Returns a range over all week of this month. A week belongs to this month
	 * if its Thursday belongs to the month.
	 *
	 * @return range over all weeks that belong to this month
	 */
	public DateItemRange<Week> getWeeks() {

		int[] weeks = ISOCalendar.getFirstAndLastWeekOfMonth(m_index);
		return new DateItemRange<>(Week.get(weeks[0]), Week.get(weeks[1]));
	}

	// ******************************************************************************** //
	// * day functions                                                                * //
	// ******************************************************************************** //

	/**
	 * Returns a range over all days of this month.
	 *
	 * @return a range over all days
	 */
	public DateItemRange<Day> getDays() {

		int[] days = ISOCalendar.getFirstAndLastDayOfMonth(m_index);
		return new DateItemRange<>(Day.get(days[0]), Day.get(days[1]));
	}

	/**
	 * Returns first day in this month.
	 *
	 * @return a first day of month
	 */
	public Day getFirstDayInMonth() {

		int day = ISOCalendar.getFirstDayOfMonth(m_index);
		return Day.get(day);
	}

	/**
	 * Returns last day in this month.
	 *
	 * @return a last day of month
	 */
	public Day getLastDayInMonth() {

		int[] days = ISOCalendar.getFirstAndLastDayOfMonth(m_index);
		return Day.get(days[1]);
	}

	// ******************************************************************************** //
	// * misc functions                                                               * //
	// ******************************************************************************** //

	/**
	 * Returns the name of this month.
	 *
	 * @return the name of this month
	 */
	public MonthName getName() {

		return MonthName.get(getIndexWithinYear());
	}

	/**
	 * Returns a string in the format YYYY-MM.
	 */
	@Override
	public String toString() {

		return getYear() + "-" + Padding.padLeft("" + getIndexWithinYear(), '0', 2);
	}

	public String toMMString() {

		if (getIndexWithinYear() < 10) {
			return "0" + getIndexWithinYear();
		} else {
			return "" + getIndexWithinYear();
		}
	}

	public String toYYMMString() {

		return getYear().toYYString() + toMMString();
	}

	// ******************************************************************************** //
	// * private stuff                                                                * //
	// ******************************************************************************** //

	private Month(int index) {

		m_index = index;
	}

	private final int m_index; // absolute index of this month
}
