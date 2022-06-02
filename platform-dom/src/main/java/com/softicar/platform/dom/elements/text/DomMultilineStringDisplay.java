package com.softicar.platform.dom.elements.text;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;

/**
 * Displays a multi-line String.
 */
public class DomMultilineStringDisplay extends DomDiv {

	public DomMultilineStringDisplay(String value) {

		addCssClass(DomElementsCssClasses.DOM_MULTILINE_STRING_DISPLAY);
		appendText(value);
	}
}
