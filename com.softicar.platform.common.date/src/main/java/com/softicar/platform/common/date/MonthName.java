package com.softicar.platform.common.date;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

/**
 * Enumeration of the twelve months of a year.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public enum MonthName implements IDisplayable {

	JANUARY(CommonDateI18n.JANUARY, 31, 1),
	FEBRUARY(CommonDateI18n.FEBRUARY, 28, 1 + 31),
	MARCH(CommonDateI18n.MARCH, 31, 1 + 31 + 28),
	APRIL(CommonDateI18n.APRIL, 30, 1 + 31 + 28 + 31),
	MAY(CommonDateI18n.MAY, 31, 1 + 31 + 28 + 31 + 30),
	JUNE(CommonDateI18n.JUNE, 30, 1 + 31 + 28 + 31 + 30 + 31),
	JULY(CommonDateI18n.JULY, 31, 1 + 31 + 28 + 31 + 30 + 31 + 30),
	AUGUST(CommonDateI18n.AUGUST, 31, 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31),
	SEPTEMBER(CommonDateI18n.SEPTEMBER, 30, 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31),
	OCTOBER(CommonDateI18n.OCTOBER, 31, 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30),
	NOVEMBER(CommonDateI18n.NOVEMBER, 30, 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31),
	DECEMBER(CommonDateI18n.DECEMBER, 31, 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30);

	private final IDisplayString name;
	private final int size;
	private final int start;

	private MonthName(IDisplayString name, int size, int start) {

		this.name = name;
		this.size = size;
		this.start = start;
	}

	/**
	 * Returns the index of the month in the range [1,12].
	 *
	 * @return the index staring from 1
	 */
	public int getIndex() {

		return ordinal() + 1;
	}

	/**
	 * Returns the name of this month.
	 *
	 * @return the name of this month
	 */
	@Override
	public IDisplayString toDisplay() {

		return name;
	}

	/**
	 * Returns the day count of the month for the specified type of year.
	 *
	 * @return month size in days in the range [29,31] for a leap year and
	 *         [28,31] for a non-leap year
	 */
	public int getDayCount(YearType yearType) {

		if (yearType == YearType.NON_LEAP) {
			return size;
		} else {
			return this != FEBRUARY? size : size + 1;
		}
	}

	/**
	 * Returns the index of the first day of the month for the specified type of
	 * year.
	 *
	 * @return the start of the month in the range [1,336] for a leap year and
	 *         [1,335] for a non leap year.
	 */
	public int getStart(YearType yearType) {

		if (yearType == YearType.NON_LEAP) {
			return start;
		} else {
			return compareTo(MARCH) < 0? start : start + 1;
		}
	}

	/**
	 * Returns the next month name following this month name.
	 *
	 * @return the next month name
	 */
	public MonthName getNext() {

		return values()[(ordinal() + 1) % 12];
	}

	/**
	 * Returns the previous month name if this is not the first month name.
	 *
	 * @return the previous month name
	 */
	public MonthName getPrevious() {

		return values()[(ordinal() + 11) % 12];
	}

	public MonthName getRelative(int i) {

		return values()[(ordinal() + i) % 12];
	}

	/**
	 * Returns the month name with the specified index.
	 *
	 * @param index
	 *            of the month within a year (in the range [1,12])
	 * @return the month name with the specified index
	 */
	public static MonthName get(int index) {

		return values()[index - 1];
	}
}
