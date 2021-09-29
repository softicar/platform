package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssBorderCollapse implements ICssStyleAttribute {
	SEPARATE("separate"),
	COLLAPSE("collapse");

	@Override
	public CssStyle getStyle() {

		return CssStyle.BORDER_COLLAPSE;
	}

	@Override
	public String getValue() {

		return name;
	}

	private CssBorderCollapse(String name) {

		this.name = name;
	}

	private String name;
}
