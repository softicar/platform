package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.time.DomTimeInput;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.dom.input.AbstractDomValueInput;
import java.util.Optional;

public class DomDayTimeInput extends AbstractDomValueInput<DayTime> {

	private final DomDayInput dayInput;
	private final DomTimeInput timeInput;

	public DomDayTimeInput() {

		this.dayInput = new DomDayInput();
		this.dayInput.addChangeCallback(this::executeChangeCallbacks);
		this.timeInput = new DomTimeInput();
		this.timeInput.addChangeCallback(this::executeChangeCallbacks);

		addCssClass(DomElementsCssClasses.DOM_DAY_TIME_INPUT);
		appendChildren(dayInput, timeInput);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		this.dayInput.setDisabled(disabled);
		this.timeInput.setDisabled(disabled);
	}

	@Override
	public void setValue(DayTime dayTime) {

		this.dayInput.setValue(dayTime != null? dayTime.getDay() : null);
		this.timeInput.setValue(dayTime != null? dayTime.getTime() : null);
	}

	@Override
	public Optional<DayTime> getValue() {

		Optional<Day> day = dayInput.getValue();
		Optional<Time> time = timeInput.getValue();

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
}
