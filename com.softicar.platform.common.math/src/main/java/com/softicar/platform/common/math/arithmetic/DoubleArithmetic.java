package com.softicar.platform.common.math.arithmetic;

/**
 * Implementation of {@link IArithmetic} for {@link Double}.
 *
 * @author Oliver Richers
 */
class DoubleArithmetic implements IArithmetic<Double> {

	private static final Double ZERO = 0.0;
	private static final Double ONE = 1.0;

	@Override
	public Double getZero() {

		return ZERO;
	}

	@Override
	public Double getOne() {

		return ONE;
	}

	@Override
	public Double plus(Double left, Double right) {

		return left + right;
	}

	@Override
	public Double minus(Double left, Double right) {

		return left - right;
	}

	@Override
	public Double times(Double left, Double right) {

		return left * right;
	}

	@Override
	public Double divided(Double left, Double right) {

		return left / right;
	}

	@Override
	public Double negate(Double value) {

		return -value;
	}

	@Override
	public int compare(Double left, Double right) {

		return Double.compare(left, right);
	}
}
