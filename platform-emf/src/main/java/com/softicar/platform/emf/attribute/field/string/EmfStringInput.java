package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.input.AbstractDomTextInputDiv;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfStringInput extends AbstractDomTextInputDiv implements IEmfInput<String> {

	@Override
	public IEmfInput<String> appendLabel(IDisplayString label) {

		input.setRequired(true);
		appendChild(createLabel(label));
		return this;
	}
}
