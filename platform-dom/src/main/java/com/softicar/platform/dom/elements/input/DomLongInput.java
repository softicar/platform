package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} for {@link Long} values.
 *
 * @author Oliver Richers
 */
public class DomLongInput extends AbstractDomNumberInput<Long> {

	public DomLongInput() {

		// nothing to do
	}

	public DomLongInput(Long value) {

		setValue(value);
	}

	@Override
	protected String formatValue(Long value) {

		return value.toString();
	}

	@Override
	protected Long parseValue(String inputText) {

		try {
			return Long.valueOf(inputText);
		} catch (Exception exception) {
			throw new DomInputException(exception, DomI18n.INVALID_INTEGER);
		}
	}
}
