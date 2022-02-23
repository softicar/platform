package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomInputException;
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
public class DomDoubleInput extends DomTextInput {

	// TODO the default precision is arbitrary
	private int displayPrecision = 4;

	public DomDoubleInput() {

		// nothing to do
	}

	public DomDoubleInput(double value) {

		super("" + value);
	}

	// TODO the semantics of this method are bad
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

			return Double.valueOf(text);
		} catch (NumberFormatException exception) {
			throw new DomInputException(exception, DomI18n.INVALID_DECIMAL_NUMBER);
		}
	}

	// TODO the semantics of this method are bad
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

	/**
	 * Defines the maximum number of decimal places to show.
	 *
	 * @param precision
	 *            the precision (must be zero or greater)
	 */
	public void setDisplayPrecision(int precision) {

		this.displayPrecision = precision;
	}
}
