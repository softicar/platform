package com.softicar.platform.common.container.schedule.coverage;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.math.arithmetic.IArithmetic;

public class ScheduleCoverageTraits<Column, Quantity extends Comparable<Quantity>> implements IMatrixTraits<ScheduleCoverageEntry<Column, Quantity>> {

	private final ScheduleCoverageEntry<Column, Quantity> DEFAULT_VALUE;
	private final IArithmetic<Quantity> arithmetic;

	public ScheduleCoverageTraits(IArithmetic<Quantity> arithmetic) {

		this.arithmetic = arithmetic;
		this.DEFAULT_VALUE = new ScheduleCoverageEntry<>(arithmetic);
	}

	@Override
	public ScheduleCoverageEntry<Column, Quantity> getDefaultValue() {

		return DEFAULT_VALUE;
	}

	@Override
	public ScheduleCoverageEntry<Column, Quantity> plus(ScheduleCoverageEntry<Column, Quantity> entry1, ScheduleCoverageEntry<Column, Quantity> entry2) {

		return new ScheduleCoverageEntry<>(arithmetic, entry1, entry2);
	}
}
