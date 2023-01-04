package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.zip.ZipLib;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.IDomCell;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.emf.data.table.export.chunking.TableExportChunkBoundaryCalculator;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter.NodeConverterResult;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportNodeConverterFactoryValueSelectBuilder;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportNodeConverterFactoryWrapper;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.conversion.factory.selection.TableExportNodeConverterFactorySelectionModel;
import com.softicar.platform.emf.data.table.export.element.TableExportChildElementFetcher;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportColumnConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportTableConfiguration;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.file.name.ITableExportFileNameCreator;
import com.softicar.platform.emf.data.table.export.file.name.TableExportDefaultFileNameCreator;
import com.softicar.platform.emf.data.table.export.file.name.TableExportFileNameWithOmittableSuffix;
import com.softicar.platform.emf.data.table.export.file.name.TableExportFileTimestampMode;
import com.softicar.platform.emf.data.table.export.model.TableExportColumnModel;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeStyle;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult.Level;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import com.softicar.platform.emf.data.table.export.spanning.TableExportSpanFetcher;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementColumnFilterer;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementColumnFilterer.TableExportSpanningElementList;
import com.softicar.platform.emf.data.table.export.spanning.algorithm.TableExportSpanningElementFilterResult;
import com.softicar.platform.emf.data.table.export.spanning.element.TableExportSpanningCell;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Base of all {@link ITableExportEngine} implementations.
 * <p>
 * Performs span-based content layouting while also allowing for subsequent
 * column filtering.
 * <p>
 * Note: Assumes the database being configured for a "repeatable reads" style
 * transaction isolation level.
 *
 * @param <ROW>
 *            The type of the rows to be generated in the implementation
 * @param <CELL>
 *            The type of the cells to be generated in the implementation
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportEngine<ROW, CELL> implements ITableExportEngine {

	private static final int PAGEABLE_TABLE_EXPORT_CHUNK_SIZE = 2500;

	private final ITableExportEngineFactory<? extends ITableExportEngine> creatingFactory;
	private final TableExportNodeConverterFactoryConfiguration nodeConverterFactoryConfiguration;
	private final TableExportNodeConverterFactorySelectionModel nodeConverterFactorySelectionModel;
	private final ITableExportFileNameCreator fileNameCreator;

	private String fileNamePrefix = null;
	private String fileExtension = null;
	private boolean appendTimestamp = false;
	private boolean enableDeflateCompression = true;
	private Supplier<OutputStream> outputStreamSupplierFunction = null;

	private TableExportSpanningElementColumnFilterer<TableExportSpanningCell> columnFilterer;
	private int rowsOnCurrentSheet;

	public AbstractTableExportEngine(TableExportEngineConfiguration configuration, ITableExportEngineFactory<? extends ITableExportEngine> creatingFactory,
			TableExportNodeConverterFactoryConfiguration nodeConverterFactoryConfiguration) {

		Objects.requireNonNull(configuration);

		this.fileExtension = configuration.getFileNameExtension();
		this.appendTimestamp = configuration.isAppendTimestamp();
		this.enableDeflateCompression = configuration.isCompressed();
		this.creatingFactory = creatingFactory;
		this.nodeConverterFactoryConfiguration = nodeConverterFactoryConfiguration;
		this.nodeConverterFactorySelectionModel = new TableExportNodeConverterFactorySelectionModel(nodeConverterFactoryConfiguration);
		this.fileNameCreator = new TableExportDefaultFileNameCreator();

		this.columnFilterer = null;
		resetRowsOnCurrentSheet();
	}

	protected abstract void prepareExport(OutputStream targetOutputStream, Collection<TableExportTableConfiguration> tableConfigurations);

	protected abstract void finishExport(OutputStream targetOutputStream) throws IOException;

	protected abstract void prepareTable(TableExportTableConfiguration tableConfiguration);

	protected abstract void finishTable();

	protected abstract ROW createAndAppendRow(int targetRowIndex, boolean isHeader);

	protected abstract void finishRow(ROW documentRow);

	/**
	 * @param targetRowIndex
	 * @return The number of rows the appended table spacer comprises.
	 */
	protected abstract int appendTableSpacerRows(int targetRowIndex);

	protected abstract CELL createAndAppendCell(ROW documentRow, int targetColIndex, boolean isHeader, NodeConverterResult convertedCellContent,
			TableExportNodeStyle exportNodeStyle);

	protected abstract void finishCell(CELL documentCell);

	protected abstract void mergeRectangularRegion(ROW documentRow, CELL documentCell, int firstRow, int lastRow, int firstCol, int lastCol);

	@Override
	public void export(DomTable table) {

		export(new DomTable[] { table });
	}

	@Override
	public void export(DomTable...tables) {

		List<TableExportTableModel> tableModels = Arrays//
			.asList(tables)
			.stream()
			.map(TableExportTableModel::new)
			.collect(Collectors.toList());

		export(tableModels);
	}

	@Override
	public void export(TableExportTableModel...tableModels) {

		export(Arrays.asList(tableModels));
	}

	@Override
	public void export(Collection<TableExportTableModel> tableModels) {

		if (!checkPreconditions(tableModels).getAllByLevel(Level.ERROR).isEmpty()) {
			throw new SofticarUserException(DomI18n.TABLE_EXPORT_PRECONDITIONS_WERE_NOT_FULFILLED);
		}

		try {
			executeExport(tableModels);
		} catch (Exception exception) {
			throw new SofticarUserException(exception, DomI18n.TABLE_EXPORT_FAILED);
		}
	}

	@Override
	public ITableExportEngine setFileNamePrefix(String fileNamepPrefix) {

		this.fileNamePrefix = fileNamepPrefix.trim();
		return this;
	}

	@Override
	public ITableExportEngine setAppendTimestamp(boolean appendTimestamp) {

		this.appendTimestamp = appendTimestamp;
		return this;
	}

	@Override
	public ITableExportEngine setEnableDeflateCompression(boolean enableDeflateCompression) {

		this.enableDeflateCompression = enableDeflateCompression;
		return this;
	}

	@Override
	public ITableExportEngine setOutputStreamCreationFunction(Supplier<OutputStream> outputStreamSupplierFunction) {

		this.outputStreamSupplierFunction = outputStreamSupplierFunction;
		return this;
	}

	@Override
	public ITableExportEngineFactory<? extends ITableExportEngine> getCreatingFactory() {

		return creatingFactory;
	}

	@Override
	public TableExportNodeConverterFactoryConfiguration getNodeConverterFactoryConfiguration() {

		return nodeConverterFactoryConfiguration;
	}

	@Override
	public TableExportNodeConverterFactorySelectionModel getNodeConverterFactorySelectionModel() {

		return this.nodeConverterFactorySelectionModel;
	}

	@Override
	public DomSimpleValueSelectBuilder<TableExportNodeConverterFactoryWrapper> createConverterFactoryValueSelectBuiler(int targetColumn,
			DomParentElement converterFactoryHelpElementContainer) {

		return new TableExportNodeConverterFactoryValueSelectBuilder(
			this.nodeConverterFactoryConfiguration,
			this.nodeConverterFactorySelectionModel,
			targetColumn,
			converterFactoryHelpElementContainer);
	}

	protected final void resetRowsOnCurrentSheet() {

		this.rowsOnCurrentSheet = 0;
	}

	private TableExportPreconditionResultContainer checkPreconditions(Collection<TableExportTableModel> tableModels) {

		var resultContainer = getCreatingFactory().checkPreconditions(tableModels);
		return Optional.ofNullable(resultContainer).orElse(new TableExportPreconditionResultContainer());
	}

	private void executeExport(Collection<TableExportTableModel> tableModels) {

		// -------- gather table configurations -------- //

		List<TableExportTableConfiguration> tableConfigurations = new ArrayList<>();

		for (TableExportTableModel tableModel: tableModels) {
			Map<Integer, TableExportColumnModel> selectedColumnModels = tableModel.getSelectedColumnModels();

			ITableExportNodeConverter headerConverter = getNodeConverterFactoryConfiguration().getHeaderFactory().create();
			Map<Integer, ITableExportNodeConverter> nodeConvertersByColumn =
					getNodeConverterFactorySelectionModel().fetchNodeConvertersByColumn(selectedColumnModels.keySet());
			TableExportColumnConfiguration columnConfiguration =
					new TableExportColumnConfiguration(selectedColumnModels, nodeConvertersByColumn, headerConverter);
			tableConfigurations.add(new TableExportTableConfiguration(tableModel, columnConfiguration));
		}

		// -------- prepare exports for all tables -------- //

		TableExportFileNameWithOmittableSuffix outputFileName;
		byte[] bytes;

		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			this.columnFilterer = new TableExportSpanningElementColumnFilterer<>();
			prepareExport(buffer, tableConfigurations);

			// -------- execute per-table export implementations -------- //

			for (TableExportTableConfiguration tableConfiguration: tableConfigurations) {
				// Run any implementation's queries in a transaction to avoid rows being dropped or duplicated
				// when paging an SQL resultset based DomPageableTable, in case the underlying database table gets
				// altered during the export process. Requires a "repeatable reads" style transaction isolation level.
				try (AutoCloseable transaction = tableConfiguration.startTransaction()) {
					var table = tableConfiguration.getTable();
					var columnConfiguration = tableConfiguration.getColumnConfiguration();
					prepareTable(tableConfiguration);
					appendHeader(table, columnConfiguration);
					appendBody(table, columnConfiguration);
					finishTable();
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			finishExport(buffer);

			outputFileName = this.fileNameCreator
				.createFileName(
					this.fileNamePrefix,
					this.fileExtension,
					this.appendTimestamp? TableExportFileTimestampMode.CURRENT_TIME : TableExportFileTimestampMode.NONE,
					enableDeflateCompression);

			if (this.enableDeflateCompression) {
				bytes = ZipLib.zipSingleFile(buffer, outputFileName.getFileName(true), false);
			} else {
				bytes = buffer.toByteArray();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		String fileName = outputFileName.getFileName(false);

		if (this.outputStreamSupplierFunction == null) {
			this.outputStreamSupplierFunction = () -> CurrentDomDocument//
				.get()
				.getEngine()
				.createExport()
				.setFilename(fileName)
				.setMimeType(MimeType.APPLICATION_OCTET_STREAM)
				.openOutputStream();
		}

		try (OutputStream targetOutputStream = this.outputStreamSupplierFunction.get()) {
			targetOutputStream.write(bytes);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void appendHeader(DomTable table, TableExportColumnConfiguration columnConfiguration) {

		List<DomRow> rows = TableExportChildElementFetcher.getHeaderRows(table);
		this.rowsOnCurrentSheet += appendRows(columnConfiguration, rows, true, this.rowsOnCurrentSheet);
	}

	private void appendBody(DomTable table, TableExportColumnConfiguration columnConfiguration) {

		TableExportLib.assertPageableIfScrollable(table);

		if (table instanceof DomPageableTable) {
			DomPageableTable pageableTable = (DomPageableTable) table;

			DomDocumentStasher stasher = new DomDocumentStasher();

			try {
				stasher.stashDomDocumentOrThrow(new DomDocument());
				int totalNumRows = Math.max(pageableTable.getTotalRowCount(), 0);
				List<Pair<Integer, Integer>> chunkBoundaries =
						TableExportChunkBoundaryCalculator.calculateChunkBoundaries(totalNumRows, PAGEABLE_TABLE_EXPORT_CHUNK_SIZE);

				if (chunkBoundaries != null) {
					for (Pair<Integer, Integer> boundary: chunkBoundaries) {
						int lowerIndex = boundary.getFirst();
						int upperIndex = boundary.getSecond();

						// >>>> FIXME: potential performance bottleneck: does this still internally fetch the RS several
						// times? >>>>
						List<DomRow> rows = new ArrayList<>(pageableTable.getRowsUncached(lowerIndex, upperIndex));
						// <<<< FIXME: potential performance bottleneck: does this still internally fetch the RS several
						// times? <<<<

						this.rowsOnCurrentSheet += appendRows(columnConfiguration, rows, false, this.rowsOnCurrentSheet);
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

	/**
	 * @param columnConfiguration
	 * @param rows
	 * @param isHeader
	 * @param rowOffset
	 * @return The number rows that were appended to the output document.
	 */
	private int appendRows(TableExportColumnConfiguration columnConfiguration, List<DomRow> rows, boolean isHeader, int rowOffset) {

		if (rows != null) {
			for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
				int targetRowIndex = rowIndex + rowOffset;

				DomRow row = rows.get(rowIndex);
				ROW documentRow = createAndAppendRow(targetRowIndex, isHeader);

				TableExportSpanningElementList<TableExportSpanningCell> cells = new TableExportSpanningElementList<>();
				for (IDomCell cell: TableExportChildElementFetcher.getCells(row)) {
					int colspan = TableExportSpanFetcher.getColspanFromCell(cell);
					int rowspan = TableExportSpanFetcher.getRowspanFromCell(cell);

					cells.add(new TableExportSpanningCell(cell, colspan, rowspan));
				}

				Map<Integer, Map<Integer, TableExportSpanningElementFilterResult<TableExportSpanningCell>>> filtered =
						this.columnFilterer.filter(cells, targetRowIndex, columnConfiguration.getSelectedColumnModels().keySet());

				Map<Integer, TableExportSpanningElementFilterResult<TableExportSpanningCell>> filteredColMap = filtered.get(targetRowIndex);

				if (filteredColMap != null) {
					for (Entry<Integer, TableExportSpanningElementFilterResult<TableExportSpanningCell>> colEntry: filteredColMap.entrySet()) {

						int targetColIndex = colEntry.getKey();

						TableExportSpanningElementFilterResult<TableExportSpanningCell> spanningCellResult = colEntry.getValue();
						TableExportSpanningCell spanningCell = spanningCellResult.getSpanningElement();

						ITableExportNodeConverter nodeConverter;

						if (isHeader) {
							nodeConverter = columnConfiguration.getHeaderConverter();
						} else {
							int originalColumnIndex = spanningCellResult.getOriginalColumnIndex();
							nodeConverter = columnConfiguration.getNodeConvertersByColumn().get(originalColumnIndex);
						}

						IDomNode cellNode = spanningCell.get();
						NodeConverterResult nodeConverterResult = nodeConverter.convertNode(cellNode);

						CELL documentCell =
								createAndAppendCell(documentRow, targetColIndex, isHeader, nodeConverterResult, TableExportNodeStyle.createFromNode(cellNode));

						int effectiveColspan = spanningCell.getEffectiveColspan();
						int rowspan = spanningCell.getRowspan();

						if (effectiveColspan > 1 || rowspan > 1) {
							int firstRow = targetRowIndex;
							int lastRow = firstRow + rowspan - 1;
							int firstCol = targetColIndex;
							int lastCol = firstCol + effectiveColspan - 1;

							mergeRectangularRegion(documentRow, documentCell, firstRow, lastRow, firstCol, lastCol);
						}

						finishCell(documentCell);
					}
				}

				finishRow(documentRow);
			}

			return rows.size();
		}

		else {
			return 0;
		}
	}
}
