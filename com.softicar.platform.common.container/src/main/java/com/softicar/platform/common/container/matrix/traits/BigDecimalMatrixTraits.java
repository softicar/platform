package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.math.BigDecimal;

/**
 * Default {@link IMatrixTraits} for {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
public class BigDecimalMatrixTraits extends NumericMatrixTraits<BigDecimal> {

	public BigDecimalMatrixTraits() {

		super(Arithmetics.BIG_DECIMAL);
	}
}
