package com.softicar.platform.emf.attribute.field.time;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.time.DomTimeInput;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;

public class EmfTimeInput extends AbstractEmfInputDiv<Time> {

	private final DomTimeInput timeInput;

	public EmfTimeInput() {

		this.timeInput = new DomTimeInput();

		appendChild(timeInput);
	}

	@Override
	public Time getValueOrThrow() {

		return timeInput.retrieveValue().orElse(null);
	}

	@Override
	public void setValue(Time value) {

		timeInput.setTime(value);
	}
}
