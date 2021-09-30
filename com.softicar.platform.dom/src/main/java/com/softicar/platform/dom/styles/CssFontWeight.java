package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFontWeight implements ICssStyleAttribute {
	NORMAL,
	LIGHTER,
	BOLD,
	BOLDER;

	@Override
	public CssStyle getStyle() {

		return CssStyle.FONT_WEIGHT;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
