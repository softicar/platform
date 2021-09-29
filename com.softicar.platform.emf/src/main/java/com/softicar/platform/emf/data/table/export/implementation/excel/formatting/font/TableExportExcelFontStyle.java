package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.font;

import com.softicar.platform.common.core.comparator.Comparators;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeFontWeight;
import java.util.Comparator;

public class TableExportExcelFontStyle implements Comparable<TableExportExcelFontStyle> {

	private final TableExportNodeFontWeight fontWeight;
	private final Short fontHeightInPoints;
	private final String fontName;
	private final IColor fontColor;

	@Override
	public int compareTo(TableExportExcelFontStyle o) {

		return Comparator//
			.comparing(TableExportExcelFontStyle::getFontWeight, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelFontStyle::getFontHeightInPoints, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelFontStyle::getFontName, Comparators.naturalOrderNullsFirst())
			.thenComparing(TableExportExcelFontStyle::getFontColorForComparison)
			.compare(this, o);
	}

	public TableExportExcelFontStyle(TableExportNodeFontWeight fontWeight, Short fontHeightInPoints, String fontName, IColor fontColor) {

		this.fontWeight = fontWeight;
		this.fontHeightInPoints = fontHeightInPoints;
		this.fontName = fontName;
		this.fontColor = fontColor;
	}

	public TableExportNodeFontWeight getFontWeight() {

		return fontWeight;
	}

	public Short getFontHeightInPoints() {

		return fontHeightInPoints;
	}

	public String getFontName() {

		return fontName;
	}

	public IColor getFontColor() {

		return fontColor;
	}

	private String getFontColorForComparison() {

		return fontColor != null? fontColor.toHtml() : "";
	}
}
