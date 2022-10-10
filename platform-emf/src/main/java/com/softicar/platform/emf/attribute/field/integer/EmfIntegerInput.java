package com.softicar.platform.emf.attribute.field.integer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfIntegerInput extends DomIntegerInput implements IEmfInput<Integer> {

	@Override
	public IEmfInput<Integer> appendLabel(IDisplayString label) {

		input.setRequired(true);
		appendChild(createLabel(label));
		return this;
	}
}
