package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.input.AbstractDomNumberInput;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;
import java.math.BigDecimal;

/**
 * A {@link DomTextInput} that verifies that the user entered a valid
 * {@link BigDecimal} value.
 *
 * @author Oliver Richers
 */
public class DomBigDecimalInput extends AbstractDomNumberInput<BigDecimal> {

	public DomBigDecimalInput() {

		this(null);
	}

	public DomBigDecimalInput(BigDecimal value) {

		setValue(value);
	}

	@Override
	protected String formatValue(BigDecimal value) {

		return value.toPlainString();
	}

	@Override
	protected BigDecimal parseValue(String text) {

		try {
			return new BigDecimal(text.replace(",", "."));
		} catch (Exception exception) {
			throw new DomInputException(exception, DomI18n.INVALID_DECIMAL_NUMBER);
		}
	}
}
