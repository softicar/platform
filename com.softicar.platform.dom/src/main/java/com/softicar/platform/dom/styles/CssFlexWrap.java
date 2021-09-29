package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFlexWrap implements ICssStyleAttribute {

	NOWRAP("nowrap"),
	WRAP("wrap"),
	WRAP_REVERSE("wrap-reverse");

	private String name;

	private CssFlexWrap(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.FLEX_WRAP;
	}

	@Override
	public String getValue() {

		return name;
	}
}
