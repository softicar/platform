package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Defines a transient field with values of type {@link Long}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientLongField<O extends IDbTableRow<O, ?>> extends AbstractTransientArithmeticField<O, Long> {

	protected AbstractTransientLongField() {

		super(TransientFieldValueTypes.getLong(), Arithmetics.LONG);
	}
}
