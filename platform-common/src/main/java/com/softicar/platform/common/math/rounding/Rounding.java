package com.softicar.platform.common.math.rounding;

import java.math.BigDecimal;

/**
 * Provides rounding and comparison methods for floating point number.
 * 
 * @author Boris Schaa
 * @author Thees Koester
 * @author Oliver Richers
 */
public class Rounding {

	/**
	 * Compares the given values using the specified decimal precision.
	 * <p>
	 * Please read the Javadoc of {@link Rounding#round(double, int)} regarding
	 * floating point types.
	 * 
	 * @param a
	 *            the first value
	 * @param b
	 *            the second value
	 * @param precision
	 *            the number of decimal places to use for comparison
	 * @return true if the given values can be considered as equal with the
	 *         given precision
	 */
	public static boolean equal(double a, double b, int precision) {

		return Math.abs(a - b) < 1.0 / Math.pow(10, precision);
	}

	/**
	 * Rounds the given floating point value to the specified decimal precision.
	 * <p>
	 * Please note, that there are a lot of pitfalls when dealing with decimal
	 * numbers and floating point types like <i>double</i>. For example, <code>
	 * Rounding.round(0.009, 2) == 0.01</code> may be <i>true</i> but it may
	 * also be <i>false</i>. If you really need to deal with decimal numbers
	 * then you should consider using {@link BigDecimal} instead of floating
	 * point types. Especially, when dealing with financial data, correctness
	 * might be more important that speed.
	 * 
	 * @param value
	 *            the floating point value to round
	 * @param precision
	 *            the number of decimal places to round to
	 * @return the given value rounded with the given precision
	 */
	public static double round(double value, int precision) {

		double power = Math.pow(10, precision);
		return Math.round(power * value) / power;
	}
}
