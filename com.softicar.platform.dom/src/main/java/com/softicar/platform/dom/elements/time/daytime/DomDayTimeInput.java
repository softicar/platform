package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.time.DomTimeInput;
import com.softicar.platform.dom.elements.time.day.DomDayInput;

public class DomDayTimeInput extends DomBar {

	private final DomDayInput dayInput;
	private final DomTimeInput timeInput;

	public DomDayTimeInput() {

		this.dayInput = new DomDayInput();
		this.timeInput = new DomTimeInput();
		appendChildren(dayInput, timeInput);
		addCssClass(DomElementsCssClasses.DOM_DAY_TIME_INPUT);
	}

	public DomDayTimeInput disableSeconds() {

		timeInput.disableSeconds();
		return this;
	}

	public DomDayInput getDayInput() {

		return dayInput;
	}

	public DomTimeInput getTimeInput() {

		return timeInput;
	}

	public void setDayTime(DayTime dayTime) {

		this.dayInput.setDay(dayTime != null? dayTime.getDay() : null);
		this.timeInput.setTime(dayTime != null? dayTime.getTime() : null);
	}

	public DayTime getDayTime() {

		Day dayOrNull = getDayInput().getDayOrNull();

		Time timeOrNull;
		try {
			timeOrNull = getTimeInput().getTime();
		} catch (Exception exception) {
			DevNull.swallow(exception);
			timeOrNull = null;
		}

		if (dayOrNull != null && timeOrNull != null) {
			int year = dayOrNull.getYear().getAbsoluteIndex();
			int month = dayOrNull.getMonth().getIndexWithinYear();
			int day = dayOrNull.getIndexWithinMonth();
			int hour = timeOrNull.getHour();
			int minute = timeOrNull.getMinute();
			int second = timeOrNull.getSecond();

			return DayTime.fromYMD_HMS(year, month, day, hour, minute, second);
		} else {
			return null;
		}
	}

	public void clear() {

		dayInput.clear();
		timeInput.clear();
	}
}
