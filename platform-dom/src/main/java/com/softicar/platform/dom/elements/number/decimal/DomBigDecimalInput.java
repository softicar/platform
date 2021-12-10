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

	/**
	 * Parses the value text into a {@link BigDecimal}.
	 * <p>
	 * If the textual value is empty or if the value text cannot be parsed into
	 * a {@link BigDecimal}, an {@link Optional#empty()} is returned.
	 *
	 * @return the optional value as {@link BigDecimal}
	 * @throws NumberFormatException
	 *             if the value text cannot be parsed into a valid
	 *             {@link BigDecimal}
	 */
	public Optional<BigDecimal> getBigDecimal() {

		try {
			return getBigDecimalOrThrowIfInvalidFormat();
		} catch (NumberFormatException exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	/**
	 * Parses the value text into a {@link BigDecimal}.
	 * <p>
	 * If and only if the textual value is empty, an {@link Optional#empty()} is
	 * returned.
	 *
	 * @return the optional value as {@link BigDecimal}
	 * @throws NumberFormatException
	 *             if the value text cannot be parsed into a valid
	 *             {@link BigDecimal}
	 */
	public Optional<BigDecimal> getBigDecimalOrThrowIfInvalidFormat() throws NumberFormatException {

		String value = getTextOrNull();
		if (value != null) {
			return Optional.of(parseValue(value));
		}
		return Optional.empty();
	}

	private BigDecimal parseValue(String value) {

		return new BigDecimal(value.replace(",", "."));
	}
}
