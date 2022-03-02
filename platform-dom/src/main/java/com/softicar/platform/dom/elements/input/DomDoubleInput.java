package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} for {@link Double} values.
 *
 * @author Oliver Richers
 */
public class DomDoubleInput extends DomFloatingPointInput<Double> {

	public DomDoubleInput() {

		super(Double::valueOf);
	}
}
