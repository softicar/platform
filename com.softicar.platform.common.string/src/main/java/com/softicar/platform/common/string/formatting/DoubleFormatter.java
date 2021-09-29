package com.softicar.platform.common.string.formatting;

import com.softicar.platform.common.string.Padding;

/**
 * This class converts a {@link Double} value into a {@link String}.
 * <p>
 * Various formatting parameters can be configured.
 *
 * @author Oliver Richers
 */
public class DoubleFormatter {

	private static final Character DEFAULT_DECIMAL_SEPARATOR = '.';
	private static final Character DEFAULT_GROUP_SEPARATOR = null;
	private static final int DIGIT_GROUP_SIZE = 3;

	private final double value;
	private final int precision;
	private Character decimalSeparator;
	private Character groupSeparator;
	private boolean retainZeros;

	public DoubleFormatter(double value, int precision) {

		this.value = value;
		this.precision = precision;
		this.decimalSeparator = DEFAULT_DECIMAL_SEPARATOR;
		this.groupSeparator = DEFAULT_GROUP_SEPARATOR;
		this.retainZeros = false;
	}

	/**
	 * Defines the character to use for separation of the decimal part.
	 * <p>
	 * The default character is the dot. When <i>null</i> is specified, no
	 * decimal separator is added.
	 *
	 * @param decimalSeparator
	 *            the decimal separator or <i>null</i>
	 * @return this object
	 */
	public DoubleFormatter setDecimalSeparator(Character decimalSeparator) {

		this.decimalSeparator = decimalSeparator;
		return this;
	}

	/**
	 * Defines the character to use for separation of digit groups.
	 * <p>
	 * If <i>null</i> is specified as group separator, groups are not separated,
	 * which is the default.
	 *
	 * @param groupSeparator
	 *            the group separator or <i>null</i>
	 * @return this object
	 */
	public DoubleFormatter setGroupSeparator(Character groupSeparator) {

		this.groupSeparator = groupSeparator;
		return this;
	}

	/**
	 * Toggles retaining of trailing zeros.
	 * <p>
	 * Then disabled, which is the default, trailing zeros in the decimal part
	 * are omitted.
	 *
	 * @param retainZeros
	 *            <i>true</i> to retain trailing zeros; <i>false</i> otherwise
	 * @return this object
	 */
	public DoubleFormatter setRetainZeros(boolean retainZeros) {

		this.retainZeros = retainZeros;
		return this;
	}

	/**
	 * Formats the given double value as configured.
	 *
	 * @return the formatted double value as {@link String} (never null)
	 */
	public String format() {

		double absoluteValue = Math.abs(value);

		// shift value to the left and round
		absoluteValue *= Math.pow(10, precision);
		absoluteValue += 0.5;

		// convert integer part to string
		String valueString = "" + (long) absoluteValue;

		// prepend zeros if string is to short
		valueString = Padding.padLeft(valueString, '0', precision + 1);

		// add decimal point and return
		int len = valueString.length();

		String prefix = valueString.substring(0, len - precision);
		if (groupSeparator != null) {
			prefix = reverse(reverse(prefix).replaceAll("([0-9]{" + DIGIT_GROUP_SIZE + "})", "$1" + groupSeparator)).trim();
			if (prefix.startsWith(groupSeparator + "")) {
				prefix = prefix.substring(1, prefix.length());
			}
		}

		String suffix = valueString.substring(len - precision);
		boolean isEmptySuffix = false;
		String suffixWithoutTrailingZeroes = getWithoutTrailingZeros(suffix);

		if (suffixWithoutTrailingZeroes.equals("")) {
			isEmptySuffix = true;
		}

		if (!retainZeros) {
			suffix = suffixWithoutTrailingZeroes;
		}

		if (isEmptySuffix && !retainZeros) {
			valueString = prefix;
		} else {
			valueString = prefix + (decimalSeparator != null? decimalSeparator : "") + suffix;
		}

		boolean isZero = prefix.equals("0") && isEmptySuffix;

		return (value < 0 && !isZero? "-" : "") + valueString;
	}

	public static String formatDouble(double value, int precision) {

		return formatDouble(value, precision, DEFAULT_DECIMAL_SEPARATOR);
	}

	public static String formatDouble(double value, int precision, Character decimalSeparator) {

		return formatDouble(value, precision, decimalSeparator, DEFAULT_GROUP_SEPARATOR);
	}

	public static String formatDouble(double value, int precision, Character decimalSeparator, Character groupingSeparator) {

		return formatDouble(value, precision, false, decimalSeparator, groupingSeparator);
	}

	/**
	 * Formats the given double value according to the given options while
	 * rounding it to the given precision.
	 *
	 * @param value
	 *            the double number to be formatted
	 * @param precision
	 *            the maximum number of digits after the decimal separator
	 * @param retainZeroes
	 *            whether or not trailing zeroes after the decimal separator
	 *            should be retained
	 * @param decimalSeparator
	 *            the character used as decimal separator
	 * @param groupSeparator
	 *            the character used as digit grouping separator (after each
	 *            triplet of digits to the left of the decimal separator)
	 * @return the formated String
	 */
	public static String formatDouble(double value, int precision, boolean retainZeroes, Character decimalSeparator, Character groupSeparator) {

		return new DoubleFormatter(value, precision)//
			.setDecimalSeparator(decimalSeparator)
			.setGroupSeparator(groupSeparator)
			.setRetainZeros(retainZeroes)
			.format();
	}

	private static String getWithoutTrailingZeros(String valueString) {

		int i = valueString.length() - 1;
		for (; i >= 0; --i) {
			if (valueString.charAt(i) != '0') {
				break;
			}
		}
		return valueString.substring(0, i + 1);
	}

	/**
	 * Reverses the given String.
	 *
	 * @param str
	 * @return A new String containing the characters of the given String in
	 *         reverse order.
	 */
	private static String reverse(String str) {

		return new StringBuilder(str).reverse().toString();
	}
}
