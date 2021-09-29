package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;

public class EmfStringDisplay extends DomDiv {

	public EmfStringDisplay(String text) {

		this();
		appendText(text);
	}

	public EmfStringDisplay(IDisplayString text) {

		this();
		appendText(text);
	}

	private EmfStringDisplay() {

		addCssClass(EmfCssClasses.EMF_STRING_DISPLAY);
	}
}
