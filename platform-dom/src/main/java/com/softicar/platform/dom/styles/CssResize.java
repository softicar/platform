package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssResize implements ICssStyleAttribute {

	NONE,
	BOTH,
	HORIZONTAL,
	VERTICAL;

	@Override
	public CssStyle getStyle() {

		return CssStyle.RESIZE;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
