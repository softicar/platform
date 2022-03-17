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
import java.util.Objects;

/**
 * A {@link DomTextInput} for decimal values that formats and parses values
 * according to {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class DomDecimalInput<T extends Number> extends AbstractDomNumberInput<T> {

	private final BigDecimalMapper<T> mapper;
	private Integer scale;

	public DomDecimalInput(BigDecimalMapper<T> mapper) {

		this.mapper = Objects.requireNonNull(mapper);
		this.scale = null;
	}

	/**
	 * Defines the number of decimal places (if given) for this input.
	 * <p>
	 * If <i>null</i> is given or by default, i.e. when this method was not
	 * called, this input does not modify the scale of values, that is, the
	 * intrinsic scale of the value will be retained.
	 * <p>
	 * If a scale is given, trailing zeros in the fractional part will be added
	 * or removed by the {@link #setValue} and {@link #getValue()} methods to
	 * match the desired scale. Non-zero decimal places will never be removed
	 * and no rounding will ever be performed. If the user entered too many
	 * decimal places, {@link #getValue()} will throw a respective exception.
	 *
	 * @param scale
	 *            the number of decimal places; or <i>null</i> to perform no
	 *            scaling
	 * @return this
	 */
	public DomDecimalInput<T> setScale(Integer scale) {

		this.scale = scale;
		return this;
	}

	@Override
	protected String formatValue(T value) {

		var decimal = getScaled(mapper.toBigDecimal(value));
		return new BigDecimalFormatter().setApplyDigitGroupSeparation(false).format(decimal);
	}

	@Override
	protected T parseValue(String text) {

		try {
			var decimal = getScaled(new BigDecimalParser(text).parseOrThrow());
			if (scale != null && decimal.scale() > scale) {
				throw new DomInputException(DomI18n.NO_MORE_THAN_ARG1_DECIMAL_PLACES_ALLOWED.toDisplay(scale));
			}
			return mapper.fromBigDecimal(decimal);
		} catch (NumberFormatException exception) {
			throw new DomInputException(exception, DomI18n.INVALID_DECIMAL_NUMBER);
		}
	}

	private BigDecimal getScaled(BigDecimal value) {

		return new BigDecimalScaleApplier()//
			.setScale(scale)
			.apply(value);
	}
}
