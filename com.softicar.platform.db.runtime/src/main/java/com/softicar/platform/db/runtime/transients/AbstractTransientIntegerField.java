package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Defines a transient field with values of type {@link Integer}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientIntegerField<O extends IDbTableRow<O, ?>> extends AbstractTransientArithmeticField<O, Integer> {

	protected AbstractTransientIntegerField() {

		super(TransientFieldValueTypes.getInteger(), Arithmetics.INTEGER);
	}
}
