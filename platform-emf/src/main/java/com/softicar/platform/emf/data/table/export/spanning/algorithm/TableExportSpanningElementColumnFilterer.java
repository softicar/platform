package com.softicar.platform.emf.data.table.export.spanning.algorithm;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.data.table.export.spanning.element.ITableExportSpanningElement;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Layouts {@link ITableExportSpanningElement}s (e.g. cells in an HTML table),
 * considering colspans and rowspans, and allows for filtering out (dropping)
 * entire columns.
 * <p>
 * Uses {@link TableExportSpanningElementLayouter} to determine the initial
 * positions of the given {@link ITableExportSpanningElement}s, considering
 * colspans and rowspans in an HTML'ish way.
 *
 * @param <OT>
 *            The type of the wrapped spanning element
 * @author Alexander Schmidt
 */
public class TableExportSpanningElementColumnFilterer<OT extends ITableExportSpanningElement<?>> {

	private final TableExportSpanningElementLayouter<OT> layouter = new TableExportSpanningElementLayouter<>();

	/**
	 * @param cells
	 *            A List of {@link ITableExportSpanningElement}s representing a
	 *            row (e.g. of HTML cells).
	 * @param initialRowIndex
	 *            An initial row index offset to be applied to the result.
	 * @param retainColumns
	 *            A zero-based Set of column indexes to be retained (i.e.
	 *            columns with indexes not contained in the Set are dropped). If
	 *            the Set is null, no columns are dropped.
	 * @return A shallow matrix of {@link ITableExportSpanningElement}s. The
	 *         keys in the outer map are row indexes while the keys in the inner
	 *         map are column indexes. The respective referenced
	 *         {@link ITableExportSpanningElement} 's top-left corner is to be
	 *         placed at the corresponding indexes.
	 */
	public Map<Integer, Map<Integer, TableExportSpanningElementFilterResult<OT>>> filter(TableExportSpanningElementList<OT> cells, int initialRowIndex,
			Set<Integer> retainColumns) {

		SortedMap<Integer, SortedMap<Integer, OT>> rowMap = this.layouter.placeSpanningElements(Collections.singletonList(cells), initialRowIndex);

		RowMapFilterExecutor rowMapFilterExecutor = new RowMapFilterExecutor();

		if (rowMap.size() == 1) {
			Integer rowIndex = rowMap.keySet().iterator().next();

			rowMapFilterExecutor.processRow(rowMap, rowIndex, retainColumns);

			return rowMapFilterExecutor.getFilteredRowMap();
		} else {
			throw new SofticarDeveloperException("Expected a map size of 1 but encountered %s.", rowMap.size());
		}
	}

	/**
	 * @param rowsWithCells
	 *            A List of Lists of {@link ITableExportSpanningElement}s. Each
	 *            inner List represents a row, while each element of each inner
	 *            List is an {@link ITableExportSpanningElement} (e.g. an HTML
	 *            cell) in the respective row.
	 * @param initialRowIndex
	 *            An initial row index offset to be applied to the result.
	 * @param retainColumns
	 *            A zero-based Set of column indexes to be retained (i.e.
	 *            columns with indexes not contained in the Set are dropped). If
	 *            the Set is null, no columns are dropped.
	 * @return A shallow matrix of {@link ITableExportSpanningElement}s. The
	 *         keys in the outer map are row indexes while the keys in the inner
	 *         map are column indexes. The respective referenced
	 *         {@link ITableExportSpanningElement} 's top-left corner is to be
	 *         placed at the corresponding indexes.
	 */
	public Map<Integer, Map<Integer, TableExportSpanningElementFilterResult<OT>>> filter(TableExportSpanningElementListList<OT> rowsWithCells,
			int initialRowIndex, Set<Integer> retainColumns) {

		TableExportLib.Timing.begin("AAA row map creation");
		SortedMap<Integer, SortedMap<Integer, OT>> rowMap = this.layouter.placeSpanningElements(rowsWithCells, initialRowIndex);
		TableExportLib.Timing.end("AAA row map creation");

		RowMapFilterExecutor rowMapFilterExecutor = new RowMapFilterExecutor();

		if (!rowMap.isEmpty()) {
			Integer lastRowIndex = rowMap.lastKey();

			for (int r = 0; r <= lastRowIndex; r++) {
				rowMapFilterExecutor.processRow(rowMap, r, retainColumns);
			}
		}

		return rowMapFilterExecutor.getFilteredRowMap();
	}

	/**
	 * Creates a filtered row map by consecutive calls of
	 * {@link #processRow(Map, int, Set)}.
	 *
	 * @author Alexander Schmidt
	 */
	private class RowMapFilterExecutor {

		Map<Integer, Map<Integer, TableExportSpanningElementFilterResult<OT>>> filteredRowMap = new TreeMap<>();

		public Map<Integer, Map<Integer, TableExportSpanningElementFilterResult<OT>>> getFilteredRowMap() {

			return filteredRowMap;
		}

		public void processRow(Map<Integer, SortedMap<Integer, OT>> originalRowMap, int rowIndex, Set<Integer> retainColumns) {

			TableExportLib.Timing.begin("BAA row iteration");

			SortedMap<Integer, OT> colMap = originalRowMap.get(rowIndex);

			if (colMap != null && !colMap.isEmpty()) {
				int lastColIndex = colMap.lastKey();

				OT lastCell = null;
				int processedColsOfLastCell = 0;

				TableExportLib.Timing.begin("BBA cell iteration #1");
				for (int colIndex = 0; colIndex <= lastColIndex; colIndex++) {
					++processedColsOfLastCell;

					OT cell = colMap.get(colIndex);

					if (cell != null) {
						if (retainColumns != null && !retainColumns.contains(colIndex)) {
							cell.addNegativeColspan(1);
						}

						if (cell != lastCell) {
							processedColsOfLastCell = 0;
							lastCell = cell;
						}
					} else {
						if (lastCell != null && processedColsOfLastCell < lastCell.getColspan()) {
							lastCell.addNegativeColspan(1);
						}
					}
				}
				TableExportLib.Timing.end("BBA cell iteration #1");

				int targetColCounter = 0;

				TableExportLib.Timing.begin("BBB cell iteration #2");
				for (int colIndex = 0; colIndex <= lastColIndex; colIndex++) {
					OT cell = colMap.get(colIndex);

					if (cell != null) {
						if (cell.getEffectiveColspan() > 0) {
							if (retainColumns == null || retainColumns.contains(colIndex)) {
								if (!cell.isProcessed()) {
									Map<Integer, TableExportSpanningElementFilterResult<OT>> filteredColMap = this.filteredRowMap.get(rowIndex);
									if (filteredColMap == null) {
										filteredColMap = new TreeMap<>();
										this.filteredRowMap.put(rowIndex, filteredColMap);
									}

									TableExportSpanningElementFilterResult<OT> result = new TableExportSpanningElementFilterResult<>(cell, colIndex);

									filteredColMap.put(targetColCounter, result);
									cell.setProcessed(true);

									targetColCounter += cell.getEffectiveColspan();
								}
							}
						}
					}

					else {
						if (retainColumns == null || retainColumns.contains(colIndex)) {
							++targetColCounter;
						}
					}
				}
				TableExportLib.Timing.end("BBB cell iteration #2");
			}

			TableExportLib.Timing.end("BAA row iteration");
		}
	}

	//
	// helper classes to circumvent type erasure issues
	//

	public static class TableExportSpanningElementList<OT extends ITableExportSpanningElement<?>> extends ArrayList<OT> {
		// nothing
	}

	public static class TableExportSpanningElementListList<OT extends ITableExportSpanningElement<?>> extends ArrayList<ArrayList<OT>> {
		// nothing
	}
}
