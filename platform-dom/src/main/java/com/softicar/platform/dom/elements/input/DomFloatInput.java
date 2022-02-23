package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.number.parser.FloatParser;
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
public class DomFloatInput extends DomTextInput {

	// TODO the default precision is arbitrary
	private int displayPrecision = 4;

	public DomFloatInput() {

		// nothing to do
	}

	public DomFloatInput(float value) {

		super("" + value + "");
	}

	// TODO the semantics of this method are bad
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

			return Float.valueOf(text);
		} catch (NumberFormatException exception) {
			throw new DomInputException(exception, DomI18n.INVALID_DECIMAL_NUMBER);
		}
	}

	// TODO the semantics of this method are bad
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
