package com.softicar.platform.dom.style;

/**
 * This is a CSS pseudo length, e.g. <i>0</i> or <i>auto</i>.
 *
 * @author Oliver Richers
 */
public class CssPseudoLength implements ICssLength {

	private final String value;

	public CssPseudoLength(String value) {

		this.value = value;
	}

	@Override
	public CssLengthUnit getUnit() {

		throw new UnsupportedOperationException("Pseudo lengths have no unit.");
	}

	@Override
	public double getValue() {

		throw new UnsupportedOperationException("Pseudo lengths have no value.");
	}

	@Override
	public ICssLength plus(double value) {

		throw new UnsupportedOperationException("Pseudo lengths support no arithmetic.");
	}

	@Override
	public ICssLength minus(double value) {

		throw new UnsupportedOperationException("Pseudo lengths support no arithmetic.");
	}

	@Override
	public ICssLength negated() {

		throw new UnsupportedOperationException("Pseudo lengths support no arithmetic.");
	}

	@Override
	public String toString() {

		return value;
	}
}
