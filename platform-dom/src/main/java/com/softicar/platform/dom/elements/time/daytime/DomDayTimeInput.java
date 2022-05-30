package com.softicar.platform.dom.elements.time.daytime;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.CommonDateI18n;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.time.DomTimeInput;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.dom.input.IDomValueInput;
import java.util.Optional;

public class DomDayTimeInput extends DomBar implements IDomValueInput<DayTime> {

	private final DomDayInput dayInput;
	private final DomTimeInput timeInput;
	private boolean disabled;

	public DomDayTimeInput() {

		this.dayInput = appendChild(new DomDayInput());
		this.timeInput = appendChild(new DomTimeInput());
		this.disabled = false;

		addCssClass(DomElementsCssClasses.DOM_DAY_TIME_INPUT);
	}

	@Override
	public DomDayTimeInput setDisabled(boolean disabled) {

		this.dayInput.setDisabled(disabled);
		this.timeInput.setDisabled(disabled);
		this.disabled = disabled;
		return this;
	}

	@Override
	public boolean isDisabled() {

		return disabled;
	}

	@Override
	public final DomDayTimeInput setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public final boolean isEnabled() {

		return !isDisabled();
	}

	// TODO remove this method, it reveals internal details
	public DomDayInput getDayInput() {

		return dayInput;
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
