package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.NullaryVoidFunctionList;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfBooleanInput extends DomCheckbox implements IEmfInput<Boolean> {

	private final NullaryVoidFunctionList callbacks;

	public EmfBooleanInput(boolean checked) {

		super(checked);

		this.callbacks = new NullaryVoidFunctionList();
	}

	@Override
	public Optional<Boolean> getValue() {

		return Optional.of(isChecked());
	}

	@Override
	public void setValueAndHandleChangeCallback(Boolean value) {

		setValue(value);
		callbacks.apply();
	}

	@Override
	public void addChangeCallback(INullaryVoidFunction callback) {

		this.callbacks.add(callback);
	}

	@Override
	public EmfBooleanInput setLabel(IDisplayString label) {

		super.setLabel(label);
		return this;
	}

	@Override
	protected void toggleCheckedState() {

		if (isEnabled()) {
			setValueAndHandleChangeCallback(!isChecked());
		}
	}
}
