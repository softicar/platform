package com.softicar.platform.emf.data.table.export.model;

import com.softicar.platform.common.container.matrix.simple.SimpleMatrix;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.IDomCell;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.emf.data.table.export.column.preselection.ITableExportColumnPreselector;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportTextOnlyNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.element.TableExportChildElementFetcher;
import com.softicar.platform.emf.data.table.export.element.TableExportNamedDomCell;
import com.softicar.platform.emf.data.table.export.spanning.TableExportSpanFetcher;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementLayouter;
import com.softicar.platform.emf.data.table.export.spanning.element.TableExportSpanningCell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;

public class TableExportColumnModelFetcher {

	private static final String HEADER_HIERARCHY_SEPARATOR = " // ";
	private static final String HEADER_CELL_CONTENT_SEPARATOR = " ";

	/**
	 * Derives a List of column names from the given {@link DomTable}.
	 * <p>
	 * Handles multi-row headers by using {@link #HEADER_HIERARCHY_SEPARATOR}
	 * for concatenation.
	 *
	 * @param table
	 * @param columnPreselector
	 * @return A List of column names of the given {@link DomTable}.
	 */
	public static List<TableExportColumnModel> fetchColumnModels(DomTable table, ITableExportColumnPreselector columnPreselector) {

		List<TableExportColumnModel> columnModels = new ArrayList<>();

		// fetch column names form header
		List<DomRow> headerRows = TableExportChildElementFetcher.getHeaderRows(table);

		if (!headerRows.isEmpty()) {
			SimpleMatrix<Integer, Integer, TableExportNamedDomCell> cellNameMatrix = createCellNameMatrix(headerRows);
			int matrixRowCount = cellNameMatrix.getRowCount();
			SortedSet<Integer> matrixCols = cellNameMatrix.getColumns();

			for (Integer col: matrixCols) {
				List<String> columnHeaderLabels = new ArrayList<>();

				List<TableExportNamedDomCell> cellsInColumn = new ArrayList<>();

				for (int row = 0; row < matrixRowCount; row++) {
					TableExportNamedDomCell namedCell = cellNameMatrix.getValue(row, col);

					if (namedCell != null) {
						if (!namedCell.isEmptyName()) {
							columnHeaderLabels.add(namedCell.getName());
						}
						cellsInColumn.add(namedCell);
					}
				}

				boolean overallEmpty = columnHeaderLabels.isEmpty();
				boolean overallPreselected = columnPreselector.isPreselected(cellsInColumn);

				String overallLabel;
				if (!overallEmpty) {
					overallLabel = Imploder.implode(columnHeaderLabels, HEADER_HIERARCHY_SEPARATOR);
				} else {
					overallLabel = createNoTitleString();
				}

				columnModels.add(new TableExportColumnModel(overallLabel, overallEmpty, overallPreselected));
			}
		}

		int numColumnsFromHeader = columnModels.size();
		int numColumnsFromBody = countTotalNumberOfBodyColumns(table);

		for (int i = numColumnsFromHeader; i < numColumnsFromBody; i++) {
			columnModels.add(new TableExportColumnModel(createNoTitleString(), true, true));
		}

		return columnModels;
	}

	private static String createNoTitleString() {

		return DomI18n.NO_TITLE.encloseInParentheses().toString();
	}

	/**
	 * TODO: simplify
	 */
	private static SimpleMatrix<Integer, Integer, TableExportNamedDomCell> createCellNameMatrix(List<DomRow> headerRows) {

		List<List<TableExportSpanningCell>> rowsWithCells = new ArrayList<>();

		for (DomRow headerRow: headerRows) {
			List<TableExportSpanningCell> headerSpanningCells = new ArrayList<>();

			for (IDomCell cell: TableExportChildElementFetcher.getCells(headerRow)) {
				headerSpanningCells
					.add(new TableExportSpanningCell(cell, TableExportSpanFetcher.getColspanFromCell(cell), TableExportSpanFetcher.getRowspanFromCell(cell)));
			}

			if (!headerSpanningCells.isEmpty()) {
				rowsWithCells.add(headerSpanningCells);
			}
		}

		Map<Integer, SortedMap<Integer, TableExportSpanningCell>> placedSpanningObjects =
				new TableExportSpanningElementLayouter<TableExportSpanningCell>().placeSpanningElements(rowsWithCells, 0);

		SimpleMatrix<Integer, Integer, TableExportNamedDomCell> result = new SimpleMatrix<>(new TableExportNamedDomCell.NamedDomCellMatrixTraits());

		ITableExportNodeConverter textConverter = new TableExportTextOnlyNodeConverterFactory(HEADER_CELL_CONTENT_SEPARATOR).create();

		for (Entry<Integer, SortedMap<Integer, TableExportSpanningCell>> outerEntry: placedSpanningObjects.entrySet()) {
			for (Entry<Integer, TableExportSpanningCell> innerEntry: outerEntry.getValue().entrySet()) {

				IDomCell domCell = innerEntry.getValue().get();
				String label = textConverter.convertNode(domCell).getContent().getString();

				result.addValue(outerEntry.getKey(), innerEntry.getKey(), new TableExportNamedDomCell(label, domCell));
			}
		}

		return result;
	}

	/**
	 * Determines the total number of columns the body of the given
	 * {@link DomTable} contains, also considering colspans.
	 * <p>
	 * For {@link DomPageableTable}s this approach results in only the currently
	 * active page being considered.
	 * <p>
	 * FIXME: Performance: Given that the table is not malformatted, the first
	 * body row should suffice to gain this information, such that there is no
	 * need to iterate over all rows. Consider this.
	 *
	 * @param table
	 * @return The total number of columns the given table contains.
	 */
	private static int countTotalNumberOfBodyColumns(DomTable table) {

		List<DomRow> rows = TableExportChildElementFetcher.getBodyRows(table);
		int numColumnsTotal = 0;

		for (DomRow row: rows) {
			int numColumnsInRow = 0;

			for (IDomCell cell: TableExportChildElementFetcher.getCells(row)) {
				numColumnsInRow += TableExportSpanFetcher.getColspanFromCell(cell);
			}

			if (numColumnsInRow > numColumnsTotal) {
				numColumnsTotal = numColumnsInRow;
			}
		}

		return numColumnsTotal;
	}
}
