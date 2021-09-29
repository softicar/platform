package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.math.arithmetic.IArithmetic;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Base class for all transient fields of arithmetic type.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientArithmeticField<O extends IDbTableRow<O, ?>, V> extends AbstractTransientAccumulativeField<O, V, V> {

	private final IArithmetic<V> arithmetic;

	public AbstractTransientArithmeticField(ITransientFieldValueType<V> valueType, IArithmetic<V> arithmetic) {

		super(valueType);

		this.arithmetic = arithmetic;
	}

	@Override
	protected final V getDefaultValue() {

		return arithmetic.getZero();
	}

	@Override
	protected final V combine(V left, V right) {

		return arithmetic.plus(left, right);
	}
}
