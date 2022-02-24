package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import java.util.Optional;

public class EmfDayInput extends AbstractEmfInputDiv<Day> {

	private final DomDayInput dayInput;

	public EmfDayInput() {

		this.dayInput = new DomDayInput();

		appendChild(dayInput);
	}

	@Override
	public Optional<Day> getValue() {

		return dayInput.getValue();
	}

	@Override
	public void setValue(Day value) {

		dayInput.setValue(value);
	}

	@Override
	public IDomElement setEnabled(boolean enabled) {

		dayInput.setEnabledRecursively(enabled);
		return this;
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		dayInput.setCallback(callback);
	}
}
