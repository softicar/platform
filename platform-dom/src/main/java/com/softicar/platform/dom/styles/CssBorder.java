package com.softicar.platform.dom.styles;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.dom.style.ICssStyleValue;

/**
 * Defines the CSS attributes of a border.
 *
 * @author Oliver Richers
 */
public class CssBorder implements ICssStyleValue {

	private final ICssLength width;
	private final CssBorderStyle style;
	private final IColor color;

	public CssBorder(ICssLength width, CssBorderStyle style, IColor color) {

		this.width = width;
		this.style = style;
		this.color = color;
	}

	@Override
	public String toString() {

		return String.format("%s %s %s", width.toString(), style.getValue(), color.toHtml());
	}
}
