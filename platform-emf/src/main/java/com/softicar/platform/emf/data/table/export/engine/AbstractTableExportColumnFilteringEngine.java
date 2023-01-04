package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.IDomCell;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.export.chunking.TableExportChunkBoundaryCalculator;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter.NodeConverterResult;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.element.TableExportChildElementFetcher;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportColumnConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportTableConfiguration;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeStyle;
import com.softicar.platform.emf.data.table.export.spanning.TableExportSpanFetcher;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementColumnFilterer;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementColumnFilterer.TableExportSpanningElementList;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementFilterResult;
import com.softicar.platform.emf.data.table.export.spanning.element.TableExportSpanningCell;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * An {@link AbstractTableExportSubdividedEngine} that performs span-based
 * content layouting while also allowing for subsequent column filtering.
 *
 * @param <CT>
 *            The type to which table cell contents get converted for the
 *            export. Though not mandatory, {@link TableExportTypedNodeValue} is
 *            recommended for any implementation.
 * @param <ROW>
 *            The type of the rows to be generated in the implementation
 * @param <CELL>
 *            The type of the cells to be generated in the implementation
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportColumnFilteringEngine<CT, ROW, CELL> extends AbstractTableExportSubdividedEngine<CT> {

	protected abstract void prepareExport(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<CT>> tableConfigurations);

	protected abstract void finishExport(OutputStream targetOutputStream) throws IOException;

	protected abstract ROW createAndAppendRow(int targetRowIndex, boolean isHeader);

	protected abstract CELL createAndAppendCell(ROW documentRow, int targetColIndex, boolean isHeader, NodeConverterResult<CT> convertedCellContent,
			TableExportNodeStyle exportNodeStyle);

	/**
	 * FIXME: should be moved to the super class
	 *
	 * @param targetRowIndex
	 * @return The number of rows the appended table spacer comprises.
	 */
	protected abstract int appendTableSpacerRows(int targetRowIndex);

	protected abstract void finishRow(ROW documentRow);

	protected abstract void finishCell(CELL documentCell);

	protected abstract void mergeRectangularRegion(ROW documentRow, CELL documentCell, int firstRow, int lastRow, int firstCol, int lastCol);

	// ----

	private static final int PAGEABLE_TABLE_EXPORT_CHUNK_SIZE = 2500;

	private TableExportSpanningElementColumnFilterer<TableExportSpanningCell> columnFilterer;
	private int rowsOnCurrentSheet;

	public AbstractTableExportColumnFilteringEngine(TableExportEngineConfiguration configuration,
			ITableExportEngineFactory<? extends ITableExportEngine<CT>> creatingFactory,
			TableExportNodeConverterFactoryConfiguration<CT> nodeConverterFactoryConfiguration) {

		super(configuration, creatingFactory, nodeConverterFactoryConfiguration);

		this.columnFilterer = null;
		resetRowsOnCurrentSheet();
	}

	@Override
	protected void prepare(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<CT>> tableConfigurations) {

		this.columnFilterer = new TableExportSpanningElementColumnFilterer<>();

		prepareExport(targetOutputStream, tableConfigurations);
	}

	@Override
	protected void finish(OutputStream targetOutputStream) throws IOException {

		finishExport(targetOutputStream);
	}

	@Override
	protected void appendHeader(DomTable table, TableExportColumnConfiguration<CT> columnConfiguration) {

		List<DomRow> rows = TableExportChildElementFetcher.getHeaderRows(table);

		this.rowsOnCurrentSheet += appendRows(columnConfiguration, rows, true, this.rowsOnCurrentSheet);
	}

	@Override
	protected void appendBody(DomTable table, TableExportColumnConfiguration<CT> columnConfiguration) {

		TableExportLib.assertPageableIfScrollable(table);

		if (table instanceof DomPageableTable) {
			TableExportLib.Timing.begin("310 Pre row iteration");

			DomPageableTable pTable = (DomPageableTable) table;

			DomDocumentStasher stasher = new DomDocumentStasher();

			try {
				stasher.stashDomDocumentOrThrow(new DomDocument());
				int totalNumRows = Math.max(pTable.getTotalRowCount(), 0);
				List<Pair<Integer, Integer>> chunkBoundaries =
						TableExportChunkBoundaryCalculator.calculateChunkBoundaries(totalNumRows, PAGEABLE_TABLE_EXPORT_CHUNK_SIZE);

				TableExportLib.Timing.end("310 Pre row iteration");

				if (chunkBoundaries != null) {
					for (Pair<Integer, Integer> boundary: chunkBoundaries) {
						int lowerIndex = boundary.getFirst();
						int upperIndex = boundary.getSecond();

						TableExportLib.Timing.begin("320 row fetching");

						// >>>> FIXME: potential performance bottleneck: does this still internally fetch the RS several
						// times? >>>>
						List<DomRow> rows = new ArrayList<>(pTable.getRowsUncached(lowerIndex, upperIndex));
						// <<<< FIXME: potential performance bottleneck: does this still internally fetch the RS several
						// times? <<<<

						TableExportLib.Timing.end("320 row fetching");

						TableExportLib.Timing.begin("330 row appending");
						this.rowsOnCurrentSheet += appendRows(columnConfiguration, rows, false, this.rowsOnCurrentSheet);
						TableExportLib.Timing.end("330 row appending");
					}
				}
			} finally {
				stasher.unstashDomDocumentOrThrow();
			}
		}

		// any other (non-pageable) DomTable
		else {
			List<DomRow> rows = TableExportChildElementFetcher.getBodyRows(table);

			this.rowsOnCurrentSheet += appendRows(columnConfiguration, rows, false, this.rowsOnCurrentSheet);
		}

		this.rowsOnCurrentSheet += Math.max(0, appendTableSpacerRows(this.rowsOnCurrentSheet));
	}

	protected void resetRowsOnCurrentSheet() {

		this.rowsOnCurrentSheet = 0;
	}

	/**
	 * @param columnConfiguration
	 * @param rows
	 * @param isHeader
	 * @param rowOffset
	 * @return The number rows that were appended to the output document.
	 */
	private int appendRows(TableExportColumnConfiguration<CT> columnConfiguration, List<DomRow> rows, boolean isHeader, int rowOffset) {

		if (rows != null) {
			for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
				int targetRowIndex = rowIndex + rowOffset;

				DomRow row = rows.get(rowIndex);

				TableExportLib.Timing.begin("331 createAndAppendRow");
				ROW documentRow = createAndAppendRow(targetRowIndex, isHeader);
				TableExportLib.Timing.end("331 createAndAppendRow");

				TableExportLib.Timing.begin("332 cell fetching");
				TableExportSpanningElementList<TableExportSpanningCell> cells = new TableExportSpanningElementList<>();
				List<IDomCell> cellsFromRow = TableExportChildElementFetcher.getCells(row);
				for (IDomCell cell: cellsFromRow) {
					int colspan = TableExportSpanFetcher.getColspanFromCell(cell);
					int rowspan = TableExportSpanFetcher.getRowspanFromCell(cell);

					cells.add(new TableExportSpanningCell(cell, colspan, rowspan));
				}
				TableExportLib.Timing.end("332 cell fetching");

				TableExportLib.Timing.begin("333 filtering");
				Map<Integer, Map<Integer, TableExportSpanningElementFilterResult<TableExportSpanningCell>>> filtered =
						this.columnFilterer.filter(cells, targetRowIndex, columnConfiguration.getSelectedColumnModels().keySet());
				TableExportLib.Timing.end("333 filtering");

				Map<Integer, TableExportSpanningElementFilterResult<TableExportSpanningCell>> filteredColMap = filtered.get(targetRowIndex);

				if (filteredColMap != null) {
					for (Entry<Integer, TableExportSpanningElementFilterResult<TableExportSpanningCell>> colEntry: filteredColMap.entrySet()) {

						int targetColIndex = colEntry.getKey();

						TableExportSpanningElementFilterResult<TableExportSpanningCell> spanningCellResult = colEntry.getValue();
						TableExportSpanningCell spanningCell = spanningCellResult.getSpanningElement();

						ITableExportNodeConverter<CT> nodeConverter;

						if (isHeader) {
							nodeConverter = columnConfiguration.getHeaderConverter();
						} else {
							int originalColumnIndex = spanningCellResult.getOriginalColumnIndex();
							nodeConverter = columnConfiguration.getNodeConvertersByColumn().get(originalColumnIndex);
						}

						IDomNode cellNode = spanningCell.get();
						NodeConverterResult<CT> nodeConverterResult = nodeConverter.convertNode(cellNode);

						TableExportLib.Timing.begin("334 cell creation");
						CELL documentCell =
								createAndAppendCell(documentRow, targetColIndex, isHeader, nodeConverterResult, TableExportNodeStyle.createFromNode(cellNode));
						TableExportLib.Timing.end("334 cell creation");

						int effectiveColspan = spanningCell.getEffectiveColspan();
						int rowspan = spanningCell.getRowspan();

						if (effectiveColspan > 1 || rowspan > 1) {
							int firstRow = targetRowIndex;
							int lastRow = firstRow + rowspan - 1;
							int firstCol = targetColIndex;
							int lastCol = firstCol + effectiveColspan - 1;

							TableExportLib.Timing.begin("335 merging");
							mergeRectangularRegion(documentRow, documentCell, firstRow, lastRow, firstCol, lastCol);
							TableExportLib.Timing.end("335 merging");
						}

						TableExportLib.Timing.begin("336 finish cell");
						finishCell(documentCell);
						TableExportLib.Timing.end("336 finish cell");
					}
				}

				TableExportLib.Timing.begin("339 finish row");
				finishRow(documentRow);
				TableExportLib.Timing.end("339 finish row");
			}

			return rows.size();
		}

		else {
			return 0;
		}
	}
}
