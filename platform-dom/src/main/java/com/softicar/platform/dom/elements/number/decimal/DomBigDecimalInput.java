package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.input.AbstractDomNumberInput;
import com.softicar.platform.dom.input.DomInputException;
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

	/**
	 * @deprecated use {@link #retrieveValue()}
	 */
	@Deprecated
	public BigDecimal getBigDecimalOrNull() {

		return getBigDecimal().orElse(null);
	}

	/**
	 * @deprecated use {@link #retrieveValue()}
	 */
	@Deprecated
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
	 * @deprecated use {@link #retrieveValue()}
	 */
	@Deprecated
	public Optional<BigDecimal> getBigDecimal() {

		try {
			return retrieveValue();
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	/**
	 * Parses the value text into a {@link BigDecimal}.
	 * <p>
	 * If the value text is empty or blank, an empty {@link Optional} is
	 * returned. Otherwise, the value text is parsed into a {@link BigDecimal}.
	 * If parsing failed, an exception is thrown.
	 *
	 * @return the optional value as {@link BigDecimal}
	 * @throws DomInputException
	 *             if the value text cannot be parsed into a valid
	 *             {@link BigDecimal}
	 */
	public Optional<BigDecimal> retrieveValue() throws DomInputException {

		String value = getValue();
		if (value != null && !value.isBlank()) {
			return Optional.of(parseValue(value));
		} else {
			return Optional.empty();
		}
	}

	private BigDecimal parseValue(String value) {

		try {
			return new BigDecimal(value.trim().replace(",", "."));
		} catch (Exception exception) {
			throw new DomInputException(exception, DomI18n.INVALID_DECIMAL_NUMBER);
		}
	}
}
