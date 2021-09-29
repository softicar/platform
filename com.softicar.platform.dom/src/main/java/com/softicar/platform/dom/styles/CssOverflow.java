package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssOverflow implements ICssStyleAttribute {
	AUTO,
	HIDDEN,
	INHERIT,
	OVERLAY,
	SCROLL,
	VISIBLE;

	@Override
	public CssStyle getStyle() {

		return CssStyle.OVERFLOW;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
