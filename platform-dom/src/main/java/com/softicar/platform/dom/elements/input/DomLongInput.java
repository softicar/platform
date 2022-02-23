package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} that verifies that the user entered a valid
 * {@link Long} value.
 * <p>
 * <b>TODO</b> A common base class should be implemented for
 * {@link DomIntegerInput} and {@link DomLongInput}.
 *
 * @author Oliver Richers
 */
public class DomLongInput extends DomTextInput {

	private long minimum = Long.MIN_VALUE;
	private long maximum = Long.MAX_VALUE;

	public DomLongInput() {

		// nothing to do
	}

	public DomLongInput(int value) {

		super("" + value);
	}

	public DomLongInput(long value) {

		super("" + value);
	}

	public Long getLong() {

		String text = getValue().trim();
		try {
			if (text.equals("")) {
				return 0l;
			}

			// parse string
			Long result = Long.valueOf(text);

			// check range
			if (result < minimum) {
				throwOutOfRange(text);
			}
			if (result > maximum) {
				throwOutOfRange(text);
			}

			return result;
		} catch (NumberFormatException exception) {
			throw new SofticarUserException(
				exception,
				DomI18n.THE_SPECIFIED_VALUE_ARG1_IS_NOT_IN_THE_RANGE_BETWEEN_ARG2_AND_ARG3.toDisplay(text, minimum, maximum));
		}
	}

	public Long getLongOrNull() {

		if (getValue().trim().isEmpty()) {
			return null;
		} else {
			return getLong();
		}
	}

	public void setLong(Long value) {

		setValue("" + value);
	}

	public void setRange(Long begin, Long end) {

		this.minimum = begin;
		this.maximum = end;
	}

	public void setMinimumLong(Long minimum) {

		this.minimum = minimum;
	}

	public void setMaximumLong(Long maximum) {

		this.maximum = maximum;
	}

	private void throwOutOfRange(String value) {

		throw new SofticarUserException(DomI18n.THE_SPECIFIED_VALUE_ARG1_IS_NOT_IN_THE_RANGE_BETWEEN_ARG2_AND_ARG3.toDisplay(value, minimum, maximum));
	}
}
