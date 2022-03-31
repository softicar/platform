package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalInput;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.math.BigDecimal;
import java.util.Objects;

// TODO Reconsider this class with PLAT-735
public class EmfBigDecimalInput extends DomBigDecimalInput implements IEmfInput<BigDecimal>, IDomChangeEventHandler {

	private INullaryVoidFunction callback;

	public EmfBigDecimalInput() {

		this.callback = INullaryVoidFunction.NO_OPERATION;
	}

	@Override
	public void setValueAndHandleChangeCallback(BigDecimal value) {

		setValue(value);
		callback.apply();
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		this.callback = Objects.requireNonNull(callback);
	}

	@Override
	public void handleChange(IDomEvent event) {

		callback.apply();
	}
}
