package com.softicar.platform.dom.styles;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.dom.style.ICssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

/**
 * Defines CSS text-shadow.
 *
 * @author Oliver Richers
 */
public class CssTextShadow implements ICssStyleAttribute {

	private final ICssLength x;
	private final ICssLength y;
	private final ICssLength blur;
	private final IColor color;

	public CssTextShadow(ICssLength x, ICssLength y, IColor color) {

		this(x, y, ICssLength.ZERO, color);
	}

	public CssTextShadow(ICssLength x, ICssLength y, ICssLength blur, IColor color) {

		this.x = x;
		this.y = y;
		this.blur = blur;
		this.color = color;
	}

	@Override
	public ICssStyle getStyle() {

		return CssStyle.TEXT_SHADOW;
	}

	@Override
	public String getValue() {

		return String.format("%s %s %s %s", x, y, blur, color.toHtml());
	}

	@Override
	public String toString() {

		return getValue();
	}
}
