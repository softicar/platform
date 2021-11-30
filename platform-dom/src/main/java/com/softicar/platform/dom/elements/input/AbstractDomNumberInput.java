package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.input.DomTextInput;

/**
 * Base class of all number input elements.
 * <p>
 * One purpose of this class is to align the text of this input to the right.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomNumberInput extends DomTextInput {

	protected AbstractDomNumberInput() {

		//nothing to do
	}

	protected AbstractDomNumberInput(String valueText) {

		super(valueText);
	}
}
