package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfStringInput extends DomTextInput implements IEmfInput<String> {

	@Override
	public String getValueOrThrow() throws EmfInputException {

		return getValue();
	}
}
