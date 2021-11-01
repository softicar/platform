package com.softicar.platform.dom.styles;

import com.softicar.platform.common.core.exceptions.SofticarNotImplementedYetException;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssFontFamily implements ICssStyleAttribute {

	SERIF,
	SANS_SERIF,
	CURSIVE,
	FANTASY,
	MONOSPACE;

	@Override
	public CssStyle getStyle() {

		return CssStyle.FONT_FAMILY;
	}

	@Override
	public String getValue() {

		switch (this) {
		case SERIF:
			return "serif";
		case SANS_SERIF:
			return "sans-serif";
		case CURSIVE:
			return "cursive";
		case FANTASY:
			return "fantasy";
		case MONOSPACE:
			return "monospace";
		default:
			throw new SofticarNotImplementedYetException();
		}
	}
}
