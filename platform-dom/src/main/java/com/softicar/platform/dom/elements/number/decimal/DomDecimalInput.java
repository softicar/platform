package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.common.core.number.formatter.BigDecimalScaleApplier;
import com.softicar.platform.common.core.number.parser.BigDecimalParser;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.input.AbstractDomNumberInput;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;
import java.math.BigDecimal;

/**
 * A {@link DomTextInput} for decimal values that formats and parses values
 * according to {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class DomDecimalInput<T extends Number> extends AbstractDomNumberInput<T> {

	private final BigDecimalMapper<T> mapper;
	private int scale = -1;

	public DomDecimalInput(BigDecimalMapper<T> mapper) {

		this.mapper = mapper;
	}

	/**
	 * Defines the number of decimal places for this input.
	 * <p>
	 * Trailing zeros in the fractional part will be added or removed by the
	 * {@link #setValue} and {@link #getValue()} methods to match the desired
	 * scale. Non-zero decimal places will never be removed. If the user entered
	 * too many decimal places, {@link #getValue()} will throw a respective
	 * exception.
	 * <p>
	 * By default, i.e. when this method was not called, this input does not
	 * modify the scale of values in this input.
	 *
	 * @param scale
	 *            the number of decimal places which must be <i>>= 0</i>
	 * @return this
	 */
	public DomDecimalInput<T> setScale(int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("Scale must be non-negative.");
		}
		this.scale = scale;
		return this;
	}

	@Override
	protected String formatValue(T value) {

		var decimal = getScaled(mapper.toBigDecimal(value));
		return new BigDecimalFormatter(decimal).format();
	}

	@Override
	protected T parseValue(String text) {

		try {
			var decimal = getScaled(new BigDecimalParser(text).parseOrThrow());
			if (scale >= 0 && decimal.scale() > scale) {
				throw new DomInputException(DomI18n.ONLY_ARG1_DECIMAL_PLACES_ALLOWED.toDisplay(scale));
			}
			return mapper.fromBigDecimal(decimal);
		} catch (NumberFormatException exception) {
			throw new DomInputException(exception, DomI18n.INVALID_DECIMAL_NUMBER);
		}
	}

	private BigDecimal getScaled(BigDecimal value) {

		if (scale >= 0) {
			return new BigDecimalScaleApplier()//
				.setScale(scale)
				.apply(value);
		} else {
			return value;
		}
	}
}
