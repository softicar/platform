package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.DomDiv;

public class EmfDayDisplay extends DomDiv {

	public EmfDayDisplay(Day value) {

		appendChild(value.toHumanString());
	}
}
