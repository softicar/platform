package com.softicar.platform.dom.style;

import java.util.Locale;

/**
 * Default implementation of {@link ICssLength}.
 *
 * @author Oliver Richers
 * @see CssPixel
 * @see CssPercent
 */
public class CssLength implements ICssLength {

	private final CssLengthUnit unit;
	private final double value;

	public CssLength(CssLengthUnit unit, double value) {

		this.unit = unit;
		this.value = value;
	}

	@Override
	public CssLengthUnit getUnit() {

		return unit;
	}

	@Override
	public double getValue() {

		return value;
	}

	@Override
	public CssLength plus(double value) {

		return new CssLength(unit, this.value + value);
	}

	@Override
	public CssLength minus(double value) {

		return new CssLength(unit, this.value - value);
	}

	@Override
	public CssLength negated() {

		return new CssLength(unit, -this.value);
	}

	@Override
	public String toString() {

		if (value == (long) value) {
			return (long) value + unit.toString();
		} else {
			return String.format(Locale.US, "%s%s", value, unit.toString());
		}
	}
}
