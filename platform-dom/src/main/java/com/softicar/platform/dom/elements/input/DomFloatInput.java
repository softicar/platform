package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} for {@link Float} values.
 *
 * @author Oliver Richers
 */
public class DomFloatInput extends DomFloatingPointInput<Float> {

	public DomFloatInput() {

		super(Float::valueOf);
	}
}
