package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.number.parser.FloatParser;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} that verifies that the user entered a valid
 * {@link Float} value.
 * <p>
 * <b>TODO</b> A common base class should be implemented for
 * {@link DomDoubleInput} and {@link DomFloatInput}.
 *
 * @author Semsudin Mekanic
 */
public class DomFloatInput extends DomTextInput {

	private int displayPrecision = 4;
	private int allowedPrecision = 0;
	private Float begin = null;
	private Float end = null;

	public DomFloatInput() {

		// nothing to do
	}

	public DomFloatInput(float value) {

		super("" + value + "");
	}

	/**
	 * @return the entered double value
	 * @throws SofticarUserException
	 *             if the user didn't enter a valid double or if the entered
	 *             double is out of range (see {@link #setRange(Float, Float)},
	 *             {@link #setMinimumDouble(Float)},
	 *             {@link #setMaximumDouble(Float)})
	 */
	public Float getFloat() {

		trimValue();
		fixComma();
		if (getValue() == null) {
			return null;
		}

		String text = getValue();

		try {
			if (text.equals("")) {
				return 0.0f;
			}

			// parse string
			Float result = Float.valueOf(text);

			// check range
			if (begin != null && begin.floatValue() > result) {
				throwOutOfRange(text);
			}
			if (end != null && end.floatValue() < result) {
				throwOutOfRange(text);
			}
			if (allowedPrecision != 0 && text.matches(".+\\..{" + (allowedPrecision + 1) + ",}")) {
				throw new TooPreciseFloatException(text, allowedPrecision);
			}
			return result;
		} catch (NumberFormatException exception) {
			throw new SofticarUserException(exception, DomI18n.THE_SPECIFIED_VALUE_ARG1_IS_NOT_A_VALID_DECIMAL_NUMBER.toDisplay(text));
		}
	}

	public Float getFloatOrNull() {

		trimValue();
		fixComma();
		if (getValue() != null) {
			if (FloatParser.isFloat(getValue())) {
				return Float.valueOf(getValue());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setFloat(Float value) {

		if (value != null) {
			setValue(DoubleFormatter.formatDouble(value.doubleValue(), displayPrecision));
		} else {
			setValue(null);
		}
	}

	public void fixComma() {

		if (getValue() != null) {
			setValue(getValue().replace(",", "."));
		}
	}

	public void setRange(Float begin, Float end) {

		this.begin = begin;
		this.end = end;
	}

	public void setMinimumDouble(Float minimum) {

		this.begin = minimum;
	}

	public void setMaximumDouble(Float maximum) {

		this.end = maximum;
	}

	/**
	 * @param precision
	 *            the allowedPrecision to set. must be an non-negative integer.
	 *            Zero means no restriction.
	 */
	public void setAllowedPrecision(final int precision) {

		if (precision < 0) {
			throw new SofticarDeveloperException("Decimal precision must be an non-negative integer");
		}
		this.allowedPrecision = precision;
	}

	/**
	 * returns the current, allowed double precision.
	 *
	 * @return the current, allowed double precision
	 */
	public int getAllowedPrecision() {

		return allowedPrecision;
	}

	/**
	 * resets the decimal precision to be shown in the input element
	 *
	 * @param precision
	 *            the precision you want to show
	 */
	public void setDisplayPrecision(final int precision) {

		this.displayPrecision = precision;
	}

	private void throwOutOfRange(String value) {

		throw new SofticarUserException(
			DomI18n.THE_SPECIFIED_VALUE_ARG1_IS_NOT_IN_THE_RANGE_BETWEEN_ARG2_AND_ARG3
				.toDisplay(//
					value,
					begin == null? "-" + DomI18n.INFINITY : begin.toString(),
					end == null? DomI18n.INFINITY : end.toString()));
	}

	public static class TooPreciseFloatException extends SofticarUserException {

		private static final long serialVersionUID = 1L;

		public TooPreciseFloatException(String valueAsText, int allowedPrecision) {

			super(DomI18n.THE_DECIMAL_VALUE_ARG1_MUST_HAVE_ARG2_OR_LESS_DECIMAL_PLACES.toDisplay(valueAsText, allowedPrecision));
		}
	}
}
