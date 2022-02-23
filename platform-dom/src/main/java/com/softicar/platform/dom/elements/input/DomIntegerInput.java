package com.softicar.platform.dom.elements.input;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} that verifies that the user entered a valid
 * {@link Integer} value.
 * <p>
 * <b>TODO</b> A common base class should be implemented for
 * {@link DomIntegerInput} and {@link DomLongInput}.
 *
 * @author Oliver Richers
 */
public class DomIntegerInput extends DomTextInput {

	private int minimum = Integer.MIN_VALUE;
	private int maximum = Integer.MAX_VALUE;

	public DomIntegerInput() {

		// nothing
	}

	public DomIntegerInput(int value) {

		super("" + value);
	}

	public DomIntegerInput(Integer value) {

		if (value != null) {
			setValue("" + value);
		}
	}

	public Integer getInteger() {

		Integer integer = getIntegerOrNull();
		return integer != null? integer : 0;
	}

	public Integer getIntegerOrNull() {

		if (getValue() == null) {
			return null;
		}

		String text = getValue().trim();
		try {
			if (text.isEmpty()) {
				return null;
			}

			// parse string
			Integer result = Integer.valueOf(text);

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

	public void setInteger(Integer value) {

		if (value != null) {
			setValue("" + value);
		} else {
			setValue(null);
		}
	}

	public DomIntegerInput setRange(Integer begin, Integer end) {

		this.minimum = begin;
		this.maximum = end;
		return this;
	}

	public void setMinimumInteger(Integer minimum) {

		this.minimum = minimum;
	}

	public void setMaximumInteger(Integer maximum) {

		this.maximum = maximum;
	}

	private void throwOutOfRange(String value) {

		throw new SofticarUserException(DomI18n.THE_SPECIFIED_VALUE_ARG1_IS_NOT_IN_THE_RANGE_BETWEEN_ARG2_AND_ARG3.toDisplay(value, minimum, maximum));
	}
}
