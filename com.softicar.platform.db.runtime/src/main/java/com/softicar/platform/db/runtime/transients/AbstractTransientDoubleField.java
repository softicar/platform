package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Defines a transient field with values of type {@link Double}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientDoubleField<O extends IDbTableRow<O, ?>> extends AbstractTransientArithmeticField<O, Double> {

	protected AbstractTransientDoubleField() {

		super(TransientFieldValueTypes.getDouble(), Arithmetics.DOUBLE);
	}
}
