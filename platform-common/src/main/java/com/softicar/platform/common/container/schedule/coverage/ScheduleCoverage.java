package com.softicar.platform.common.container.schedule.coverage;

import com.softicar.platform.common.container.matrix.simple.SimpleMatrix;
import com.softicar.platform.common.math.arithmetic.IArithmetic;

public class ScheduleCoverage<Row, Column, Quantity extends Comparable<Quantity>> extends SimpleMatrix<Row, Column, ScheduleCoverageEntry<Column, Quantity>> {

	private final IArithmetic<Quantity> arithmetic;

	public ScheduleCoverage(IArithmetic<Quantity> arithmetica) {

		super(new ScheduleCoverageTraits<>(arithmetica));
		this.arithmetic = arithmetica;
	}

	public void addQuantity(Row row, Column sourceColumn, Column targetColumn, Quantity quantity) {

		addValue(row, sourceColumn, new ScheduleCoverageEntry<>(arithmetic, targetColumn, quantity));
	}

	public void addUnassignedQuantity(Row row, Column sourceColumn, Quantity quantity) {

		addValue(row, sourceColumn, new ScheduleCoverageEntry<>(arithmetic, quantity));
	}
}
