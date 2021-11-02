package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssOverflowY implements ICssStyleAttribute {
	AUTO,
	HIDDEN,
	INHERIT,
	INITIAL,
	SCROLL,
	VISIBLE;

	@Override
	public ICssStyle getStyle() {

		return CssStyle.OVERFLOW_Y;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
