package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssDisplay implements ICssStyleAttribute {

	BLOCK("block"),
	COMPACT("compact"),
	FLEX("flex"),
	GRID("grid"),
	INLINE("inline"),
	INLINE_BLOCK("inline-block"),
	INLINE_FLEX("inline-flex"),
	INLINE_GRID("inline-grid"),
	INLINE_TABLE("inline-table"),
	LIST_ITEM("list-item"),
	NONE("none"),
	RUN_IN("run-in"),
	TABLE("table"),
	TABLE_ROW_GROUP("table-row-group"),
	TABLE_HEADER_GROUP("table-header-group"),
	TABLE_FOOTER_GROUP("table-footer-group"),
	TABLE_ROW("table-row"),
	TABLE_COLUMN_GROUP("table-column-group"),
	TABLE_COLUMN("table-column"),
	TABLE_CELL("table-cell"),
	TABLE_CAPTION("table-caption");

	@Override
	public CssStyle getStyle() {

		return CssStyle.DISPLAY;
	}

	@Override
	public String getValue() {

		return name;
	}

	private CssDisplay(String name) {

		this.name = name;
	}

	private String name;
}
