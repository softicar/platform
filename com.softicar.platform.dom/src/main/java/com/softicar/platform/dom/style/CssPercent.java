package com.softicar.platform.dom.style;

/**
 * Specialized implementation of {@link CssLength} for percentage lengths.
 *
 * @author Oliver Richers
 */
public class CssPercent extends CssLength {

	public static final CssPercent _0 = new CssPercent(0);
	public static final CssPercent _25 = new CssPercent(25);
	public static final CssPercent _33 = new CssPercent(33);
	public static final CssPercent _50 = new CssPercent(50);
	public static final CssPercent _67 = new CssPercent(66);
	public static final CssPercent _100 = new CssPercent(100);

	public static final CssPercent HUNDRED = new CssPercent(100);

	public CssPercent(double percent) {

		super(CssLengthUnit.PERCENT, percent);
	}
}
