package com.softicar.platform.emf.attribute.field.integer;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Objects;

public class EmfIntegerInput extends DomIntegerInput implements IEmfInput<Integer>, IDomChangeEventHandler {

	private INullaryVoidFunction callback = INullaryVoidFunction.NO_OPERATION;

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		this.callback = Objects.requireNonNull(callback);
	}

	@Override
	public void handleChange(IDomEvent event) {

		callback.apply();
	}
}
