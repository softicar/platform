package com.softicar.platform.common.date;

/**
 * Enumeration of the types of years.
 * <p>
 * There are only two types of years: leap and non-leap years, where a leap year
 * contains an additional 29th day in February.
 * 
 * @author Oliver Richers
 */
public enum YearType {
	LEAP,
	NON_LEAP;

	/**
	 * Returns the type of the year with the specified index.
	 * 
	 * @param year
	 *            the absolute index of the year
	 * @return the type of the year
	 */
	public static YearType getForYear(int year) {

		boolean isLeap = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
		return isLeap? LEAP : NON_LEAP;
	}

	/**
	 * Returns the day count of this type of year.
	 * 
	 * @return the number of days
	 */
	public int getDayCount() {

		return this == LEAP? 366 : 365;
	}
}
