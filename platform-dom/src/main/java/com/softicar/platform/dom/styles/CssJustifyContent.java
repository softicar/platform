package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssJustifyContent implements ICssStyleAttribute {

	FLEX_START("flex-start"),
	FLEX_END("flex-end"),
	CENTER("center"),
	SPACE_BETWEEN("space-between"),
	SPACE_AROUND("space-around");

	private String name;

	private CssJustifyContent(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.JUSTIFY_CONTENT;
	}

	@Override
	public String getValue() {

		return name;
	}
}
