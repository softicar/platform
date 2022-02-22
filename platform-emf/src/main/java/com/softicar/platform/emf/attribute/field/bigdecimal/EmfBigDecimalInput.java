package com.softicar.platform.emf.attribute.field.bigdecimal;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.number.decimal.DomBigDecimalInput;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.IDomValueBasedInputNode;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.input.AbstractEmfChangeListeningInputDiv;
import java.math.BigDecimal;

public class EmfBigDecimalInput extends AbstractEmfChangeListeningInputDiv<BigDecimal> {

	private final ChangeListeningBigDecimalInput input;
	private int scale;

	public EmfBigDecimalInput() {

		this.input = new ChangeListeningBigDecimalInput();
		this.scale = -1;

		appendChild(input);
	}

	/**
	 * Defines the number of decimal places for this input.
	 * <p>
	 * Trailing zeros in the fractional part will be added or removed by the
	 * {@link #setValue} and {@link #getValueOrThrow} methods to match the
	 * desired scale. Non-zero decimal places will never be removed. See the
	 * respective methods for more details.
	 * <p>
	 * By default, i.e. when this method was not called, this input does not
	 * modify the scale of values in this input.
	 *
	 * @param scale
	 *            the number of decimal places which must be <i>>= 0</i>
	 * @return this
	 */
	public EmfBigDecimalInput setScale(int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("Scale must be non-negative.");
		}
		this.scale = scale;
		return this;
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		this.input.setChangeCallback(callback);
	}

	/**
	 * Returns the value of this input as {@link BigDecimal}.
	 * <p>
	 * If the input value is blank, <i>null</i> will be returned.
	 * <p>
	 * If and only if a scale was defined by {@link #setScale(int)}, trailing
	 * zeros will be appended or removed to match the scale. If the defined
	 * scale cannot be reached by this, an {@link DomInputException} will be
	 * thrown.
	 *
	 * @return the {@link BigDecimal} value or <i>null</i>
	 * @throws DomInputException
	 *             if the input value does not represent a valid
	 *             {@link BigDecimal} or if the scale of the {@link BigDecimal}
	 *             exceeds the scale defined by {@link #setScale(int)}
	 */
	@Override
	public BigDecimal getValueOrThrow() throws DomInputException {

		try {
			var value = applyScale(input.getBigDecimalOrThrowIfInvalidFormat().orElse(null));
			if (value != null && scale >= 0 && value.scale() > scale) {
				throw new DomInputException(EmfI18n.ONLY_ARG1_DECIMAL_PLACES_ALLOWED.toDisplay(scale));
			}
			return value;
		} catch (NumberFormatException exception) {
			DevNull.swallow(exception);
			throw new DomInputException(EmfI18n.INVALID_DECIMAL_NUMBER);
		}
	}

	/**
	 * Sets the value of this input to the specified {@link BigDecimal}.
	 * <p>
	 * If and only if a scale was defined by {@link #setScale(int)}, trailing
	 * zeros will be append or removed to match the scale, if possible.
	 *
	 * @param value
	 *            the input value to set (may be <i>null</i>)
	 */
	@Override
	public void setValue(BigDecimal value) {

		input.setBigDecimal(applyScale(value));
	}

	@Override
	public IDomElement setEnabled(boolean enabled) {

		input.setEnabled(enabled);
		return this;
	}

	@Override
	public IDomValueBasedInputNode<BigDecimal> setPlaceholder(IDisplayString placeholder) {

		input.setPlaceholder(placeholder);
		return this;
	}

	private BigDecimal applyScale(BigDecimal value) {

		if (value != null && scale >= 0) {
			value = value.stripTrailingZeros();
			if (value.scale() < scale) {
				value = value.setScale(scale);
			}
		}
		return value;
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
