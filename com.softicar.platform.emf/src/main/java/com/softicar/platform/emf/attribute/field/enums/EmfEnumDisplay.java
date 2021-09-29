package com.softicar.platform.emf.attribute.field.enums;

import com.softicar.platform.dom.elements.DomDiv;

public class EmfEnumDisplay<E extends Enum<E>> extends DomDiv {

	public EmfEnumDisplay(E value) {

		appendText(value.name());
	}
}
