package com.softicar.platform.dom.elements.input;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} for {@link Integer} values.
 *
 * @author Oliver Richers
 */
public class DomIntegerInput extends AbstractDomNumberInput<Integer> {

	public DomIntegerInput() {

		// nothing to do
	}

	public DomIntegerInput(Integer value) {

		setValue(value);
	}

	@Override
	protected String formatValue(Integer value) {

		return value.toString();
	}

	@Override
	protected Integer parseValue(String inputText) {

		try {
			return Integer.valueOf(inputText);
		} catch (Exception exception) {
			throw new DomInputException(exception, DomI18n.INVALID_INTEGER);
		}
	}
}
