package com.softicar.platform.common.math.arithmetic;

/**
 * Implementation of {@link IArithmetic} for {@link Float}.
 *
 * @author Oliver Richers
 */
class FloatArithmetic implements IArithmetic<Float> {

	private static final Float ZERO = 0.0f;
	private static final Float ONE = 1.0f;

	@Override
	public Float getZero() {

		return ZERO;
	}

	@Override
	public Float getOne() {

		return ONE;
	}

	@Override
	public Float plus(Float left, Float right) {

		return left + right;
	}

	@Override
	public Float minus(Float left, Float right) {

		return left - right;
	}

	@Override
	public Float times(Float left, Float right) {

		return left * right;
	}

	@Override
	public Float divided(Float left, Float right) {

		return left / right;
	}

	@Override
	public Float negate(Float value) {

		return -value;
	}

	@Override
	public int compare(Float left, Float right) {

		return Float.compare(left, right);
	}
}
