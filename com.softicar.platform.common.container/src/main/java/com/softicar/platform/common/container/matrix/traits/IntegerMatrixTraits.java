package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.math.arithmetic.Arithmetics;

/**
 * Default {@link IMatrixTraits} for {@link Integer}.
 *
 * @author Oliver Richers
 */
public class IntegerMatrixTraits extends NumericMatrixTraits<Integer> {

	public IntegerMatrixTraits() {

		super(Arithmetics.INTEGER);
	}
}
