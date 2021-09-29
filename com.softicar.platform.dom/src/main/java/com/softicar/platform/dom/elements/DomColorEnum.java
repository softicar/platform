package com.softicar.platform.dom.elements;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.common.ui.color.RgbColor;

/**
 * Enumeration of colors.
 * <p>
 * TODO This enumeration should be eliminated. The colors should be defined in
 * style sheets instead.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public enum DomColorEnum implements IColor {

	// ---------- base colors ---------- //
	BLACK(0, 0, 0),
	DARK_GREEN(0, 128, 0),
	DARK_RED(128, 0, 0),
	RED(255, 0, 0),
	YELLOW(255, 255, 0),

	// ---------- generic colors ---------- //
	ORANGE(255, 165, 0),
	SOFT_RED(214, 192, 192),

	// ---------- specialized colors ---------- //
	MESSAGE_BLUE_DARK(0x518ABF),
	MESSAGE_RED_DARK(182, 0, 0),
	MESSAGE_YELLOW_DARKER(203, 161, 36),
	//
	;

	private RgbColor color;

	private DomColorEnum(int rgbValue) {

		this.color = new RgbColor(rgbValue);
	}

	private DomColorEnum(int red, int green, int blue) {

		this.color = new RgbColor(red, green, blue);
	}

	public RgbColor getRgbColor() {

		return color;
	}

	@Override
	public String toHtml() {

		return color.toHtml();
	}
}
