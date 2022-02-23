package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomInputException;
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

	public DomLongInput() {

		// nothing to do
	}

	public DomLongInput(int value) {

		super("" + value);
	}

	public DomLongInput(long value) {

		super("" + value);
	}

	// TODO the semantics of this method are bad
	public Long getLong() {

		String text = getValue().trim();
		try {
			if (text.equals("")) {
				return 0l;
			}

			return Long.valueOf(text);
		} catch (NumberFormatException exception) {
			throw new DomInputException(exception, DomI18n.INVALID_INTEGER);
		}
	}

	// TODO the semantics of this method are bad
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
}
