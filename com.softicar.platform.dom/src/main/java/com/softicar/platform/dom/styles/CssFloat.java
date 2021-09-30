package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFloat implements ICssStyleAttribute {
	NONE,
	LEFT,
	RIGHT;

	@Override
	public CssStyle getStyle() {

		return CssStyle.FLOAT;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
