package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFlexDirection implements ICssStyleAttribute {

	ROW("row"),
	ROW_REVERSE("row-reverse"),
	COLUMN("column"),
	COLUMN_REVERSE("column-reverse");

	private String name;

	private CssFlexDirection(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.FLEX_DIRECTION;
	}

	@Override
	public String getValue() {

		return name;
	}
}
