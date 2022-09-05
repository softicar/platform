package com.softicar.platform.dom.elements.text;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

/**
 * Displays a multi-line String.
 */
public class DomMultilineStringDisplay extends DomDiv {

	public DomMultilineStringDisplay(String value) {

		addCssClass(DomCssClasses.DOM_MULTILINE_STRING_DISPLAY);
		appendText(value);
	}
}
