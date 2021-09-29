package com.softicar.platform.common.math.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 * convenience methods for BigDecimal manipulations
 *
 * @author Semsudin Mekanic
 */
public class BigDecimalFunctions {

	public static boolean isInBetween(BigDecimal value, BigDecimal from, BigDecimal to) {

		if (value.compareTo(from) >= 0 && value.compareTo(to) <= 0) {
			return true;
		}
		return false;
	}

	public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {

		return value != null? value.setScale(scale, roundingMode) : null;
	}

	public static BigDecimal round(BigDecimal value, int scale) {

		return value != null? value.setScale(scale, RoundingMode.HALF_UP) : null;
	}

	public static BigDecimal round(Double value, int scale) {

		return value != null? BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP) : null;
	}

	public static BigDecimal round(Double value, int scale, RoundingMode roundingMode) {

		return value != null? BigDecimal.valueOf(value).setScale(scale, roundingMode) : null;
	}

	public static BigDecimal round(Float value, int scale) {

		return value != null? BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP) : null;
	}

	public static BigDecimal round(Integer value, int scale) {

		return value != null? BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP) : null;
	}

	public static BigDecimal parse(String value) {

		// Create a DecimalFormat that fits your requirements
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#,##0.0#";

		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// parse the string
		try {
			return (BigDecimal) decimalFormat.parse(value);
		} catch (ParseException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String formatToStringGerman(BigDecimal value, String defaultValue, boolean stripTrailingZeros, boolean addGroupingSeparator) {

		return formatToString(value, defaultValue, stripTrailingZeros, addGroupingSeparator, Locale.GERMANY);
	}

	public static String formatToStringGerman(BigDecimal value, int scale, String defaultValue, boolean stripTrailingZeros, boolean addGroupingSeparator) {

		return formatToString(value, scale, defaultValue, stripTrailingZeros, addGroupingSeparator, Locale.GERMANY);
	}

	public static String formatToString(BigDecimal value, String defaultValue, boolean stripTrailingZeros, boolean addGroupingSeparator, Locale locale) {

		if (value == null) {
			return defaultValue;
		}
		BigDecimal newValue = value.multiply(BigDecimal.ONE);
		if (stripTrailingZeros) {
			newValue = newValue.stripTrailingZeros();
		}
		return formatBigDecimal(newValue, addGroupingSeparator, locale);
	}

	public static String formatToString(BigDecimal value, int scale, String defaultValue, boolean stripTrailingZeros, boolean addGroupingSeparator,
			Locale locale) {

		if (value == null) {
			return defaultValue;
		}
		BigDecimal newValue = value.multiply(BigDecimal.ONE);
		newValue = BigDecimalFunctions.round(newValue, scale);

		if (stripTrailingZeros) {
			newValue = newValue.stripTrailingZeros();
		}
		return formatBigDecimal(newValue, addGroupingSeparator, locale);
	}

	public static String formatBigDecimal(BigDecimal valueBigDecimal, boolean addGroupingSeparator, Locale locale) {

		if (valueBigDecimal == null) {
			return null;
		}
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
		String[] valueStrings = valueBigDecimal.toPlainString().split("\\.");
		String integerPart = valueStrings.length == 2? valueStrings[0] : valueBigDecimal.toPlainString();
		String fractionalPart = valueStrings.length == 2? valueStrings[1] : null;
		StringBuilder sb;
		if (addGroupingSeparator) {
			String integerPartWithoutIndent = integerPart.replace("-", "");
			int numberOfPoints = integerPartWithoutIndent.length() / 3;
			if (integerPartWithoutIndent.length() % 3 == 0) {
				numberOfPoints--;
			}
			int capacity = integerPart.length() + numberOfPoints + (fractionalPart != null? fractionalPart.length() + 1 : 0);
			sb = new StringBuilder(capacity).append(integerPart).reverse();
			for (int i = 0; i < numberOfPoints; i++) {
				sb.insert(i + i * 3 + 3, decimalFormatSymbols.getGroupingSeparator());
			}
			sb = sb.reverse();
		} else {
			int capacity = integerPart.length() + (fractionalPart != null? fractionalPart.length() + 1 : 0);
			sb = new StringBuilder(capacity).append(integerPart);
		}

		if (fractionalPart != null) {
			sb.append(decimalFormatSymbols.getDecimalSeparator());
			sb.append(fractionalPart);
		}
		return sb.toString();
	}

	public static BigDecimal createScaled(int value, int scale) {

		return BigDecimal.valueOf(value * Double.valueOf(Math.pow(10, scale)).longValue(), scale);
	}
}
