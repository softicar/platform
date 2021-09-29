package com.softicar.platform.dom.style;

/**
 * Specialized implementation of {@link CssLength} for pixel lengths.
 *
 * @author Oliver Richers
 */
public class CssPixel extends CssLength {

	public static final CssPixel ONE = new CssPixel(1);
	public static final CssPixel TWO = new CssPixel(2);
	public static final CssPixel THREE = new CssPixel(3);
	public static final CssPixel FOUR = new CssPixel(4);
	public static final CssPixel FIVE = new CssPixel(5);
	public static final CssPixel TEN = new CssPixel(10);

	public CssPixel(double pixel) {

		super(CssLengthUnit.PX, pixel);
	}
}
