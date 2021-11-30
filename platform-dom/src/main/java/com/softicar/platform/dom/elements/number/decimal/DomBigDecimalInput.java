package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.elements.input.AbstractDomNumberInput;
import com.softicar.platform.dom.input.DomTextInput;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * A {@link DomTextInput} that verifies that the user entered a valid
 * {@link BigDecimal} value.
 *
 * @author Semsudin Mekanic
 * @author Oliver Richers
 */
public class DomBigDecimalInput extends AbstractDomNumberInput {

	public DomBigDecimalInput() {

		this(null);
	}

	public DomBigDecimalInput(BigDecimal value) {

		setBigDecimal(value);
	}

	public void setBigDecimal(BigDecimal value) {

		if (value != null) {
			setValue(value.toPlainString());
		} else {
			setValue(null);
		}
	}

	public BigDecimal getBigDecimalOrNull() {

		return getBigDecimal().orElse(null);
	}

	public BigDecimal getBigDecimalOrZero() {

		return getBigDecimal().orElse(BigDecimal.ZERO);
	}

	public Optional<BigDecimal> getBigDecimal() {

		String value = getTextOrNull();
		if (value != null) {
			try {
				return Optional.of(parseValue(value));
			} catch (NumberFormatException exception) {
				DevNull.swallow(exception);
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	private BigDecimal parseValue(String value) {

		return new BigDecimal(value.replace(",", "."));
	}
}
