package com.softicar.platform.emf.attribute.field.longs;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.input.DomLongInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfLongInput extends DomLongInput implements IEmfInput<Long> {

	@Override
	public IEmfInput<Long> appendLabel(IDisplayString label) {

		input.setRequired(true);
		appendChild(createLabel(label));
		return this;
	}
}
