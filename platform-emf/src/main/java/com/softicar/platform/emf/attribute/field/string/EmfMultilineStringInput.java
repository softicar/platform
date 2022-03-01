package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfMultilineStringInput extends DomTextArea implements IEmfInput<String> {

	public EmfMultilineStringInput() {

		setRowCount(5);
	}

	@Override
	public void setValue(String value) {

		setInputText(value);
	}

	@Override
	public Optional<String> getValue() {

		return Optional.of(getInputText());
	}
}
