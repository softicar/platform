package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfStringInput extends DomTextInput implements IEmfInput<String> {

	@Override
	public void setValue(String value) {

		setInputText(value);
	}

	@Override
	public Optional<String> getValue() {

		return Optional.of(getInputText());
	}
}
