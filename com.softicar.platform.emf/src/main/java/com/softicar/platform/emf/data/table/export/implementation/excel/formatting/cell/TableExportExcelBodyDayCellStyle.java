package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data.TableExportExcelDayDataFormat;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font.TableExportExcelBodyFontStyle;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeFontWeight;

public class TableExportExcelBodyDayCellStyle extends TableExportExcelCellStyle {

	public TableExportExcelBodyDayCellStyle(TableExportCellAlignment alignment, IColor backgroundColor, TableExportNodeFontWeight fontWeight, IColor fontColor) {

		super(new TableExportExcelBodyFontStyle(fontWeight, fontColor), new TableExportExcelDayDataFormat(), getPoiAlignment(alignment), true, backgroundColor);
	}
}
