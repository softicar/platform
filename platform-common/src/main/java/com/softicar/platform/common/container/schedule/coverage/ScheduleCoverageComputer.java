package com.softicar.platform.common.container.schedule.coverage;

import com.softicar.platform.common.container.matrix.IImmutableMatrix;
import com.softicar.platform.common.math.arithmetic.IArithmetic;
import java.util.Set;
import java.util.TreeSet;

/**
 * Computes the coverage of a work schedule over a to-do schedule.
 *
 * @author Oliver Richers
 */
public class ScheduleCoverageComputer<Row, Column, Quantity extends Comparable<Quantity>> {

	protected final IImmutableMatrix<Row, Column, Quantity> todoSchedule;
	protected final IImmutableMatrix<Row, Column, Quantity> workSchedule;
	protected final IArithmetic<Quantity> arithmetic;
	protected final ScheduleCoverage<Row, Column, Quantity> todoCoverage;
	protected final ScheduleCoverage<Row, Column, Quantity> workCoverage;

	public ScheduleCoverageComputer(IImmutableMatrix<Row, Column, Quantity> todoSchedule, IImmutableMatrix<Row, Column, Quantity> workSchedule,
			IArithmetic<Quantity> arithmetic) {

		this.todoSchedule = todoSchedule;
		this.workSchedule = workSchedule;
		this.arithmetic = arithmetic;
		this.todoCoverage = new ScheduleCoverage<>(arithmetic);
		this.workCoverage = new ScheduleCoverage<>(arithmetic);
	}

	public Set<Row> getAllRows() {

		Set<Row> rows = new TreeSet<>();
		rows.addAll(todoSchedule.getRows());
		rows.addAll(workSchedule.getRows());
		return rows;
	}

	public ScheduleCoverageComputer<Row, Column, Quantity> compute() {

		for (Row row: getAllRows()) {
			new RowCoverageComputer(row).compute();
		}

		return this;
	}

	public void computeRow(Row row) {

		this.todoCoverage.resetRow(row);
		this.workCoverage.resetRow(row);
		new RowCoverageComputer(row).compute();
	}

	public ScheduleCoverage<Row, Column, Quantity> getTodoCoverage() {

		return todoCoverage;
	}

	public ScheduleCoverage<Row, Column, Quantity> getWorkCoverage() {

		return workCoverage;
	}

	private class RowCoverageComputer {

		private final ScheduleCellIterator<Row, Column, Quantity> todoIterator;
		private final ScheduleCellIterator<Row, Column, Quantity> workIterator;
		private Quantity backlog;

		public RowCoverageComputer(Row row) {

			this.todoIterator = new ScheduleCellIterator<>(todoSchedule, arithmetic, row);
			this.workIterator = new ScheduleCellIterator<>(workSchedule, arithmetic, row);
			this.backlog = arithmetic.getZero();
		}

		public void compute() {

			while (todoIterator.gatherQuantity() && workIterator.gatherQuantity()) {
				Quantity todoQuantity = todoIterator.getQuantity();
				Quantity workQuantity = workIterator.getQuantity();
				if (isNegative(todoQuantity)) {
					handleNegativeTodoQuantity();
				} else if (isNegative(workQuantity)) {
					handleNegativeWorkQuantity(workQuantity);
				} else if (isPositive(backlog)) {
					handleBacklog();
				} else {
					Quantity minimum = getMinimum(todoQuantity, workQuantity);
					todoIterator.transferToCoverage(todoCoverage, workIterator.getColumn(), minimum);
					workIterator.transferToCoverage(workCoverage, todoIterator.getColumn(), minimum);
				}
			}

			// gather remaining to-do and work quantities
			gatherRemainingQuantities(todoIterator, todoCoverage);
			gatherRemainingQuantities(workIterator, workCoverage);
		}

		private void handleNegativeTodoQuantity() {

			throw new IllegalArgumentException(
				String
					.format(
						"Got negative to-do quantity '%s' for row '%s' and column '%s'. Quantities of the to-do schedule may not be negative.",
						todoIterator.getQuantity(),
						todoIterator.getRow(),
						todoIterator.getColumn()));
		}

		private void handleNegativeWorkQuantity(Quantity workQuantity) {

			backlog = arithmetic.plus(backlog, arithmetic.negate(workQuantity));
			workIterator.transferToCoverage(workCoverage, workQuantity);
		}

		private void handleBacklog() {

			Quantity minimum = getMinimum(backlog, workIterator.getQuantity());
			backlog = arithmetic.minus(backlog, minimum);
			workIterator.transferToCoverage(workCoverage, minimum);
		}

		private void gatherRemainingQuantities(ScheduleCellIterator<Row, Column, Quantity> iterator, ScheduleCoverage<Row, Column, Quantity> coverage) {

			while (iterator.gatherQuantity()) {
				iterator.transferToCoverage(coverage, iterator.getQuantity());
			}
		}

		private boolean isPositive(Quantity quantity) {

			return arithmetic.isLess(arithmetic.getZero(), quantity);
		}

		private boolean isNegative(Quantity quantity) {

			return arithmetic.isLess(quantity, arithmetic.getZero());
		}

		private Quantity getMinimum(Quantity a, Quantity b) {

			return arithmetic.isLess(a, b)? a : b;
		}
	}
}
