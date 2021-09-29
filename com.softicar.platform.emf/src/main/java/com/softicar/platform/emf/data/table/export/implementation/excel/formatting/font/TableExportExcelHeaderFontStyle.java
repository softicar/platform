package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.TableExportExcelEngine;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeFontWeight;

public class TableExportExcelHeaderFontStyle extends TableExportExcelFontStyle {

	public TableExportExcelHeaderFontStyle(IColor fontColor) {

		super(TableExportNodeFontWeight.BOLD, TableExportExcelEngine.EXPORT_FONT_SIZE_PT, TableExportExcelEngine.FONT_NAME, fontColor);
	}
}
