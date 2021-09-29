package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.styles.CssTextAlign;

/**
 * Base class of all number input elements.
 * <p>
 * One purpose of this class is to align the text of this input to the right.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomNumberInput extends DomTextInput {

	protected AbstractDomNumberInput() {

		addCssClass(DomElementsCssClasses.DOM_NUMBER_INPUT);
	}

	protected AbstractDomNumberInput(String valueText) {

		super(valueText);
		addCssClass(DomElementsCssClasses.DOM_NUMBER_INPUT);
		setStyle(CssTextAlign.RIGHT);
	}
}
