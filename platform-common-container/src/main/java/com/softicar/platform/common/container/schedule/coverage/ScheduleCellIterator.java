package com.softicar.platform.common.container.schedule.coverage;

import com.softicar.platform.common.container.matrix.IImmutableMatrix;
import com.softicar.platform.common.math.arithmetic.IArithmetic;
import java.util.Iterator;

class ScheduleCellIterator<Row, Column, Quantity extends Comparable<Quantity>> {

	private final IImmutableMatrix<Row, Column, Quantity> schedule;
	private final IArithmetic<Quantity> arithmetic;
	private final Row row;
	private final Iterator<Column> columnIterator;
	private Quantity quantity;
	private Column column;

	public ScheduleCellIterator(IImmutableMatrix<Row, Column, Quantity> schedule, IArithmetic<Quantity> arithmetic, Row row) {

		this.schedule = schedule;
		this.arithmetic = arithmetic;
		this.row = row;
		this.columnIterator = schedule.getColumns().iterator();
		this.column = null;
		this.quantity = arithmetic.getZero();
	}

	public Row getRow() {

		return row;
	}

	public Quantity getQuantity() {

		return quantity;
	}

	public Column getColumn() {

		return column;
	}

	public boolean gatherQuantity() {

		while (arithmetic.isZero(quantity)) {
			if (columnIterator.hasNext()) {
				this.column = columnIterator.next();
				this.quantity = schedule.getValue(row, column);
			} else {
				return false;
			}
		}

		return true;
	}

	public void transferToCoverage(ScheduleCoverage<Row, Column, Quantity> coverage, Quantity quantity) {

		coverage.addUnassignedQuantity(row, column, quantity);
		subtract(quantity);
	}

	public void transferToCoverage(ScheduleCoverage<Row, Column, Quantity> coverage, Column targetColumn, Quantity quantity) {

		coverage.addQuantity(row, column, targetColumn, quantity);
		subtract(quantity);
	}

	public void subtract(Quantity quantity) {

		this.quantity = arithmetic.minus(this.quantity, quantity);
	}
}
