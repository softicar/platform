package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font.TableExportExcelHeaderFontStyle;

public class TableExportExcelHeaderCellStyle extends TableExportExcelCellStyle {

	public TableExportExcelHeaderCellStyle(IColor fontColor) {

		super(new TableExportExcelHeaderFontStyle(fontColor), null, getPoiAlignment(TableExportCellAlignment.CENTER), false, null);
	}
}
