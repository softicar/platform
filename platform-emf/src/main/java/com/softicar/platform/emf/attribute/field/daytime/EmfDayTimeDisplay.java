package com.softicar.platform.emf.attribute.field.daytime;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Objects;
import java.util.function.Function;

public class EmfDayTimeDisplay extends DomDiv {

	public EmfDayTimeDisplay(DayTime value) {

		this(value, DayTime::toHumanString);
	}

	public EmfDayTimeDisplay(DayTime value, Function<DayTime, String> displayFunction) {

		Objects.requireNonNull(displayFunction);
		appendChild(displayFunction.apply(value));
	}
}
