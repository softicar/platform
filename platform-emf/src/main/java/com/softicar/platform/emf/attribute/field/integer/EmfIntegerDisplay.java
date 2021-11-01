package com.softicar.platform.emf.attribute.field.integer;

import com.softicar.platform.dom.elements.DomDiv;

public class EmfIntegerDisplay extends DomDiv {

	public EmfIntegerDisplay(Integer value) {

		appendText(value + "");
	}
}
