package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssPosition implements ICssStyleAttribute {
	ABSOLUTE,
	FIXED,
	INHERIT,
	RELATIVE,
	STATIC;

	@Override
	public CssStyle getStyle() {

		return CssStyle.POSITION;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
