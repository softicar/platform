package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.color.TableExportExcelColorManager;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeFontWeight;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class TableExportExcelFontStyleManager {

	private final Workbook workbook;
	private final TableExportExcelColorManager colorManager;
	private final Map<TableExportExcelFontStyle, Font> map = new TreeMap<>();

	public TableExportExcelFontStyleManager(Workbook workbook, TableExportExcelColorManager colorManager) {

		this.workbook = workbook;
		this.colorManager = colorManager;
	}

	public Font getFont(TableExportExcelFontStyle fontStyleKey) {

		Font font = this.map.get(fontStyleKey);
		if (font == null) {
			font = generateFont(fontStyleKey);
			this.map.put(fontStyleKey, font);
		}

		return font;
	}

	private Font generateFont(TableExportExcelFontStyle fontStyleKey) {

		Font font = this.workbook.createFont();

		boolean bold = getFontWeightAsShort(fontStyleKey.getFontWeight());
		font.setBold(bold);

		Short fontHeightInPoints = fontStyleKey.getFontHeightInPoints();
		if (fontHeightInPoints != null) {
			font.setFontHeightInPoints(fontHeightInPoints);
		}

		String fontName = fontStyleKey.getFontName();
		if (fontName != null) {
			font.setFontName(fontName);
		}

		IColor fontColor = fontStyleKey.getFontColor();
		if (fontColor != null) {
			this.colorManager.applyFontColorToFont(font, fontColor);
		}

		return font;
	}

	private boolean getFontWeightAsShort(TableExportNodeFontWeight fontWeight) {

		if (fontWeight == TableExportNodeFontWeight.NORMAL) {
			return false;
		} else {
			return true;
		}
	}
}
