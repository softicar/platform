package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;

public class EmfPasswordDisplay extends DomDiv {

	public EmfPasswordDisplay(String text) {

		this();
		if (text != null && !text.isEmpty()) {
			appendText("••••••••");
		}
	}

	public EmfPasswordDisplay(IDisplayString text) {

		this();
		if (text != null && !text.toString().isEmpty()) {
			appendText("••••••••");
		}
	}

	public EmfPasswordDisplay() {

		addCssClass(EmfCssClasses.EMF_STRING_DISPLAY);
	}
}
