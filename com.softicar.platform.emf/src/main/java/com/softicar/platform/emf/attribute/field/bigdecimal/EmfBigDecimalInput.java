package com.softicar.platform.emf.attribute.field.bigdecimal;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalInput;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.attribute.input.AbstractEmfChangeListeningInputDiv;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import java.math.BigDecimal;

public class EmfBigDecimalInput extends AbstractEmfChangeListeningInputDiv<BigDecimal> {

	private final Integer scale;
	private final ChangeListeningBigDecimalInput input;

	public EmfBigDecimalInput() {

		this(null);
	}

	public EmfBigDecimalInput(Integer scale) {

		this.scale = scale;
		this.input = new ChangeListeningBigDecimalInput();
		appendChild(input);
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		this.input.setChangeCallback(callback);
	}

	@Override
	public BigDecimal getValueOrThrow() throws EmfInputException {

		return input.getBigDecimalOrNull();
	}

	@Override
	public void setValue(BigDecimal value) {

		if (scale != null) {
			input.setBigDecimal(value, scale);
		} else {
			input.setBigDecimal(value);
		}
	}

	@Override
	public IDomElement setEnabled(boolean enabled) {

		input.setEnabled(enabled);
		return this;
	}

	private static class ChangeListeningBigDecimalInput extends DomBigDecimalInput implements IDomChangeEventHandler {

		private INullaryVoidFunction callback;

		public void setChangeCallback(INullaryVoidFunction callback) {

			this.callback = callback;
		}

		@Override
		public void handleChange(IDomEvent event) {

			if (callback != null) {
				callback.apply();
			}
		}
	}
}
