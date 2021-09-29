package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.number.parser.DoubleParser;
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
public class DomDoubleInput extends AbstractDomNumberInput {

	private int displayPrecision = 6;
	private int allowedPrecision = 0;
	private Double begin = null;
	private Double end = null;

	public DomDoubleInput() {

		// nothing to do
	}

	public DomDoubleInput(double value) {

		super("" + value);
	}

	/**
	 * @return the entered double value
	 * @throws SofticarUserException
	 *             if the user didn't enter a valid double or if the entered
	 *             double is out of range (see {@link #setRange(Double, Double)}
	 *             , {@link #setMinimumDouble(Double)},
	 *             {@link #setMaximumDouble(Double)})
	 */
	public Double getDouble() {

		trimValue();
		fixComma();
		if (getValue() == null) {
			return null;
		}

		String text = getValue();

		try {
			if (text.equals("")) {
				return 0.0;
			}

			// parse string
			Double result = Double.valueOf(text);

			// check range
			if (begin != null && begin.doubleValue() > result) {
				throwOutOfRange(text);
			}
			if (end != null && end.doubleValue() < result) {
				throwOutOfRange(text);
			}
			if (allowedPrecision != 0 && text.matches(".+\\..{" + (allowedPrecision + 1) + ",}")) {
				throw new TooPreciseDoubleException(text, allowedPrecision);
			}
			return result;
		} catch (NumberFormatException exception) {
			throw new SofticarUserException(exception, DomI18n.THE_SPECIFIED_VALUE_ARG1_IS_NOT_A_VALID_DECIMAL_NUMBER.toDisplay(text));
		}
	}

	public Double getDoubleOrNull() {

		trimValue();
		fixComma();
		if (getValue() != null) {
			if (DoubleParser.isDouble(getValue())) {
				return Double.valueOf(getValue());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setDouble(Double value) {

		if (value != null) {
			setValue(DoubleFormatter.formatDouble(value, displayPrecision));
		} else {
			setValue(null);
		}
	}

	public void fixComma() {

		if (getValue() != null) {
			setValue(getValue().replace(",", "."));
		}
	}

	public void setRange(Double begin, Double end) {

		this.begin = begin;
		this.end = end;
	}

	public void setMinimumDouble(Double minimum) {

		this.begin = minimum;
	}

	public void setMaximumDouble(Double maximum) {

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

	public static class TooPreciseDoubleException extends SofticarUserException {

		private static final long serialVersionUID = 1L;

		public TooPreciseDoubleException(String valueAsText, int allowedPrecision) {

			super(DomI18n.THE_DECIMAL_VALUE_ARG1_MUST_HAVE_ARG2_OR_LESS_DECIMAL_PLACES.toDisplay(valueAsText, allowedPrecision));
		}
	}
}
