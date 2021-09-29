package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.math.arithmetic.Arithmetics;

/**
 * Default {@link IMatrixTraits} for {@link Long}.
 *
 * @author Oliver Richers
 */
public class LongMatrixTraits extends NumericMatrixTraits<Long> {

	public LongMatrixTraits() {

		super(Arithmetics.LONG);
	}
}
