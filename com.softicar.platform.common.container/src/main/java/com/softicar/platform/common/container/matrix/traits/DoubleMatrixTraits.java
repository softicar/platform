package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.math.arithmetic.Arithmetics;

/**
 * Default {@link IMatrixTraits} for {@link Double}.
 *
 * @author Oliver Richers
 */
public class DoubleMatrixTraits extends NumericMatrixTraits<Double> {

	public DoubleMatrixTraits() {

		super(Arithmetics.DOUBLE);
	}
}
