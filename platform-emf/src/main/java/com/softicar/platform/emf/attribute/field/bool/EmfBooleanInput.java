package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfBooleanInput extends DomCheckbox implements IEmfInput<Boolean> {

	@Override
	public Optional<Boolean> getValue() {

		return Optional.of(isChecked());
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		setChangeCallback(it -> callback.apply());
	}
}
