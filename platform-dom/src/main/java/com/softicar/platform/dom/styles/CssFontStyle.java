package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFontStyle implements ICssStyleAttribute {
	NORMAL,
	ITALIC,
	OBLIQUE;

	@Override
	public CssStyle getStyle() {

		return CssStyle.FONT_STYLE;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
