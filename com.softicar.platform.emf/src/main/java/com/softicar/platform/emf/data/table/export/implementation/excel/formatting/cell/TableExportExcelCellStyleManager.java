package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.color.TableExportExcelColorManager;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data.TableExportExcelDataFormat;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data.TableExportExcelDataFormatManager;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font.TableExportExcelFontStyleManager;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class TableExportExcelCellStyleManager {

	private final Workbook workbook;
	private final TableExportExcelFontStyleManager fontManager;
	private final TableExportExcelDataFormatManager dataFormatManager;
	private final TableExportExcelColorManager colorManager;
	private final Map<TableExportExcelCellStyle, CellStyle> map = new TreeMap<>();

	public TableExportExcelCellStyleManager(Workbook workbook, TableExportExcelFontStyleManager fontManager, TableExportExcelDataFormatManager dataFormatManager, TableExportExcelColorManager colorManager) {

		this.workbook = workbook;
		this.fontManager = fontManager;
		this.dataFormatManager = dataFormatManager;
		this.colorManager = colorManager;
	}

	public CellStyle getCellStyle(TableExportExcelCellStyle cellStyleKey) {

		CellStyle cellStyle = this.map.get(cellStyleKey);
		if (cellStyle == null) {
			cellStyle = generateCellStyle(cellStyleKey);
			this.map.put(cellStyleKey, cellStyle);
		}

		return cellStyle;
	}

	private CellStyle generateCellStyle(TableExportExcelCellStyle cellStyleKey) {

		CellStyle cellStyle = this.workbook.createCellStyle();

		Font font = this.fontManager.getFont(cellStyleKey.getFontStyleKey());
		cellStyle.setFont(font);

		TableExportExcelDataFormat dataFormatKey = cellStyleKey.getDataFormatKey();
		if (dataFormatKey != null) {
			Short dataFormat = this.dataFormatManager.getDataFormat(dataFormatKey.getFormatString());
			cellStyle.setDataFormat(dataFormat);
		}

		HorizontalAlignment alignment = cellStyleKey.getHorizontalAlignment();
		if (alignment != null) {
			cellStyle.setAlignment(alignment);
		}

		Boolean wrapText = cellStyleKey.getWrapText();
		if (wrapText != null) {
			cellStyle.setWrapText(wrapText);
		}

		IColor backgroundColor = cellStyleKey.getBackgroundColor();
		if (backgroundColor != null) {
			this.colorManager.applyBackgroundColorToCellStyle(cellStyle, backgroundColor);
		}

		return cellStyle;
	}
}
