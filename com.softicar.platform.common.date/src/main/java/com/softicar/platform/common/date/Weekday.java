package com.softicar.platform.common.date;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;

/**
 * Enumeration of the seven days of a week.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public enum Weekday implements IDisplayable {

	MONDAY(CommonDateI18n.MONDAY, CommonWeekdayAbbreviationI18n.MO),
	TUESDAY(CommonDateI18n.TUESDAY, CommonWeekdayAbbreviationI18n.TU),
	WEDNESDAY(CommonDateI18n.WEDNESDAY, CommonWeekdayAbbreviationI18n.WE),
	THURSDAY(CommonDateI18n.THURSDAY, CommonWeekdayAbbreviationI18n.TH),
	FRIDAY(CommonDateI18n.FRIDAY, CommonWeekdayAbbreviationI18n.FR),
	SATURDAY(CommonDateI18n.SATURDAY, CommonWeekdayAbbreviationI18n.SA),
	SUNDAY(CommonDateI18n.SUNDAY, CommonWeekdayAbbreviationI18n.SU);

	private IDisplayString name;
	private IDisplayString shortName;

	private Weekday(IDisplayString name, IDisplayString shortName) {

		this.name = name;
		this.shortName = shortName;
	}

	public int getIndex() {

		return ordinal() + 1;
	}

	@Override
	public IDisplayString toDisplay() {

		return name;
	}

	public IDisplayString toShortDisplay() {

		return shortName;
	}

	public int getDistance(Weekday other) {

		return other.getIndex() - getIndex();
	}

	public Weekday getNext() {

		return this == SUNDAY? MONDAY : get(getIndex() + 1);
	}

	public Weekday getPrevious() {

		return this == MONDAY? SUNDAY : get(getIndex() - 1);
	}

	public static Weekday get(int index) {

		return values()[index - 1];
	}
}
