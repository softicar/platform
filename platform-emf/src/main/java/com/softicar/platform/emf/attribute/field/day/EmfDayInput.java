package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfDayInput extends DomDayInput implements IEmfInput<Day> {

	@Override
	public IEmfInput<Day> appendLabel(IDisplayString label) {

		dayInput.setRequired(true);
		appendChild(createLabel(label));
		return this;
	}
}
