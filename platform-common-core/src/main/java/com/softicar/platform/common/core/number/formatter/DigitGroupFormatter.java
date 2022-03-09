package com.softicar.platform.common.core.number.formatter;

import com.softicar.platform.common.core.string.Strings;

/**
 * Adds digit group separators to a given number string.
 *
 * @author Oliver Richers
 */
class DigitGroupFormatter {

	private static final int DIGIT_GROUP_SIZE = BigDecimalFormatter.getDigitGroupSize();
	private final String reversedNumber;
	private final String reversedSeparator;
	private StringBuilder builder;
	private int index;

	/**
	 * Constructs this instance.
	 *
	 * @param numberString
	 *            the number string, only contain digits and must not be empty
	 *            (never <i>null</i>)
	 * @param separator
	 *            the separator string (never <i>null</i>)
	 */
	public DigitGroupFormatter(String numberString, String separator) {

		this.reversedNumber = Strings.reversed(numberString);
		this.reversedSeparator = Strings.reversed(separator);
	}

	public String format() {

		this.builder = new StringBuilder();
		this.index = 0;

		addDigitGroup();
		while (index < reversedNumber.length()) {
			addSeparator();
			addDigitGroup();
		}

		return builder.reverse().toString();
	}

	private void addDigitGroup() {

		builder.append(Strings.substringClamped(reversedNumber, index, index + DIGIT_GROUP_SIZE));
		index += DIGIT_GROUP_SIZE;
	}

	private void addSeparator() {

		builder.append(reversedSeparator);
	}
}
