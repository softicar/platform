package com.softicar.platform.emf.attribute.field.longs;

import com.softicar.platform.dom.elements.DomDiv;

public class EmfLongDisplay extends DomDiv {

	public EmfLongDisplay(Long value) {

		appendText(value + "");
	}
}
