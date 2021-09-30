package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssVisibility implements ICssStyleAttribute {
	VISIBLE,
	HIDDEN,
	COLLAPSE;

	@Override
	public CssStyle getStyle() {

		return CssStyle.VISIBILITY;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
