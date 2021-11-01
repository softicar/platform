package com.softicar.platform.emf.data.table.export.spanning.algorithm;

import com.softicar.platform.common.container.matrix.simple.SimpleMatrix;
import com.softicar.platform.common.container.matrix.traits.BooleanMatrixTraits;
import com.softicar.platform.emf.data.table.export.spanning.element.ITableExportSpanningElement;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implements a HTML-like placing algorithm for
 * {@link ITableExportSpanningElement}s (i.e. elements that have colspans and
 * rowspans, like HTML table cells).
 *
 * @param <OT>
 *            The type of the wrapped spanning element
 * @author Alexander Schmidt
 */
public class TableExportSpanningElementLayouter<OT extends ITableExportSpanningElement<?>> {

	// (rowIndex, colIndex) -> blocked, if true
	private final SimpleMatrix<Integer, Integer, Boolean> blockMatrix = new SimpleMatrix<>(new BooleanMatrixTraits());

	/**
	 * Determines the starting row and column indexes for multiple rows, each
	 * represented by a List of {@link ITableExportSpanningElement}s.
	 *
	 * @param rowsWithCells
	 * @param initialRowIndex
	 * @return A Map from target row index to (target column index of the
	 *         {@link ITableExportSpanningElement} to put in the addressed
	 *         cell).
	 */
	public SortedMap<Integer, SortedMap<Integer, OT>> placeSpanningElements(List<? extends List<OT>> rowsWithCells, int initialRowIndex) {

		SortedMap<Integer, SortedMap<Integer, OT>> rowMap = new TreeMap<>();

		for (int r = 0; r < rowsWithCells.size(); r++) {
			int targetRowIndex = initialRowIndex + r;

			rowMap.put(targetRowIndex, processRow(rowsWithCells.get(r), targetRowIndex));
		}

		return rowMap;
	}

	/**
	 * Determines the starting column indexes for one single row, represented by
	 * a List of {@link ITableExportSpanningElement}s (e.g. Lists of cells).
	 * <p>
	 * Handles rowspans by statefully maintaining a matrix of blocked cells for
	 * subsequent calls of {@link #placeSpanningElements(List, int)}.
	 *
	 * @param cells
	 * @param rowIndex
	 * @return A Map from target column index to the
	 *         {@link ITableExportSpanningElement} to put in the corresponding
	 *         cell.
	 */
	private SortedMap<Integer, OT> processRow(List<OT> cells, int rowIndex) {

		SortedMap<Integer, OT> rowMap = new TreeMap<>();

		int targetColCounter = 0;
		for (OT cell: cells) {
			// find the next non-blocked cell in the current row
			while (this.blockMatrix.getValue(rowIndex, targetColCounter)) {
				++targetColCounter;
			}

			int colspan = cell.getColspan();
			int rowspan = cell.getRowspan();

			for (int i = 0; i < colspan; i++) {
				if (!this.blockMatrix.getValue(rowIndex, targetColCounter)) {
					rowMap.put(targetColCounter, cell);
				}

				if (rowspan > 1) {
					for (int j = 1; j < rowspan; j++) {
						this.blockMatrix.setValue(rowIndex + j, targetColCounter, true);
					}
				}

				++targetColCounter;
			}
		}

		// allow for block matrix entries of processed rows to be GC'ed
		this.blockMatrix.removeRow(rowIndex);

		return rowMap;
	}
}
