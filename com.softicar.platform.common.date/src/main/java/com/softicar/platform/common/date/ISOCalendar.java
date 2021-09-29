package com.softicar.platform.common.date;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Collection of date algorithms for years, months, weeks and days.
 * <p>
 * This class contains only static functions and static data members. It depends
 * on almost no other class.
 * <p>
 * This uses a proleptic Gregorian calendar and is thus compatible to the MySQL
 * function TO_DAYS. This means that this function assumes the Gregorian
 * Calendar was used since the beginning of the universe.
 * <p>
 * See the MySQL documentation for more detail.
 * 
 * @author Oliver Richers
 */
public class ISOCalendar {

	public static final int SECONDS_PER_DAY = 24 * 3600;
	public static final Locale LOCALE = Locale.GERMANY;
	public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Berlin");
	static {
		setDefaultTimeZone();
	}

	public static void setDefaultTimeZone() {

		TimeZone.setDefault(TIME_ZONE);
	}

	// ******************************************************************************** //
	// * get{Year,Month,Week}With{Month,Week,Day}(int)                                * //
	// ******************************************************************************** //

	/**
	 * Returns the year containing the specified month.
	 * 
	 * @param month
	 *            the absolute index of the month
	 * @return the absolute index of the year
	 */
	public static int getYearWithMonth(int month) {

		return month / MONTHS_PER_YEAR;
	}

	/**
	 * Returns the absolute index of the year containing the specified week.
	 * 
	 * @param week
	 *            the absolute index of the week
	 * @return the absolute index of the year
	 */
	public static int getYearWithWeek(int week) {

		int thursday = getFirstDayOfWeek(week) + Weekday.THURSDAY.getIndex() - 1;
		return getYearWithDay(thursday);
	}

	/**
	 * Returns the absolute index of the year containing the specified day.
	 * 
	 * @param day
	 *            the absolute index of the day
	 * @return the absolute index of the year
	 */
	public static int getYearWithDay(int day) {

		// approximate
		int year = day / 365;

		// year is greater than or equal to the actual year
		while (day < getFirstDayOfYear(year)) {
			--year;
		}

		return year;
	}

	/**
	 * Returns the month containing the specified week.
	 * 
	 * @param week
	 *            the absolute index of the week
	 * @return the absolute index of the month
	 */
	public static int getMonthWithWeek(int week) {

		int thursday = getFirstDayOfWeek(week) + Weekday.THURSDAY.getIndex() - 1;
		return getMonthWithDay(thursday);
	}

	// ******************************************************************************** //
	// * getFirst{Month,Week,Day}Of{Year,Month,Week}(int)                             * //
	// ******************************************************************************** //

	/**
	 * Returns the first day of the specified year.
	 * 
	 * @param year
	 *            the absolute index of the year
	 * @return the absolute index of the first day
	 */
	public static int getFirstDayOfYear(int year) {

		return 365 * year + (year + 3) / 4 - (year + 99) / 100 + (year + 399) / 400;
	}

	/**
	 * Returns the first week of the specified year.
	 * 
	 * @param year
	 *            the absolute index of the year
	 * @return the absolute index of the week
	 */
	public static int getFirstWeekOfYear(int year) {

		int firstDay = getFirstDayOfYear(year);
		int week = getWeekWithDay(firstDay);
		return getYearWithWeek(week) == year? week : week + 1;
	}

	/**
	 * Returns the first day of the specified month.
	 * 
	 * @param month
	 *            the absolute index of the month
	 * @return the absolute index of the first day of the month
	 */
	public static int getFirstDayOfMonth(int month) {

		int indexWithinYear = getMonthIndexWithinYear(month);
		MonthName monthName = MonthName.get(indexWithinYear);

		int year = getYearWithMonth(month);
		YearType yearType = YearType.getForYear(year);

		return getFirstDayOfYear(year) + monthName.getStart(yearType) - 1;
	}

	/**
	 * Returns the first week of the specified month.
	 * 
	 * @param month
	 *            the absolute index of the month
	 * @return the absolute index of the week
	 */
	public static int getFirstWeekOfMonth(int month) {

		int firstDay = getFirstDayOfMonth(month);
		int week = getWeekWithDay(firstDay);
		return getMonthWithWeek(week) == month? week : week + 1;
	}

	/**
	 * Returns the first day of the specified week.
	 * 
	 * @param week
	 *            the absolute index of the week
	 * @return the absolute index of the first day of the week
	 */
	public static int getFirstDayOfWeek(int week) {

		return week * DAYS_PER_WEEK + FIRST_MONDAY;
	}

	// ******************************************************************************** //
	// * getFirstAndLast{Month,Week,Day}Of{Year,Month,Week}(int)                      * //
	// ******************************************************************************** //

	/**
	 * Returns the first and the last day of the specified year.
	 * 
	 * @param year
	 *            the absolute index of the year
	 * @return the absolute indices of the days
	 */
	public static int[] getFirstAndLastDayOfYear(int year) {

		YearType yearType = YearType.getForYear(year);

		int[] days = new int[2];
		days[0] = getFirstDayOfYear(year);
		days[1] = days[0] + yearType.getDayCount() - 1;
		return days;
	}

	public static int[] getFirstAndLastWeekOfYear(int year) {

		int[] weeks = new int[2];
		weeks[0] = getFirstWeekOfYear(year);
		weeks[1] = getFirstWeekOfYear(year + 1) - 1;
		return weeks;
	}

	/**
	 * Returns the first and last day of the specified month.
	 * 
	 * @param month
	 *            the absolute index of the month
	 * @return the absolute indices of the days
	 */
	public static int[] getFirstAndLastDayOfMonth(int month) {

		int indexWithinYear = getMonthIndexWithinYear(month);
		MonthName monthName = MonthName.get(indexWithinYear);

		int year = getYearWithMonth(month);
		YearType yearType = YearType.getForYear(year);

		int[] result = new int[2];
		result[0] = getFirstDayOfYear(year) + monthName.getStart(yearType) - 1;
		result[1] = result[0] + monthName.getDayCount(yearType) - 1;
		return result;
	}

	public static int[] getFirstAndLastWeekOfMonth(int month) {

		int[] weeks = new int[2];
		weeks[0] = getFirstWeekOfMonth(month);
		weeks[1] = getFirstWeekOfMonth(month + 1) - 1;
		return weeks;
	}

	/**
	 * Returns the first and last day of the specified week.
	 * 
	 * @param week
	 *            the absolute index of the week
	 * @return the absolute indices of the days
	 */
	public static int[] getFirstAndLastDayOfWeek(int week) {

		int[] days = new int[2];
		days[0] = getFirstDayOfWeek(week);
		days[1] = days[0] + DAYS_PER_WEEK - 1;
		return days;
	}

	// ******************************************************************************** //
	// * getDayIndexWithin{Year,Month,Week}(int)                                      * //
	// * getDayOf{Year,Month,Week}(int, int)                                          * //
	// ******************************************************************************** //

	/**
	 * Returns the index of the specified day within its year.
	 * 
	 * @param day
	 *            the absolute index of the day
	 * @return the relative index of the day [1,366]
	 */
	public static int getDayIndexWithinYear(int day) {

		int year = getYearWithDay(day);
		int firstDay = getFirstDayOfYear(year);
		return day - firstDay + 1;
	}

	/**
	 * Returns the absolute index of the specified day.
	 * 
	 * @param year
	 *            the absolute index of the year
	 * @param day
	 *            the index of the day within the year [1,366]
	 * @return the absolute index of the day
	 */
	public static int getDayOfYear(int year, int day) {

		return getFirstDayOfYear(year) + day - 1;
	}

	/**
	 * Return the index of the specified day within its containing month.
	 * 
	 * @param day
	 *            the absolute index of the day
	 * @return the index of the day within its month
	 */
	public static int getDayIndexWithinMonth(int day) {

		int month = getMonthWithDay(day);
		int firstDay = getFirstDayOfMonth(month);
		return day - firstDay + 1;
	}

	/**
	 * Returns the index of the specified day within its containing week.
	 * 
	 * @param day
	 *            the absolute index of the day
	 * @return the index of the day within its week [1,7]
	 */
	public static int getDayIndexWithinWeek(int day) {

		// compute the distance to some monday
		int result = (day - FIRST_MONDAY) % DAYS_PER_WEEK;

		// the operator% returns the remainder which is in the range [-6,6]
		// e.g. '-16 % 7 == -2' but we need '-16 mod 7 == 5'
		if (result < 0) {
			result += DAYS_PER_WEEK;
		}

		// week days go from 1 to 7
		return result + 1;
	}

	/**
	 * Returns the specified day.
	 * 
	 * @param month
	 *            the absolute index of the month
	 * @param day
	 *            the index of the day within the month [1,31]
	 * @return the absolute index of the day
	 */
	public static int getDayOfMonth(int month, int day) {

		return getFirstDayOfMonth(month) + day - 1;
	}

	// ******************************************************************************** //
	// * month                                                                        * //
	// ******************************************************************************** //

	/**
	 * Returns the month containing the specified day.
	 * 
	 * @param day
	 *            the absolute index of the day
	 * @return the absolute index of the month
	 */
	public static int getMonthWithDay(int day) {

		// get year of the day and its type
		int year = getYearWithDay(day);
		YearType yearType = YearType.getForYear(year);

		// get month name
		int indexWithYear = getDayIndexWithinYear(day);
		MonthName monthName = yearType == YearType.NON_LEAP? DAY_TO_MONTH_MAP1[indexWithYear - 1] : DAY_TO_MONTH_MAP2[indexWithYear - 1];

		return getMonthOfYear(year, monthName.getIndex());
	}

	/**
	 * Returns the index of the month within its year.
	 * 
	 * @param month
	 *            the absolute month index
	 * @return the relative month index
	 */
	public static int getMonthIndexWithinYear(int month) {

		return month % MONTHS_PER_YEAR + 1;
	}

	/**
	 * Returns the absolute index of the specified month.
	 * 
	 * @param year
	 *            the absolute index of the year
	 * @param month
	 *            the relative index of the month wihtin the year [1,12]
	 * @return the absolute index of the month
	 */
	public static int getMonthOfYear(int year, int month) {

		return year * MONTHS_PER_YEAR + month - 1;
	}

	// ******************************************************************************** //
	// * week                                                                         * //
	// ******************************************************************************** //

	/**
	 * Returns the absolute index of the week containing the specified day.
	 * 
	 * @param day
	 *            some day of the returned week
	 * @return the absolute index of the week
	 */
	public static int getWeekWithDay(int day) {

		if (day >= FIRST_MONDAY) {
			return (day - FIRST_MONDAY) / DAYS_PER_WEEK;
		} else {
			return (day - FIRST_MONDAY + 1) / DAYS_PER_WEEK - 1;
		}
	}

	/**
	 * Returns the index of the specified week within its year.
	 * 
	 * @param week
	 *            the absolute index of the week
	 * @return the index of the week [1,53]
	 */
	public static int getWeekIndexWithinYear(int week) {

		int year = getYearWithWeek(week);
		int firstWeek = getFirstWeekOfYear(year);
		return week - firstWeek + 1;
	}

	/**
	 * Returns the index of the specified week within its month.
	 * 
	 * @param week
	 *            the absolute index of the week
	 * @return the index of the week [1,5]
	 */
	public static int getWeekIndexWithinMonth(int week) {

		int month = getMonthWithWeek(week);
		int firstWeek = getFirstWeekOfMonth(month);
		return week - firstWeek + 1;
	}

	// ******************************************************************************** //
	// * misc                                                                         * //
	// ******************************************************************************** //

	public static Calendar getNewCalendar() {

		return Calendar.getInstance(TIME_ZONE, LOCALE);
	}

	public static boolean isValidYMD(int year, int month, int day) {

		if (day < 1 || month < 1 || month > 12) {
			return false;
		}

		YearType yearType = YearType.getForYear(year);
		MonthName monthName = MonthName.get(month);
		return day <= monthName.getDayCount(yearType);
	}

	// ******************************************************************************** //
	// * private                                                                      * //
	// ******************************************************************************** //

	private static final int MONTHS_PER_YEAR = 12; // number of months in a year
	private static final int DAYS_PER_WEEK = 7;   // number of days in a week
	private static final int FIRST_MONDAY = 2;    // the first Monday of the year 0
	private static final MonthName[] DAY_TO_MONTH_MAP1 = new MonthName[365];
	private static final MonthName[] DAY_TO_MONTH_MAP2 = new MonthName[366];
	static {
		int i1 = 0;
		int i2 = 0;
		for (MonthName monthName: MonthName.values()) {
			for (int j = 0; j != monthName.getDayCount(YearType.NON_LEAP); ++j) {
				DAY_TO_MONTH_MAP1[i1++] = monthName;
			}
			for (int j = 0; j != monthName.getDayCount(YearType.LEAP); ++j) {
				DAY_TO_MONTH_MAP2[i2++] = monthName;
			}
		}
	}
}
