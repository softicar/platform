package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssWhiteSpace implements ICssStyleAttribute {
	NORMAL("normal"),
	NOWRAP("nowrap"),
	PRE("pre"),
	PRE_LINE("pre-line"),
	PRE_WRAP("pre-wrap"),
	INITIAL("initial"),
	INHERIT("inherit");

	private final String value;

	private CssWhiteSpace(String value) {

		this.value = value;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.WHITE_SPACE;
	}

	@Override
	public String getValue() {

		return value;
	}
}
