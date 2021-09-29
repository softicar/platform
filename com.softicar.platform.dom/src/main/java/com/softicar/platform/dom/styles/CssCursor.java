package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssCursor implements ICssStyleAttribute {

	AUTO("auto"),
	CROSSHAIR("crosshair"),
	DEFAULT("default"),
	HELP("help"),
	MOVE("move"),
	POINTER("pointer"),
	TEXT("text"),
	WAIT("wait"),
	ZOOM_IN("zoom-in"),
	ZOOM_OUT("zoom-out");

	private final String value;

	private CssCursor(String value) {

		this.value = value;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.CURSOR;
	}

	@Override
	public String getValue() {

		return value;
	}
}
