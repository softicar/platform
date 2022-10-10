package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.input.AbstractDomTextAreaDiv;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfMultilineStringInput extends AbstractDomTextAreaDiv implements IEmfInput<String> {

	@Override
	public IEmfInput<String> appendLabel(IDisplayString label) {

		textArea.setRequired(true);
		appendChild(createLabel(label));
		return this;
	}
}
