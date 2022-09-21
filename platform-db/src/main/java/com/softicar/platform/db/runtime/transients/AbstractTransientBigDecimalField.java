package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.math.BigDecimal;

/**
 * Defines a transient field with values of type {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientBigDecimalField<O extends IDbTableRow<O, ?>> extends AbstractTransientArithmeticField<O, BigDecimal> {

	protected AbstractTransientBigDecimalField() {

		super(TransientFieldValueTypes.getBigDecimal(), Arithmetics.BIG_DECIMAL);
	}
}
