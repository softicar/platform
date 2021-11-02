package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssAlignSelf implements ICssStyleAttribute {

	AUTO("auto"),
	FLEX_START("flex-start"),
	FLEX_END("flex-end"),
	CENTER("center"),
	BASELINE("baseline"),
	STRETCH("stretch");

	private String name;

	private CssAlignSelf(String name) {

		this.name = name;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.ALIGN_SELF;
	}

	@Override
	public String getValue() {

		return name;
	}
}
