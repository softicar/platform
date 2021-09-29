package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter.NodeConverterResult;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportDefaultNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportStrictNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportTextOnlyNodeConverterFactory;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.engine.AbstractTableExportColumnFilteringEngine;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportTableConfiguration;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportCellAlignment;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportExcelBodyCellStyle;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportExcelBodyDayCellStyle;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportExcelBodyDayTimeCellStyle;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportExcelCellStyleManager;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportExcelHeaderCellStyle;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.color.TableExportExcelColorManager;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data.TableExportExcelDataFormatManager;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font.TableExportExcelFontStyleManager;
import com.softicar.platform.emf.data.table.export.node.TableExportNodeValueType;
import com.softicar.platform.emf.data.table.export.node.TableExportNodeValueTypeHandlingException;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeFontWeight;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeStyle;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Exports {@link DomTable}s to XLS or XLSX format using Apache POI.
 *
 * @author Alexander Schmidt
 */
public class TableExportExcelEngine extends AbstractTableExportColumnFilteringEngine<TableExportTypedNodeValue, Row, Cell> {

	public static final int NUM_TABLE_SPACER_ROWS = 2;

	public static final short EXPORT_FONT_SIZE_PT = (short) 10;
	public static final String FONT_NAME = "Arial";

	public static final String DATA_FORMAT_DAY = "yyyy-MM-dd";
	public static final String DATA_FORMAT_DAYTIME = "yyyy-MM-dd HH:mm:ss";

	// note: units are not in pixels but in some kind of esoteric Microsoft measure
	private static final short LINE_BREAK_SPACING = 24;
	private static final short VERTIAL_PADDING_COMPENSATION = 64;

	// >>>> these are general constants in both HSSF and XSSF formats - DO NOT CHANGE! >>>>
	private static final int WIDTH_OF_ONE_CHARACTER = 256;
	private static final int MAX_COLUMN_WIDTH = 255 * WIDTH_OF_ONE_CHARACTER;
	// <<<< these are general constants in both HSSF and XSSF formats - DO NOT CHANGE! <<<<

	private static final int ADDITIONAL_COLUMN_WIDTH = 1 * WIDTH_OF_ONE_CHARACTER;

	// ----

	private final TableExportExcelExportConfiguration poiExportConfiguration;
	private Workbook workbook;
	private Sheet sheet;
	private TableExportExcelCellStyleManager cellStyleManager;
	private int maxColumnIndex;

	public TableExportExcelEngine(TableExportEngineConfiguration configuration, TableExportExcelExportConfiguration poiExportConfiguration,
			ITableExportEngineFactory<? extends ITableExportEngine<TableExportTypedNodeValue>> creatingFactory) {

		super(
			configuration,
			creatingFactory,
			new TableExportNodeConverterFactoryConfiguration<>(new TableExportTextOnlyNodeConverterFactory(), new TableExportDefaultNodeConverterFactory())
				.addAvailableFactories(new TableExportStrictNodeConverterFactory(), new TableExportTextOnlyNodeConverterFactory()));

		this.poiExportConfiguration = poiExportConfiguration;
		this.workbook = null;
		this.sheet = null;
		this.cellStyleManager = null;
		this.maxColumnIndex = 0;
	}

	@Override
	protected void prepareExport(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<TableExportTypedNodeValue>> tableConfigurations) {

		this.workbook = TableExportExcelWorkbookCreator.createWorkbook(poiExportConfiguration.getFormat());
		assertStreamingWorkbook(this.workbook);

		TableExportExcelColorManager colorManager = new TableExportExcelColorManager(this.workbook);

		this.cellStyleManager = new TableExportExcelCellStyleManager(//
			this.workbook,
			new TableExportExcelFontStyleManager(this.workbook, colorManager),
			new TableExportExcelDataFormatManager(this.workbook),
			colorManager);
	}

	@Override
	protected void finishExport(OutputStream targetOutputStream) throws IOException {

		if (!poiExportConfiguration.isSheetPerTable()) {
			autosizeSheetColumns();
		}

		this.workbook.write(targetOutputStream);
	}

	@Override
	protected void prepareTable(TableExportTableConfiguration<TableExportTypedNodeValue> tableConfiguration) {

		if (sheet == null || poiExportConfiguration.isSheetPerTable()) {
			this.sheet = this.workbook.createSheet(getNextSheetTitle(tableConfiguration));
			this.maxColumnIndex = 0;
			resetRowsOnCurrentSheet();
		}
	}

	@Override
	protected Row createAndAppendRow(int targetRowIndex, boolean isHeader) {

		return this.sheet.createRow(targetRowIndex);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected Cell createAndAppendCell(Row documentRow, int targetColIndex, boolean isHeader,
			NodeConverterResult<TableExportTypedNodeValue> convertedCellContent, TableExportNodeStyle exportNodeStyle) {

		TableExportCellAlignment cellAlignment = exportNodeStyle.getAlignment();
		IColor backgroundColor = exportNodeStyle.getBackgroundColor();
		IColor fontColor = exportNodeStyle.getFontColor();
		TableExportNodeFontWeight fontWeight = Optional.ofNullable(exportNodeStyle.getFontWeight()).orElse(TableExportNodeFontWeight.NORMAL);

		Cell cell = documentRow.createCell(targetColIndex);
		CellStyle cellStyle = this.cellStyleManager.getCellStyle(new TableExportExcelBodyCellStyle(cellAlignment, backgroundColor, fontWeight, fontColor));
		TableExportTypedNodeValue cellContent = convertedCellContent.getContent();
		Integer contentLineCount = convertedCellContent.getContentLineCount();

		//
		// >>>> handle distinct cell ValueTypes >>>>
		//

		if (cellContent.getValueType().isA(TableExportNodeValueType.NUMBER)) {
			Number numberValue = cellContent.getNumber();
			if (numberValue != null) {
				cell.setCellValue(numberValue.doubleValue());
			}
		}

		else if (cellContent.getValueType().isA(TableExportNodeValueType.STRING)) {
			String stringValue = cellContent.getString();
			if (stringValue != null) {
				cell.setCellValue(stringValue);
			}
		}

		else if (cellContent.getValueType().isA(TableExportNodeValueType.DAY)) {
			Day day = cellContent.getDay();
			if (day != null) {
				cell.setCellValue(day.toDate());
				cellStyle = this.cellStyleManager.getCellStyle(new TableExportExcelBodyDayCellStyle(cellAlignment, backgroundColor, fontWeight, fontColor));
			}
		}

		else if (cellContent.getValueType().isA(TableExportNodeValueType.DAYTIME)) {
			DayTime dayTime = cellContent.getDayTime();
			if (dayTime != null) {
				cell.setCellValue(dayTime.toDate());
				cellStyle = this.cellStyleManager.getCellStyle(new TableExportExcelBodyDayTimeCellStyle(cellAlignment, backgroundColor, fontWeight, fontColor));
			}
		}

		else {
			throw new TableExportNodeValueTypeHandlingException(cellContent.getValueType());
		}

		//
		// <<<< handle distinct cell ValueTypes <<<<
		//

		if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
			// circumvent NullPointerException when horizontally auto-sizing the column
			cell.setCellValue("");
		}

		if (isHeader) {
			cell.setCellStyle(this.cellStyleManager.getCellStyle(new TableExportExcelHeaderCellStyle(fontColor)));
		} else {
			cell.setCellStyle(cellStyle);
		}

		if (targetColIndex > this.maxColumnIndex) {
			this.maxColumnIndex = targetColIndex;
		}

		//
		// increase row height if necessary
		//

		if (contentLineCount > 1) {
			short currentRowHeight = documentRow.getHeight();
			short newRowHeight = calculateOutputRowHeight(cellStyle, contentLineCount);

			if (currentRowHeight == this.sheet.getDefaultRowHeight() || newRowHeight > currentRowHeight) {
				documentRow.setHeight(newRowHeight);
			}
		}

		return cell;
	}

	@Override
	protected void mergeRectangularRegion(Row documentRow, Cell documentCell, int firstRow, int lastRow, int firstCol, int lastCol) {

		this.sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	@Override
	protected int appendTableSpacerRows(int targetRowIndex) {

		if (poiExportConfiguration.isSheetPerTable()) {
			return 0;
		} else {
			for (int i = 0; i < NUM_TABLE_SPACER_ROWS; i++) {
				createAndAppendRow(targetRowIndex, false);
			}
			return NUM_TABLE_SPACER_ROWS;
		}
	}

	@Override
	protected void finishTable() {

		if (poiExportConfiguration.isSheetPerTable()) {
			autosizeSheetColumns();
		}
	}

	@Override
	protected void finishRow(Row documentRow) {

		// nothing to do
	}

	@Override
	protected void finishCell(Cell documentCell) {

		// nothing to do
	}

	private short calculateOutputRowHeight(CellStyle cellStyle, int numLines) {

		short fontHeight = (short) (numLines * this.workbook.getFontAt(cellStyle.getFontIndexAsInt()).getFontHeight());
		short spacing = (short) ((numLines - 1) * LINE_BREAK_SPACING);

		return (short) (fontHeight + spacing + VERTIAL_PADDING_COMPENSATION);
	}

	private String getNextSheetTitle(TableExportTableConfiguration<TableExportTypedNodeValue> tableConfiguration) {

		if (poiExportConfiguration.isSheetPerTable()) {
			return tableConfiguration//
				.getTableName()
				.orElse(getNextDefaultSheetTitle());
		} else {
			return getNextDefaultSheetTitle();
		}
	}

	private String getNextDefaultSheetTitle() {

		return poiExportConfiguration.getDefaultSheetTitle().toString() + getNextSheetNumber();
	}

	private int getNextSheetNumber() {

		return workbook.getNumberOfSheets() + 1;
	}

	private void autosizeSheetColumns() {

		if (sheet instanceof SXSSFSheet) {
			((SXSSFSheet) this.sheet).trackAllColumnsForAutoSizing();
		}

		for (int i = 0; i <= this.maxColumnIndex; ++i) {
			// warning: that will throw a NullPointerException if at least one cell in that column contains null
			this.sheet.autoSizeColumn(i, true);

			int newColumnWidth = Math.min(this.sheet.getColumnWidth(i) + ADDITIONAL_COLUMN_WIDTH, MAX_COLUMN_WIDTH);
			this.sheet.setColumnWidth(i, newColumnWidth);
		}
	}

	private static void assertStreamingWorkbook(Workbook workbook) {

		if (workbook instanceof XSSFWorkbook) {
			throw new SofticarDeveloperException(
				"You just tried to create a non-streaming %s (\"xlsx format\"). Since that won't scale well, you should use %s instead.",
				XSSFWorkbook.class.getSimpleName(),
				SXSSFWorkbook.class.getSimpleName());
		}
	}
}
