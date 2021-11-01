package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.math.arithmetic.IArithmetic;

/**
 * A {@link IMatrixTraits} implementation based on {@link IArithmetic}.
 *
 * @author Oliver Richers
 */
public class NumericMatrixTraits<V> implements IMatrixTraits<V> {

	private final IArithmetic<V> arithmetic;

	public NumericMatrixTraits(IArithmetic<V> arithmetic) {

		this.arithmetic = arithmetic;
	}

	@Override
	public V getDefaultValue() {

		return arithmetic.getZero();
	}

	@Override
	public V plus(V q1, V q2) {

		return arithmetic.plus(q1, q2);
	}
}
