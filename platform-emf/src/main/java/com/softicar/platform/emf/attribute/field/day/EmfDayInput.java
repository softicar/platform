package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfDayInput extends DomDayInput implements IEmfInput<Day> {

	@Override
	public void setValueAndHandleChangeCallback(Day value) {

		setValue(value);
		applyCallback();
	}
}
