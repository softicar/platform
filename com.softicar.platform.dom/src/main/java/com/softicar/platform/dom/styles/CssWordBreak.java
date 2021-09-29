package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssWordBreak implements ICssStyleAttribute {

	NORMAL("normal"),
	BREAK_ALL("break-all"),
	KEEP_ALL("keep-all"),
	BREAK_WORD("break-word"),
	INITIAL("initial"),
	INHERIT("inherit");

	private final String value;

	private CssWordBreak(String value) {

		this.value = value;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.WORD_BREAK;
	}

	@Override
	public String getValue() {

		return value;
	}
}
