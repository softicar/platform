package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.attribute.input.AbstractEmfChangeListeningInputDiv;
import java.util.Optional;

public class EmfBooleanInput extends AbstractEmfChangeListeningInputDiv<Boolean> {

	private final DomCheckbox checkBox;

	public EmfBooleanInput() {

		this.checkBox = new DomCheckbox();
		addCssClass(EmfCssClasses.EMF_BOOLEAN_DISPLAY);
		appendChild(checkBox);
	}

	@Override
	public Optional<Boolean> getValue() {

		return Optional.of(checkBox.isChecked());
	}

	@Override
	public void setValue(Boolean value) {

		checkBox.setChecked(value);
	}

	@Override
	public void setValueAndHandleChangeCallback(Boolean value) {

		// DomCheckbox#setChecked already executes a callback
		checkBox.setChecked(value);
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		checkBox.setChangeCallback(callback);
	}

	@Override
	public IDomElement setEnabled(boolean enabled) {

		checkBox.setEnabled(enabled);
		return this;
	}
}
