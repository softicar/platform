package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssTextAlign implements ICssStyleAttribute {
	LEFT,
	RIGHT,
	CENTER,
	JUSTIFY;

	@Override
	public CssStyle getStyle() {

		return CssStyle.TEXT_ALIGN;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
