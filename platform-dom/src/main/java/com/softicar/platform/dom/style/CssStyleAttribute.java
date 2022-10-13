package com.softicar.platform.dom.style;

import java.util.Objects;

public class CssStyleAttribute implements ICssStyleAttribute {

	private final ICssStyle style;
	private final String value;

	public CssStyleAttribute(ICssStyle style, String value) {

		this.style = Objects.requireNonNull(style);
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public ICssStyle getStyle() {

		return style;
	}

	@Override
	public String getValue() {

		return value;
	}
}
