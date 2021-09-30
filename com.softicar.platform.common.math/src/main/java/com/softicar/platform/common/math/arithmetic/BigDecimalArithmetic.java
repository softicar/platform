package com.softicar.platform.common.math.arithmetic;

import java.math.BigDecimal;

/**
 * Implementation of {@link IArithmetic} for {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
class BigDecimalArithmetic implements IArithmetic<BigDecimal> {

	@Override
	public BigDecimal getZero() {

		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getOne() {

		return BigDecimal.ONE;
	}

	@Override
	public BigDecimal plus(BigDecimal left, BigDecimal right) {

		return left.add(right);
	}

	@Override
	public BigDecimal minus(BigDecimal left, BigDecimal right) {

		return left.subtract(right);
	}

	@Override
	public BigDecimal times(BigDecimal left, BigDecimal right) {

		return left.multiply(right);
	}

	@Override
	public BigDecimal divided(BigDecimal left, BigDecimal right) {

		return left.divide(right);
	}

	@Override
	public BigDecimal negate(BigDecimal value) {

		return value.negate();
	}

	@Override
	public int compare(BigDecimal left, BigDecimal right) {

		return left.compareTo(right);
	}
}
