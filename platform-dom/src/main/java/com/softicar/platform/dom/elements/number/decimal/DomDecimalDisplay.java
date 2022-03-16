package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.common.core.number.formatter.BigDecimalScaleApplier;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * An {@link IDomElement} to display decimal values according to
 * {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class DomDecimalDisplay<T extends Number> extends DomDiv {

	private final BigDecimalMapper<T> mapper;
	private Integer scale;

	public DomDecimalDisplay(BigDecimalMapper<T> mapper) {

		this.mapper = Objects.requireNonNull(mapper);
		this.scale = null;
	}

	/**
	 * Defines the number of decimal places (if given) for this display.
	 * <p>
	 * If <i>null</i> is given or by default, i.e. when this method was not
	 * called, this display does not modify the scale of values, that is, the
	 * intrinsic scale of the value will be retained.
	 * <p>
	 * If a scale is given, rounding will be applied as necessary using
	 * {@link RoundingMode#HALF_UP}
	 *
	 * @param scale
	 *            the number of decimal places; or <i>null</i> to perform no
	 *            scaling
	 * @return this
	 */
	public DomDecimalDisplay<T> setScale(Integer scale) {

		this.scale = scale;
		return this;
	}

	/**
	 * Updates this {@link IDomElement} to show the given value.
	 *
	 * @param value
	 *            the value to show (may be <i>null</i>)
	 * @return this
	 */
	public DomDecimalDisplay<T> setValue(T value) {

		removeChildren();

		if (value != null) {
			var decimal = getScaled(mapper.toBigDecimal(value));
			appendText(new BigDecimalFormatter().format(decimal));
		}

		return this;
	}

	private BigDecimal getScaled(BigDecimal value) {

		return new BigDecimalScaleApplier()//
			.setScale(scale)
			.setRoundingMode(RoundingMode.HALF_UP)
			.apply(value);
	}
}
