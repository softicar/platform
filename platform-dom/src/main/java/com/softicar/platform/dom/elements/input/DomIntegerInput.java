package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomInputException;
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

	// TODO the semantics of this method are bad
	public Integer getInteger() {

		Integer integer = getIntegerOrNull();
		return integer != null? integer : 0;
	}

	// TODO the semantics of this method are bad
	public Integer getIntegerOrNull() {

		if (getValue() == null) {
			return null;
		}

		String text = getValue().trim();
		try {
			if (text.isEmpty()) {
				return null;
			}

			return Integer.valueOf(text);
		} catch (NumberFormatException exception) {
			throw new DomInputException(exception, DomI18n.INVALID_INTEGER);
		}
	}

	public void setInteger(Integer value) {

		if (value != null) {
			setValue("" + value);
		} else {
			setValue(null);
		}
	}
}
