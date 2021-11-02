package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.TableExportExcelEngine;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeFontWeight;

public class TableExportExcelBodyFontStyle extends TableExportExcelFontStyle {

	public TableExportExcelBodyFontStyle(TableExportNodeFontWeight fontWeight, IColor fontColor) {

		super(fontWeight, TableExportExcelEngine.EXPORT_FONT_SIZE_PT, TableExportExcelEngine.FONT_NAME, fontColor);
	}
}
