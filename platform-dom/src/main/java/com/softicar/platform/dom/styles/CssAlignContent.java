package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssAlignContent implements ICssStyleAttribute {

	FLEX_START("flex-start"),
	FLEX_END("flex-end"),
	CENTER("center"),
	SPACE_AROUND("space-around"),
	SPACE_BETWEEN("space-between"),
	STRETCH("stretch");

	private String name;

	private CssAlignContent(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.ALIGN_CONTENT;
	}

	@Override
	public String getValue() {

		return name;
	}
}
