package com.softicar.platform.common.ui.color;

/**
 * This class is only for backwards compatibility.
 * <p>
 * TODO remove this class when not needed anymore
 *
 * @author Oliver Richers
 */
public class Color extends RgbColor {

	public Color(int rgbValue) {

		super(rgbValue);
	}

	public Color(int red, int green, int blue) {

		super(red, green, blue);
	}

	public Color(double red, double green, double blue) {

		super(red, green, blue);
	}
}
