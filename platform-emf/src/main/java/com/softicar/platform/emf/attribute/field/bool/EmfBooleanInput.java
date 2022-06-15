package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfBooleanInput extends DomCheckbox implements IEmfInput<Boolean> {

	public EmfBooleanInput(boolean checked) {

		super(checked);
	}
}
