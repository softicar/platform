package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssOverflowWrap implements ICssStyleAttribute {

	ANYWHERE("anywhere"),
	BREAK_WORD("break-word"),
	INHERIT("inherit"),
	INITIAL("initial"),
	NORMAL("normal");

	private final String value;

	private CssOverflowWrap(String value) {

		this.value = value;
	}

	@Override
	public ICssStyle getStyle() {

		return CssStyle.OVERFLOW_WRAP;
	}

	@Override
	public String getValue() {

		return value;
	}
}
