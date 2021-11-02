package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFontVariant implements ICssStyleAttribute {
	NORMAL("normal"),
	SMALL_CAPS("small-caps");

	@Override
	public CssStyle getStyle() {

		return CssStyle.FONT_VARIANT;
	}

	@Override
	public String getValue() {

		return name;
	}

	private CssFontVariant(String name) {

		this.name = name;
	}

	private String name;
}
