package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfCssClasses;

/**
 * Displays a multi-line String.
 */
public class EmfMultilineStringDisplay extends DomDiv {

	public EmfMultilineStringDisplay(String value) {

		addCssClass(EmfCssClasses.EMF_MULTILINE_STRING_DISPLAY);
		appendText(value);
	}
}
