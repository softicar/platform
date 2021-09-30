package com.softicar.platform.common.container.schedule.coverage;

import com.softicar.platform.common.container.map.number.INumberMap;
import com.softicar.platform.common.container.map.number.NumberMap;
import com.softicar.platform.common.math.arithmetic.IArithmetic;

public class ScheduleCoverageEntry<Column, Quantity extends Comparable<Quantity>> {

	private final INumberMap<Column, Quantity> columnQuantities;
	private final Quantity unassignedQuantity;

	public ScheduleCoverageEntry(IArithmetic<Quantity> arithmetic) {

		this.columnQuantities = new NumberMap<>(arithmetic);
		this.unassignedQuantity = arithmetic.getZero();
	}

	public ScheduleCoverageEntry(IArithmetic<Quantity> arithmetic, Quantity unassignedQuantity) {

		this.columnQuantities = new NumberMap<>(arithmetic);
		this.unassignedQuantity = unassignedQuantity;
	}

	public ScheduleCoverageEntry(IArithmetic<Quantity> arithmetic, Column coveringColumn, Quantity quantity) {

		this(arithmetic);
		this.columnQuantities.add(coveringColumn, quantity);
	}

	public ScheduleCoverageEntry(IArithmetic<Quantity> arithmetic, ScheduleCoverageEntry<Column, Quantity> entry1,
			ScheduleCoverageEntry<Column, Quantity> entry2) {

		this(arithmetic, arithmetic.plus(entry1.getUnassignedQuantity(), entry2.getUnassignedQuantity()));
		this.columnQuantities.add(entry1.getColumnQuantities());
		this.columnQuantities.add(entry2.getColumnQuantities());
	}

	public INumberMap<Column, Quantity> getColumnQuantities() {

		return columnQuantities;
	}

	public Quantity getUnassignedQuantity() {

		return unassignedQuantity;
	}

	public Quantity getTotalQuantity() {

		Quantity totalQuantity = unassignedQuantity;
		IArithmetic<Quantity> arithmetic = columnQuantities.getArithmetic();
		for (Column column: columnQuantities.keySet()) {
			totalQuantity = arithmetic.plus(totalQuantity, columnQuantities.getOrZero(column));
		}
		return totalQuantity;
	}
}
