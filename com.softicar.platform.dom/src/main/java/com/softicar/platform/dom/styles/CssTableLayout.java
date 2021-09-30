package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssTableLayout implements ICssStyleAttribute {

	AUTO,
	FIXED;

	@Override
	public CssStyle getStyle() {

		return CssStyle.TABLE_LAYOUT;
	}

	@Override
	public String getValue() {

		return toString();
	}
}
