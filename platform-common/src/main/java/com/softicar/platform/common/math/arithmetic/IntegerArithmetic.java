package com.softicar.platform.common.math.arithmetic;

/**
 * Implementation of {@link IArithmetic} for {@link Integer}.
 *
 * @author Oliver Richers
 */
class IntegerArithmetic implements IArithmetic<Integer> {

	private static final Integer ZERO = 0;
	private static final Integer ONE = 1;

	@Override
	public Integer getZero() {

		return ZERO;
	}

	@Override
	public Integer getOne() {

		return ONE;
	}

	@Override
	public Integer plus(Integer left, Integer right) {

		return left + right;
	}

	@Override
	public Integer minus(Integer left, Integer right) {

		return left - right;
	}

	@Override
	public Integer times(Integer left, Integer right) {

		return left * right;
	}

	@Override
	public Integer divided(Integer left, Integer right) {

		return left / right;
	}

	@Override
	public Integer negate(Integer value) {

		return -value;
	}

	@Override
	public int compare(Integer left, Integer right) {

		return Integer.compare(left, right);
	}
}
