package com.softicar.platform.emf.attribute.field.daytime;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfDayTimeInput extends DomDayTimeInput implements IEmfInput<DayTime> {

	@Override
	public IEmfInput<DayTime> appendLabel(IDisplayString label) {

		//TODO this is ugly but works
		dayInput//
			.getChildren()
			.stream()
			.filter(DomTextInput.class::isInstance)
			.findFirst()
			.ifPresent(it -> it.setAttribute("required", "required"));
		dayInput.appendChild(createLabel(label));
		return this;
	}
}
