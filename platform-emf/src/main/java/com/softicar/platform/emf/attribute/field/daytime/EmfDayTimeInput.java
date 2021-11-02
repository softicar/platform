package com.softicar.platform.emf.attribute.field.daytime;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;

public class EmfDayTimeInput extends AbstractEmfInputDiv<DayTime> {

	private final DomDayTimeInput dayTimeInput;

	public EmfDayTimeInput() {

		this.dayTimeInput = new DomDayTimeInput();

		appendChild(dayTimeInput);
	}

	@Override
	public DayTime getValueOrThrow() {

		return dayTimeInput.getDayTime();
	}

	@Override
	public void setValue(DayTime value) {

		dayTimeInput.setDayTime(value);
	}
}
