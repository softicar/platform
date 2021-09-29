package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssTextDecoration implements ICssStyleAttribute {
	NONE("none"),
	UNDERLINE("underline"),
	OVERLINE("overline"),
	LINE_THROUGH("line-through"),
	BLINK("blink");

	private String name;

	private CssTextDecoration(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.TEXT_DECORATION;
	}

	@Override
	public String getValue() {

		return name;
	}
}
