package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfBooleanInput extends DomCheckbox implements IEmfInput<Boolean> {

	public EmfBooleanInput(boolean checked) {

		super(checked);
	}

	@Override
	public IEmfInput<Boolean> appendLabel(IDisplayString label) {

		setLabel(label);
		return this;
	}
}
