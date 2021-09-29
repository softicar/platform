package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell;

import com.softicar.platform.common.core.comparator.Comparators;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.data.TableExportExcelDataFormat;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font.TableExportExcelFontStyle;
import java.util.Comparator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class TableExportExcelCellStyle implements Comparable<TableExportExcelCellStyle> {

	private final TableExportExcelFontStyle fontStyleKey;
	private final TableExportExcelDataFormat dataFormatKey;
	private final HorizontalAlignment horizontalAlignment;
	private final Boolean wrapText;
	private final IColor backgroundColor;

	@Override
	public int compareTo(TableExportExcelCellStyle o) {

		return Comparator//
			.comparing(TableExportExcelCellStyle::getFontStyleKey, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelCellStyle::getDataFormatKey, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelCellStyle::getHorizontalAlignment, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelCellStyle::getWrapText, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelCellStyle::getBackgroundColorForComparison)
			.compare(this, o);
	}

	public TableExportExcelCellStyle(TableExportExcelFontStyle fontStyleKey, TableExportExcelDataFormat dataFormatKey, HorizontalAlignment horizontalAlignment,
			Boolean wrapText, IColor backgroundColor) {

		this.fontStyleKey = fontStyleKey;
		this.dataFormatKey = dataFormatKey;
		this.horizontalAlignment = horizontalAlignment;
		this.wrapText = wrapText;
		this.backgroundColor = backgroundColor;
	}

	public TableExportExcelFontStyle getFontStyleKey() {

		return fontStyleKey;
	}

	public TableExportExcelDataFormat getDataFormatKey() {

		return dataFormatKey;
	}

	public HorizontalAlignment getHorizontalAlignment() {

		return horizontalAlignment;
	}

	public Boolean getWrapText() {

		return wrapText;
	}

	public IColor getBackgroundColor() {

		return backgroundColor;
	}

	protected static HorizontalAlignment getPoiAlignment(TableExportCellAlignment alignment) {

		if (alignment == TableExportCellAlignment.LEFT) {
			return HorizontalAlignment.LEFT;
		} else if (alignment == TableExportCellAlignment.CENTER) {
			return HorizontalAlignment.CENTER;
		} else if (alignment == TableExportCellAlignment.RIGHT) {
			return HorizontalAlignment.RIGHT;
		} else if (alignment == null) {
			return null;
		} else {
			throw new SofticarUnknownEnumConstantException(alignment);
		}
	}

	private String getBackgroundColorForComparison() {

		return backgroundColor != null? backgroundColor.toHtml() : "";
	}
}
