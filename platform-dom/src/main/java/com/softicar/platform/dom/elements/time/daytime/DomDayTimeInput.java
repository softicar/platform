package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.time.DomTimeInput;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import java.util.Optional;

public class DomDayTimeInput extends DomBar {

	private final DomDayInput dayInput;
	private final DomTimeInput timeInput;

	public DomDayTimeInput() {

		this(DayTime.now());
	}

	public DomDayTimeInput(DayTime dayTime) {

		this.dayInput = new DomDayInput(dayTime != null? dayTime.getDay() : null);
		this.timeInput = new DomTimeInput(dayTime != null? dayTime.getTime() : null);
		appendChildren(dayInput, timeInput);
		addCssClass(DomElementsCssClasses.DOM_DAY_TIME_INPUT);
	}

	public DomDayTimeInput disableSeconds() {

		timeInput.disableSeconds();
		return this;
	}

	// TODO remove this method, it reveals internal details
	public DomDayInput getDayInput() {

		return dayInput;
	}

	public void setDayTime(DayTime dayTime) {

		this.dayInput.setDay(dayTime != null? dayTime.getDay() : null);
		this.timeInput.setTime(dayTime != null? dayTime.getTime() : null);
	}

	/**
	 * Parses the value text into a {@link DayTime}.
	 * <p>
	 * If the value text is empty or blank, an empty {@link Optional} is
	 * returned. Otherwise, the value text is parsed into a {@link DayTime}. If
	 * parsing failed, an exception is thrown.
	 *
	 * @return the optionally entered {@link DayTime}
	 * @throws SofticarUserException
	 *             if the non-blank value text could not be parsed
	 */
	public Optional<DayTime> getDayTimeOrThrowIfInvalid() {

		Optional<Day> day = dayInput.getDayOrThrowIfInvalid();
		Optional<Time> time = timeInput.getTimeOrThrowIfInvalid();

		if (day.isPresent() && time.isPresent()) {
			return Optional.of(new DayTime(day.get(), time.get()));
		} else if (!day.isPresent() && !time.isPresent()) {
			return Optional.empty();
		} else {
			if (time.isPresent()) {
				throw new SofticarUserException(CommonDateI18n.MISSING_DATE_SPECIFICATION);
			} else {
				throw new SofticarUserException(CommonDateI18n.MISSING_TIME_SPECIFICATION);
			}
		}
	}

	/**
	 * @deprecated use {@link #getDayTimeOrThrowIfInvalid()}
	 */
	@Deprecated
	public DayTime getDayTime() {

		Day dayOrNull = dayInput.getDayOrNull();

		Time timeOrNull;
		try {
			timeOrNull = timeInput.getTime();
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
