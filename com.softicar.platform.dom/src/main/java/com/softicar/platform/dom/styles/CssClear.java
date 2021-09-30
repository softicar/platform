package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssClear implements ICssStyleAttribute {
	NONE,
	LEFT,
	RIGHT,
	BOTH;

	@Override
	public CssStyle getStyle() {

		return CssStyle.CLEAR;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
