package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfMultilineStringInput extends DomTextArea implements IEmfInput<String> {

	public EmfMultilineStringInput() {

		setRowCount(5);
	}

	@Override
	public String getValueOrThrow() throws DomInputException {

		return getValue();
	}
}
