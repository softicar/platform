package com.softicar.platform.emf.data.table.export.implementation.html;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.dom.DomCssFiles;
import com.softicar.platform.dom.element.DomElementTag;
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
import com.softicar.platform.emf.data.table.export.implementation.html.TableExportHtmlEngine.HtmlCell;
import com.softicar.platform.emf.data.table.export.implementation.html.TableExportHtmlEngine.HtmlRow;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeStyle;
import com.softicar.platform.emf.data.table.export.node.style.TableExportTableCssClass;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.text.StringEscapeUtils;

public class TableExportHtmlEngine extends AbstractTableExportEngine<HtmlRow, HtmlCell> {

	private static final Charset OUTPUT_FILE_CHARSET = StandardCharsets.UTF_8;
	private static final Charset CSS_FILE_CHARSET = StandardCharsets.UTF_8;
	private static final int NUM_TABLE_SPACER_ROWS = 2;

	private PrintWriter printWriter = null;

	private StringBuilder currentTableHeadBuilder = null;
	private StringBuilder currentTableBodyBuilder = null;
	private TableExportTableCssClass currentTableCssClass = null;

	public TableExportHtmlEngine(TableExportEngineConfiguration configuration, ITableExportEngineFactory<? extends ITableExportEngine> creatingFactory) {

		super(
			configuration, //
			creatingFactory,
			new TableExportNodeConverterFactoryConfiguration(//
				new TableExportTextOnlyNodeConverterFactory(),
				new TableExportDefaultNodeConverterFactory()//
			).addAvailableFactories(new TableExportStrictNodeConverterFactory(), new TableExportTextOnlyNodeConverterFactory())//
		);
	}

	@Override
	protected void prepareExport(OutputStream targetOutputStream, Collection<TableExportTableConfiguration> tableConfigurations) {

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(targetOutputStream, OUTPUT_FILE_CHARSET);

		this.printWriter = new PrintWriter(outputStreamWriter);
		this.printWriter.println("<html>");
		this.printWriter.println("<head>");
		this.printWriter.println("<style>");
		this.printWriter.println(getCoreCss());
		this.printWriter.println("</style>");
		this.printWriter.println("</head>");
		this.printWriter.println("<body>");
	}

	private String getCoreCss() {

		try (InputStream inputStream = DomCssFiles.DOM_STYLE.getResource().getResourceAsStream()) {
			return StreamUtils.toString(inputStream, CSS_FILE_CHARSET);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	protected void finishExport(OutputStream targetOutputStream) {

		this.printWriter.println("</body>");
		this.printWriter.println("</html>");

		this.printWriter.close();
	}

	@Override
	protected void prepareTable(TableExportTableConfiguration tableConfiguration) {

		this.currentTableCssClass = tableConfiguration.getTableCssClass();

		resetHeadAndBodyBuilders();
	}

	@Override
	protected void finishTable() {

		this.printWriter.println(String.format("<table class=\"%s\">", this.currentTableCssClass.getCssClassName()));
		this.printWriter.println("<thead>");
		this.printWriter.print(this.currentTableHeadBuilder);
		this.printWriter.println("</thead>");
		this.printWriter.println("<tbody>");
		this.printWriter.print(this.currentTableBodyBuilder);
		this.printWriter.println("</tbody>");
		this.printWriter.println("</table>");
	}

	@Override
	protected HtmlRow createAndAppendRow(int targetRowIndex, boolean isHeader) {

		return new HtmlRow(isHeader);
	}

	/**
	 * Note that {@link TableExportNodeStyle} is not used at all in this HTML
	 * export engine. Instead it uses {@link DomCssFiles#DOM_STYLE}.
	 */
	@Override
	protected HtmlCell createAndAppendCell(HtmlRow documentRow, int targetColIndex, boolean isHeader, NodeConverterResult convertedCellContent,
			TableExportNodeStyle exportNodeStyle) {

		return documentRow.addCell(targetColIndex, new HtmlCell(convertedCellContent.getContent(), isHeader));
	}

	@Override
	protected int appendTableSpacerRows(int targetRowIndex) {

		for (int i = 0; i < NUM_TABLE_SPACER_ROWS; i++) {
			finishRow(new HtmlRow(false));
		}

		return NUM_TABLE_SPACER_ROWS;
	}

	@Override
	protected void finishRow(HtmlRow documentRow) {

		StringBuilder targetBuilder = documentRow.isHeader()? this.currentTableHeadBuilder : this.currentTableBodyBuilder;
		targetBuilder.append(getHtmlFromRow(documentRow));
	}

	@Override
	protected void finishCell(HtmlCell documentCell) {

		// nothing to do
	}

	@Override
	protected void mergeRectangularRegion(HtmlRow documentRow, HtmlCell documentCell, int firstRow, int lastRow, int firstCol, int lastCol) {

		documentCell.setColSpan(lastCol - firstCol + 1);
		documentCell.setRowSpan(lastRow - firstRow + 1);
	}

	private String getHtmlFromRow(HtmlRow row) {

		StringBuilder output = new StringBuilder();
		output.append(row.getTagOpen());

		SortedMap<Integer, HtmlCell> cellMap = row.getCellMap();

		if (!cellMap.isEmpty()) {
			Integer lastKey = cellMap.lastKey();

			for (int i = 0; i <= lastKey; i++) {
				HtmlCell cell = cellMap.get(i);

				if (cell != null) {
					TableExportTypedNodeValue content = cell.getContent();
					Object contentValue = content.getValue();

					String contentString = "";
					if (contentValue != null) {
						contentString += contentValue;
					}

					output.append(cell.getTagOpen());
					output.append(StringEscapeUtils.escapeHtml4(contentString));
					output.append(cell.getTagClose());
				}

//				if (i < lastKey) {
//					output.append(OUTPUT_SEPARATOR);
//				}
			}
		}

		output.append(row.getTagClose());

		return output.toString() + "\n";
	}

	private void resetHeadAndBodyBuilders() {

		this.currentTableHeadBuilder = new StringBuilder();
		this.currentTableBodyBuilder = new StringBuilder();
	}

	public static class HtmlRow {

		private final SortedMap<Integer, HtmlCell> cellMap = new TreeMap<>();
		private final boolean isHeader;

		public HtmlRow(boolean isHeader) {

			this.isHeader = isHeader;
		}

		public HtmlCell addCell(int targetColIndex, HtmlCell cell) {

			this.cellMap.put(targetColIndex, cell);

			return cell;
		}

		public SortedMap<Integer, HtmlCell> getCellMap() {

			return cellMap;
		}

		protected boolean isHeader() {

			return isHeader;
		}

		public String getTagOpen() {

			return new StringBuilder()//
				.append("<")
				.append(DomElementTag.TR.getName())
				.append(">")
				.toString();
		}

		public String getTagClose() {

			return new StringBuilder()//
				.append("</")
				.append(DomElementTag.TR.getName())
				.append(">")
				.toString();
		}
	}

	public static class HtmlCell {

		private final TableExportTypedNodeValue content;
		private final boolean isHeader;

		private int colSpan = 1;
		private int rowSpan = 1;

		public HtmlCell(TableExportTypedNodeValue content, boolean isHeader) {

			this.content = content;
			this.isHeader = isHeader;
		}

		public void setColSpan(int colSpan) {

			this.colSpan = colSpan;
		}

		public void setRowSpan(int rowSpan) {

			this.rowSpan = rowSpan;
		}

		public TableExportTypedNodeValue getContent() {

			return content;
		}

		public boolean isHeader() {

			return isHeader;
		}

		public int getColSpan() {

			return colSpan;
		}

		public int getRowSpan() {

			return rowSpan;
		}

		public String getTagOpen() {

			return new StringBuilder()//
				.append("<")
				.append((this.isHeader? DomElementTag.TH : DomElementTag.TD).getName())
				.append(">")
				.toString();
		}

		public String getTagClose() {

			return new StringBuilder()//
				.append("</")
				.append((this.isHeader? DomElementTag.TH : DomElementTag.TD).getName())
				.append(colSpan > 1? " colspan=\"" + colSpan + "\"" : "")
				.append(rowSpan > 1? " rowspan=\"" + rowSpan + "\"" : "")
				.append(">")
				.toString();
		}
	}
}
