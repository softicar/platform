package com.softicar.platform.emf.attribute.field.time;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.dom.elements.DomDiv;

public class EmfTimeDisplay extends DomDiv {

	public EmfTimeDisplay(Time value) {

		appendChild(value.toString());
	}
}
