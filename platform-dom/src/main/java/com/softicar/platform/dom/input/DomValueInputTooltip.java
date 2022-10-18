package com.softicar.platform.dom.input;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;

/**
 * An instantly-appearing pseudo tooltip, for use in value input elements which
 * contain a textual input field.
 *
 * @author Alexander Schmidt
 */
public class DomValueInputTooltip extends DomDiv {

	public DomValueInputTooltip(String text) {

		addCssClass(DomCssClasses.DOM_VALUE_INPUT_TOOLTIP);
		appendText(text);
	}
}
