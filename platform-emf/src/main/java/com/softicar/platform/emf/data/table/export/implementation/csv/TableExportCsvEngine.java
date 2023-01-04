package com.softicar.platform.emf.data.table.export.implementation.csv;

import com.softicar.platform.common.string.unicode.UnicodeEnum;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter.NodeConverterResult;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportDefaultNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportStrictNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportTextOnlyNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.engine.AbstractTableExportEngine;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportTableConfiguration;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.implementation.csv.TableExportCsvEngine.CsvCell;
import com.softicar.platform.emf.data.table.export.implementation.csv.TableExportCsvEngine.CsvRow;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeStyle;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Exports {@link DomTable}s to CSV format.
 *
 * @author Alexander Schmidt
 */
public class TableExportCsvEngine extends AbstractTableExportEngine<TableExportTypedNodeValue, CsvRow, CsvCell> {

	private static final String ALL_COMMA_SEPARATORS = "\\,\t;";
	private static final char QUOTATION_CHAR = '"';
	private static final char NEWLINE_CHAR = '\n';
	private static final Pattern QUOTATION_DETECTION_PATTERN = Pattern.compile("[" + ALL_COMMA_SEPARATORS + QUOTATION_CHAR + NEWLINE_CHAR + "]");
	private static final Charset OUTPUT_FILE_CHARSET = StandardCharsets.UTF_8;
	private static final int NUM_TABLE_SPACER_ROWS = 2;

	private final char outputSeparator;
	private final boolean prependByteOrderMark;
	private PrintWriter printWriter;

	/**
	 * @param configuration
	 * @param creatingFactory
	 * @param outputSeparator
	 *            The character separating each pair of values. Note: For MS
	 *            Excel, that must be ';'.
	 * @param prependByteOrderMark
	 */
	public TableExportCsvEngine(TableExportEngineConfiguration configuration,
			ITableExportEngineFactory<? extends ITableExportEngine<TableExportTypedNodeValue>> creatingFactory, char outputSeparator,
			boolean prependByteOrderMark) {

		super(
			configuration, //
			creatingFactory,
			new TableExportNodeConverterFactoryConfiguration<>(//
				new TableExportTextOnlyNodeConverterFactory(),
				new TableExportDefaultNodeConverterFactory()//
			).addAvailableFactories(new TableExportStrictNodeConverterFactory(), new TableExportTextOnlyNodeConverterFactory())//
		);

		this.outputSeparator = outputSeparator;
		this.prependByteOrderMark = prependByteOrderMark;
	}

	@Override
	protected void prepareExport(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<TableExportTypedNodeValue>> tableConfigurations) {

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(targetOutputStream, OUTPUT_FILE_CHARSET);

		this.printWriter = new PrintWriter(outputStreamWriter);

		// add a Unicode "Byte Order Mark" character to allow for Excel 2003 compatibility
		if (this.prependByteOrderMark) {
			this.printWriter.print(UnicodeEnum.BYTE_ORDER_MARK.toChar());
		}
	}

	@Override
	protected void finishExport(OutputStream targetOutputStream) {

		this.printWriter.close();
	}

	@Override
	protected void prepareTable(TableExportTableConfiguration<TableExportTypedNodeValue> tableConfiguration) {

		// nothing to do
	}

	@Override
	protected CsvRow createAndAppendRow(int targetRowIndex, boolean isHeader) {

		return new CsvRow();
	}

	@Override
	protected CsvCell createAndAppendCell(CsvRow documentRow, int targetColIndex, boolean isHeader,
			NodeConverterResult<TableExportTypedNodeValue> convertedCellContent, TableExportNodeStyle exportNodeStyle) {

		return documentRow.addCell(targetColIndex, new CsvCell(convertedCellContent.getContent()));
	}

	@Override
	protected int appendTableSpacerRows(int targetRowIndex) {

		for (int i = 0; i < NUM_TABLE_SPACER_ROWS; i++) {
			finishRow(new CsvRow());
		}

		return NUM_TABLE_SPACER_ROWS;
	}

	@Override
	protected void finishTable() {

		// nothing to do
	}

	@Override
	protected void finishRow(CsvRow documentRow) {

		String rowString = getStringFromRow(documentRow);

		this.printWriter.println(rowString);
	}

	@Override
	protected void finishCell(CsvCell documentCell) {

		// nothing to do
	}

	@Override
	protected void mergeRectangularRegion(CsvRow documentRow, CsvCell documentCell, int firstRow, int lastRow, int firstCol, int lastCol) {

		// nothing to do
	}

	private String getStringFromRow(CsvRow row) {

		StringBuilder output = new StringBuilder();

		SortedMap<Integer, CsvCell> cellMap = row.getCellMap();

		if (!cellMap.isEmpty()) {
			Integer lastKey = cellMap.lastKey();

			for (int i = 0; i <= lastKey; i++) {
				CsvCell cell = cellMap.get(i);

				if (cell != null) {
					TableExportTypedNodeValue content = cell.getContent();
					Object contentValue = content.getValue();

					String contentString = "";
					if (contentValue != null) {
						contentString += contentValue;
					}

					output.append(prepareContent(contentString));
				}

				if (i < lastKey) {
					output.append(this.outputSeparator);
				}
			}
		}

		return output.toString();
	}

	/**
	 * Prepares the given content String according to the following rules:
	 * <p>
	 * 1)-If the value contains a comma, newline or double quote, then the
	 * String value should be returned enclosed in double quotes.<br>
	 * 2)-Any double quote characters in the value should be escaped with
	 * another double quote.<br>
	 * 3)-If the value does not contain a comma, newline or double quote, then
	 * the String value should be returned unchanged.
	 * <p>
	 * see this:
	 * http://stackoverflow.com/questions/12473480/how-should-i-escape-
	 * commas-and-speech-marks-in-csv-files-so-they-work-in-excel
	 *
	 * @param content
	 * @return The prepared content String, quoted if necessary.
	 */
	private static String prepareContent(String content) {

		if (QUOTATION_DETECTION_PATTERN.matcher(content).find()) {
			return new StringBuilder()
				.append(QUOTATION_CHAR)
				.append(content.replaceAll("" + QUOTATION_CHAR, "" + QUOTATION_CHAR + QUOTATION_CHAR))
				.append(QUOTATION_CHAR)
				.toString();
		}

		else {
			return content;
		}
	}

	// ----

	public static class CsvRow {

		private final SortedMap<Integer, CsvCell> cellMap = new TreeMap<>();

		public CsvCell addCell(int targetColIndex, CsvCell cell) {

			this.cellMap.put(targetColIndex, cell);

			return cell;
		}

		public SortedMap<Integer, CsvCell> getCellMap() {

			return cellMap;
		}
	}

	public static class CsvCell {

		private final TableExportTypedNodeValue content;

		public CsvCell(TableExportTypedNodeValue content) {

			this.content = content;
		}

		public TableExportTypedNodeValue getContent() {

			return content;
		}
	}
}
