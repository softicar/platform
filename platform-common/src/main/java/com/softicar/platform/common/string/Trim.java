package com.softicar.platform.common.string;

/**
 * Provides static methods for trimming strings.
 *
 * @author Oliver Richers
 */
public class Trim {

	/**
	 * Trims the given text to the specified length.
	 * <p>
	 * If the given text is <i>null</i>, this method will also return
	 * <i>null</i>.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @param maxLength
	 *            the maximum length (must be greater or equal to zero)
	 * @return the trimmed text (or null if the given text was null)
	 */
	public static String trimToLength(String string, int maxLength) {

		if (maxLength < 0) {
			throw new IllegalArgumentException("The maximum length must be a positive integer.");
		} else if (string == null) {
			return null;
		} else if (string.length() > maxLength) {
			return string.substring(0, maxLength);
		} else {
			return string;
		}
	}

	/**
	 * Trims the given {@link String} to the given maximum length.
	 * <p>
	 * If the {@link String} is longer than the maximum length, it is trimmed
	 * and the ellipsis {@link String} is appended. The resulting {@link String}
	 * will then have a length of exactly the given maximum length.
	 * <p>
	 * If the given maximum length is less than the length of the ellipsis, the
	 * ellipsis, trimmed to the maximum length, will be returned.
	 *
	 * @param string
	 *            the {@link String} to trim (never <i>null</i>)
	 * @param maximumLength
	 *            the maximum length of the {@link String}; must be at least the
	 *            length of the ellipsis
	 * @param ellipsis
	 *            the ellipsis {@link String} to append if the {@link String}
	 *            was trimmed (never <i>null</i>)
	 * @return the trimmed {@link String} (never <i>null</i>)
	 */
	public static String trimToLengthWithEllipsis(String string, int maximumLength, String ellipsis) {

		if (maximumLength <= 0) {
			return "";
		} else if (maximumLength < ellipsis.length()) {
			return trimToLength(ellipsis, maximumLength);
		} else if (string.length() > maximumLength) {
			return trimToLength(string, maximumLength - ellipsis.length()) + ellipsis;
		} else {
			return string;
		}
	}

	/**
	 * Trims the prefix from the given text.
	 * <p>
	 * The prefix is only removed once. If the given text does not start with
	 * the prefix, the text is returned as is.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @param prefix
	 *            the prefix to remove (never null)
	 * @return the text without the prefix (never null)
	 */
	public static String trimPrefix(String string, String prefix) {

		if (string.startsWith(prefix)) {
			return string.substring(prefix.length());
		} else {
			return string;
		}
	}

	/**
	 * Trims the suffix from the given text.
	 * <p>
	 * The suffix is only removed once. If the given text does not end with the
	 * suffix, the text is returned as is.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @param suffix
	 *            the suffix to remove (never null)
	 * @return the text without the suffix (never null)
	 */
	public static String trimSuffix(String string, String suffix) {

		if (string.endsWith(suffix)) {
			return string.substring(0, string.length() - suffix.length());
		} else {
			return string;
		}
	}

	/**
	 * Trims the leading whitespace from the given text.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @return the text without leading whitespace
	 */
	public static String trimLeft(String string) {

		for (int i = 0; i < string.length(); i++) {
			if (!Character.isWhitespace(string.charAt(i))) {
				return string.substring(i);
			}
		}
		return "";
	}

	/**
	 * Trims the trailing whitespace from the given text.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @return the text without trailing whitespace
	 */
	public static String trimRight(String string) {

		for (int i = string.length(); i > 0; i--) {
			if (!Character.isWhitespace(string.charAt(i - 1))) {
				return string.substring(0, i);
			}
		}
		return "";
	}

	/**
	 * Trims all leading zeros removed from the given text.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @return the trimmed text
	 */
	public static String trimZeros(String string) {

		return trimLeft(string, '0');
	}

	/**
	 * Trims all matching characters from the beginning of the given text.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @param c
	 *            the character to remove
	 * @return the trimmed text
	 */
	public static String trimLeft(String string, char c) {

		int first = 0;
		int n = string.length();
		while (first < n && string.charAt(first) == c) {
			++first;
		}
		return string.substring(first, n);
	}

	/**
	 * Trims all matching characters from the ending of the given text.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @param c
	 *            the character to remove
	 * @return the trimmed text
	 */
	public static String trimRight(String string, char c) {

		int last = string.length() - 1;
		while (last >= 0 && string.charAt(last) == c) {
			--last;
		}
		return string.substring(0, last + 1);
	}

	/**
	 * Trims all matching characters from the beginning and ending of the given
	 * text.
	 *
	 * @param string
	 *            the text to trim (never null)
	 * @param c
	 *            the character to remove
	 * @return the trimmed text
	 */
	public static String trim(String string, char c) {

		return trimLeft(trimRight(string, c), c);
	}

	/**
	 * Trims the given string and checks for empty strings.
	 * <p>
	 * If the given string is <i>null</i>, the default value will be returned.
	 * Also, if the trimmed string is empty, the default value will be returned.
	 *
	 * @param string
	 *            the string to trim (may be null)
	 * @return the trimmed string or the default value (may be null)
	 */
	public static String trimOrDefault(String string, String defaultValue) {

		if (string != null) {
			string = string.trim();
			return string.isEmpty()? defaultValue : string;
		} else {
			return defaultValue;
		}
	}
}
