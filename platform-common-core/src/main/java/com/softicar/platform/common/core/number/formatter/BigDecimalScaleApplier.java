package com.softicar.platform.common.core.number.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Applies a given scale to a {@link BigDecimal} value.
 *
 * @author Oliver Richers
 */
public class BigDecimalScaleApplier {

	private Integer scale;
	private RoundingMode roundingMode;

	public BigDecimalScaleApplier() {

		this.scale = null;
		this.roundingMode = null;
	}

	/**
	 * Defines the desired number of decimal places.
	 * <p>
	 * If <i>null</i> is provided as scale (which is the default), no changes
	 * will be applied to the value.
	 *
	 * @param scale
	 *            the scale; or <i>null</i> to perform no scaling
	 * @return this
	 */
	public BigDecimalScaleApplier setScale(Integer scale) {

		this.scale = scale;
		return this;
	}

	/**
	 * Defines the desired {@link RoundingMode} used to match the scale.
	 * <p>
	 * If <i>null</i> is provided as {@link RoundingMode} (which is the
	 * default), no rounding will be done. Only redundant, trailing zeros will
	 * be added or discarded from the fractional part to approximate the desired
	 * scale as possible.
	 *
	 * @param roundingMode
	 *            the {@link RoundingMode}; or <i>null</i> to perform no
	 *            rounding
	 * @return this
	 */
	public BigDecimalScaleApplier setRoundingMode(RoundingMode roundingMode) {

		this.roundingMode = roundingMode;
		return this;
	}

	/**
	 * Applies the scale defined by calling {@link #setScale(Integer)} and
	 * rounds the values as necessary if a {@link RoundingMode} was specified.
	 *
	 * @param value
	 *            the value to apply the scale to (may be <i>null</i>)
	 * @return the scaled value; or <i>null</i> if and only if the given value
	 *         was <i>null</i>
	 */
	public BigDecimal apply(BigDecimal value) {

		if (scale != null) {
			if (roundingMode != null) {
				return applyScaleWithRounding(value);
			} else {
				return applyScaleWithoutRounding(value);
			}
		}
		return value;
	}

	private BigDecimal applyScaleWithRounding(BigDecimal value) {

		return value.setScale(scale, roundingMode);
	}

	private BigDecimal applyScaleWithoutRounding(BigDecimal value) {

		if (scale >= 0 && value.scale() != scale) {
			value = value.stripTrailingZeros();
			if (value.scale() < scale) {
				value = value.setScale(scale);
			}
		}
		return value;
	}
}
