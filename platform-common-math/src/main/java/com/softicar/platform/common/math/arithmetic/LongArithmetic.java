package com.softicar.platform.common.math.arithmetic;

/**
 * Implementation of {@link IArithmetic} for {@link Long}.
 *
 * @author Oliver Richers
 */
class LongArithmetic implements IArithmetic<Long> {

	private static final Long ZERO = 0L;
	private static final Long ONE = 1L;

	@Override
	public Long getZero() {

		return ZERO;
	}

	@Override
	public Long getOne() {

		return ONE;
	}

	@Override
	public Long plus(Long left, Long right) {

		return left + right;
	}

	@Override
	public Long minus(Long left, Long right) {

		return left - right;
	}

	@Override
	public Long times(Long left, Long right) {

		return left * right;
	}

	@Override
	public Long divided(Long left, Long right) {

		return left / right;
	}

	@Override
	public Long negate(Long value) {

		return -value;
	}

	@Override
	public int compare(Long left, Long right) {

		return Long.compare(left, right);
	}
}
