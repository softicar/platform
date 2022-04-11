package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;
import java.util.function.Consumer;

public class EmfBooleanInput extends DomCheckbox implements IEmfInput<Boolean>, IDomClickEventHandler, IDomEnterKeyEventHandler, IDomSpaceKeyEventHandler {

	private INullaryVoidFunction callback = INullaryVoidFunction.NO_OPERATION;

	public EmfBooleanInput() {

		super();
	}

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

		this.callback = callback;
	}

	@Override
	public void handleClick(IDomEvent event) {

		handleEvent();
	}

	@Override
	public void handleEnterKey(IDomEvent event) {

		handleEvent();
	}

	@Override
	public void handleSpaceKey(IDomEvent event) {

		handleEvent();
	}

	@Override
	public EmfBooleanInput setLabel(IDisplayString label) {

		super.setLabel(label);
		return this;
	}

	public void setChangeCallback(Consumer<Boolean> consumer) {

		this.callback = () -> consumer.accept(isChecked());
	}

	private void handleEvent() {

		if (isEnabled()) {
			setValueAndHandleChangeCallback(!isChecked());
		}
	}
}
