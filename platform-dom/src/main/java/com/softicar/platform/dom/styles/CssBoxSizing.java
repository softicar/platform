package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssBoxSizing implements ICssStyleAttribute {

	BORDER_BOX("border-box"),
	CONTENT_BOX("content-box");

	private String name;

	private CssBoxSizing(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.BOX_SIZING;
	}

	@Override
	public String getValue() {

		return name;
	}
}
