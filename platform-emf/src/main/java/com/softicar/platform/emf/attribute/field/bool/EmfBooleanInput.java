package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class EmfBooleanInput extends DomCheckbox implements IEmfInput<Boolean> {

	private INullaryVoidFunction callback = INullaryVoidFunction.NO_OPERATION;

	public EmfBooleanInput(boolean checked) {

		super(checked);
	}

	@Override
	public Optional<Boolean> getValue() {

		return Optional.of(isChecked());
	}

	@Override
	public void setValueAndHandleChangeCallback(Boolean value) {

		setValue(value);
		callback.apply();
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		this.callback = Objects.requireNonNull(callback);
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

	public void setChangeCallback(Consumer<Boolean> consumer) {

		this.callback = () -> Objects.requireNonNull(consumer).accept(isChecked());
	}
}
