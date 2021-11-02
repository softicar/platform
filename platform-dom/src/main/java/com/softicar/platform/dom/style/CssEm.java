package com.softicar.platform.dom.style;

/**
 * Specialized implementation of {@link CssLength} for unit
 * {@link CssLengthUnit#EM}.
 *
 * @author Oliver Richers
 */
public class CssEm extends CssLength {

	public static final CssEm ONE = new CssEm(1);

	public CssEm(double em) {

		super(CssLengthUnit.EM, em);
	}
}
