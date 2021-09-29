package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssVerticalAlign implements ICssStyleAttribute {
	BASELINE,
	INHERIT,
	INITIAL,
	SUB,
	SUPER,
	TOP,
	TEXT_TOP,
	MIDDLE,
	BOTTOM,
	TEXT_BOTTOM;

	@Override
	public CssStyle getStyle() {

		return CssStyle.VERTICAL_ALIGN;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
