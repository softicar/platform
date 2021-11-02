package com.softicar.platform.common.string.parsing;

import com.softicar.platform.common.string.regex.PatternFinder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexander Schmidt
 */
public class NumberStringCleaner {

	private static final String ALL_VALID_CHARACTERS = "[\\.,' :0-9]";
	private static final String DECIMAL_SEPARATORS_REGEX_CLASS = "[\\.,]";
	private static final String GROUPING_SEPARATORS_REGEX_CLASS = "[\\.,' :]";
	private static final Pattern GROUPING_SEPARATORS_PATTERN = Pattern.compile(GROUPING_SEPARATORS_REGEX_CLASS);

	/**
	 * Checks if the given String can be interpreted as a number. If so, it is
	 * stripped off any unnecessary decimal and/or digit grouping separators,
	 * solely retaining a dot as a decimal separator. If the given String is not
	 * considered a number, no conversion is applied.
	 * <p>
	 * Note: Doing such conversions should not be necessary. However, at the
	 * time of implementing this, they must be done, e.g. during table-to-Excel
	 * exports as a part of type inference, due to a lack of type safety in
	 * tables displayed in SoftiCAR.
	 *
	 * @param numberString
	 *            the numeric string to clean (may be null)
	 * @return A cleanly formatted numeric string. If the given String is not
	 *         considered a number, it is returned. If the given String is null,
	 *         null is returned.
	 */
	public static String convertToCleanNumberString(String numberString) {

		if (numberString != null) {
			String formatMe = numberString.trim();
			boolean isNegative = false;

			if (formatMe.startsWith("-")) {
				formatMe = formatMe.substring(1, formatMe.length());
				isNegative = true;
			}

			if (formatMe.matches(ALL_VALID_CHARACTERS + "+")) {
				int lastSeparatorIndex = PatternFinder.lastIndexOfPattern(DECIMAL_SEPARATORS_REGEX_CLASS, formatMe);

				boolean charAfterLastSeparatorIndexIsNumber = checkCharacterAfterLastSeparatorIndexIsNumber(formatMe, lastSeparatorIndex);
				boolean charBeforeLastSeparatorIndexIsNumber = checkCharacterBeforeLastSeparatorIndexIsNumber(formatMe, lastSeparatorIndex);

				if (charAfterLastSeparatorIndexIsNumber && charBeforeLastSeparatorIndexIsNumber) {
					String prefix = formatMe.substring(0, lastSeparatorIndex);
					char foundDecimalSeparator = formatMe.charAt(lastSeparatorIndex);
					String suffix = formatMe.substring(lastSeparatorIndex + 1);

					String uniqueGroupingSeparatorOrNull = fetchUniqueGroupingSeparatorOrNull(prefix, foundDecimalSeparator);
					if (uniqueGroupingSeparatorOrNull != null) {
						prefix = prefix.replace(uniqueGroupingSeparatorOrNull, "");
					}

					return (isNegative? "-" : "") + prefix + "." + suffix;
				}
			}
		}

		return numberString;
	}

	private static boolean checkCharacterAfterLastSeparatorIndexIsNumber(String input, int lastSeparatorIndex) {

		Character charAfterLastSeparatorIndex = null;
		if (lastSeparatorIndex < input.length() - 1) {
			charAfterLastSeparatorIndex = input.charAt(lastSeparatorIndex + 1);
		}
		return (charAfterLastSeparatorIndex != null && charAfterLastSeparatorIndex.toString().matches("[0-9]"));
	}

	private static boolean checkCharacterBeforeLastSeparatorIndexIsNumber(String input, int lastSeparatorIndex) {

		Character charBeforeLastSeparatorIndex = null;
		if (lastSeparatorIndex > 0) {
			charBeforeLastSeparatorIndex = input.charAt(lastSeparatorIndex - 1);
		}
		return ((charBeforeLastSeparatorIndex != null && charBeforeLastSeparatorIndex.toString().matches("[0-9]")) || (lastSeparatorIndex) == 0);//doubles may start with a dot
	}

	private static String fetchUniqueGroupingSeparatorOrNull(String input, char foundDecimalSeparator) {

		Matcher matcher = GROUPING_SEPARATORS_PATTERN.matcher(input);

		String lastCharacter = null;
		while (matcher.find()) {
			String currentCharacter = matcher.group();

			boolean foundDifferentGroupers = (lastCharacter != null && !currentCharacter.equalsIgnoreCase(lastCharacter));
			boolean equalsDecimalSeparator = (currentCharacter.equals(foundDecimalSeparator + ""));

			if (foundDifferentGroupers || equalsDecimalSeparator) {
				return null;
			} else {
				lastCharacter = currentCharacter;
			}
		}

		return lastCharacter;
	}
}
