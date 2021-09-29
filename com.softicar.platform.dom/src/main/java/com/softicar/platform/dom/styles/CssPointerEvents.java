package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssPointerEvents implements ICssStyleAttribute {

	ALL("all"),
	AUTO("auto"),
	FILL("fill"),
	NONE("none"),
	PAINTED("painted"),
	STROKE("stroke"),
	VISIBLE("visible"),
	VISIBLE_FILL("visibleFill"),
	VISIBLE_PAINTED("visiblePainted"),
	VISIBLE_STROKE("visibleStroke"),;

	private final String value;

	private CssPointerEvents(String value) {

		this.value = value;
	}

	@Override
	public CssStyle getStyle() {

		return CssStyle.POINTER_EVENTS;
	}

	@Override
	public String getValue() {

		return value;
	}
}
