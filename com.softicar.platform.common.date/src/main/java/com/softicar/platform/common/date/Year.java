package com.softicar.platform.common.date;

import com.softicar.platform.common.string.Padding;

/**
 * Represents a year.
 * <p>
 * Every year has an integral index that uniquely identifies it. The index of a
 * year can be positive or negative. This class assumes that the first year is
 * year zero!
 *
 * @author Oliver Richers
 */
public final class Year extends DateItem<Year> {

	// ******************************************************************************** //
	// * standard functions                                                           * //
	// ******************************************************************************** //

	/**
	 * Returns the year with the specified index.
	 *
	 * @param index
	 *            the absolute index of the year
	 * @return the year with the specified index
	 */
	public static Year get(int index) {

		return new Year(index);
	}

	/**
	 * Returns the current year.
	 *
	 * @return current year
	 */
	public static Year thisYear() {

		return Day.today().getYear();
	}

	/**
	 * Returns the absolute index of this year. The following holds true:
	 * Year.get(x).getAbsoluteIndex() == x
	 *
	 * @return the absolute index of this year
	 */
	@Override
	public int getAbsoluteIndex() {

		return m_index;
	}

	/**
	 * Returns the year in the specified distance from this year.
	 *
	 * @return the year with the specified relative index
	 */
	@Override
	public Year getRelative(int distance) {

		return Year.get(m_index + distance);
	}

	// ******************************************************************************** //
	// * month functions                                                              * //
	// ******************************************************************************** //

	/**
	 * Returns the specified month of this year.
	 *
	 * @param monthName
	 *            the name of the month
	 * @return the month within this year with the specified name
	 */
	public Month getMonth(MonthName monthName) {

		return Month.get(ISOCalendar.getMonthOfYear(m_index, monthName.getIndex()));
	}

	/**
	 * Returns a range over all months of this year.
	 *
	 * @return a range over all months
	 */
	public DateItemRange<Month> getMonths() {

		return new DateItemRange<>(getMonth(MonthName.JANUARY), getMonth(MonthName.DECEMBER));
	}

	// ******************************************************************************** //
	// * week functions                                                               * //
	// ******************************************************************************** //

	/**
	 * Returns a range over all weeks of this year. NOTE: A week always belongs
	 * to the year which contains the largest part of it as specified by the
	 * international standard.
	 *
	 * @return a range over all weeks
	 */
	public DateItemRange<Week> getWeeks() {

		int weeks[] = ISOCalendar.getFirstAndLastWeekOfYear(m_index);
		return new DateItemRange<>(Week.get(weeks[0]), Week.get(weeks[1]));
	}

	// ******************************************************************************** //
	// * day functions                                                                * //
	// ******************************************************************************** //

	/**
	 * Returns a range containing all days of this year.
	 *
	 * @return a range over all days
	 */
	public DateItemRange<Day> getDays() {

		int[] days = ISOCalendar.getFirstAndLastDayOfYear(m_index);
		return new DateItemRange<>(Day.get(days[0]), Day.get(days[1]));
	}

	// ******************************************************************************** //
	// * miscellaneous functions                                                      * //
	// ******************************************************************************** //

	/**
	 * Returns the type of the year.
	 *
	 * @return YearType.LEAP if this is a leap year, YearType.NON_LEAP otherwise
	 */
	public YearType getType() {

		return YearType.getForYear(m_index);
	}

	/**
	 * Returns a 4 digit string-representation of this year.
	 *
	 * @return string representing this year
	 */
	@Override
	public String toString() {

		return Padding.padLeft("" + m_index, '0', 4);
	}

	public String toYYString() {

		String tmp = "" + getAbsoluteIndex() % 100;
		return Padding.padLeft(tmp, '0', 2);
	}

	public static Integer getCurrentCentury() {

		return Integer.parseInt(new String("" + thisYear().getAbsoluteIndex()).substring(0, 2));
	}

	public static Year getInCurrentCentury(int YY) {

		return Year.get(YY + getCurrentCentury() * 100);
	}

	// ******************************************************************************** //
	// * private                                                                      * //
	// ******************************************************************************** //

	/**
	 * Private constructor, constructing a new year object from an absolute
	 * index.
	 *
	 * @param index
	 *            the absolute index of the year
	 */
	private Year(int index) {

		m_index = index;
	}

	private final int m_index; // absolute index of this year
}
