package com.softicar.platform.dom.style;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.common.ui.color.RgbColor;

/**
 * Enumerates common Css colors.
 *
 * @author Oliver Richers
 */
public enum CssColorEnum implements IColor {

	BLACK(0.0, 0.0, 0.0),
	BLUE(0.0, 0.0, 1.0),
	CYAN(0.0, 1.0, 1.0),
	GRAY(0.5, 0.5, 0.5),
	GRAY_06(0.6, 0.6, 0.6),
	GRAY_07(0.7, 0.7, 0.7),
	GREEN(0.0, 1.0, 0.0),
	RED(1.0, 0.0, 0.0),
	WHITE(1.0, 1.0, 1.0),
	YELLOW(1.0, 1.0, 0.0);

	private final IColor color;

	private CssColorEnum(IColor color) {

		this.color = color;
	}

	private CssColorEnum(double red, double green, double blue) {

		this(new RgbColor(red, green, blue));
	}

	public IColor get() {

		return color;
	}

	@Override
	public String toHtml() {

		return color.toHtml();
	}
}
