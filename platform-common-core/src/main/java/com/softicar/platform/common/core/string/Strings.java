package com.softicar.platform.common.core.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Common utility methods for {@link String}.
 *
 * @author Oliver Richers
 */
public abstract class Strings {

	/**
	 * Reverses the given {@link String}.
	 *
	 * @param string
	 *            the {@link String} to reverse (never <i>null</i>)
	 * @return the reversed {@link String} (never <i>null</i>)
	 */
	public static String reversed(String string) {

		return new StringBuilder(string).reverse().toString();
	}

	/**
	 * Same as {@link String#substring} but clamps the indexes into their valid
	 * range.
	 *
	 * @param string
	 *            the {@link String} to get the substring from (never
	 *            <i>null</i>)
	 * @param begin
	 *            the begin index (inclusive)
	 * @param end
	 *            the end index (exclusive)
	 * @return the substring (never <i>null</i>)
	 */
	public static String substringClamped(String string, int begin, int end) {

		if (begin < string.length() && end > 0 && begin < end) {
			begin = Math.max(begin, 0);
			end = Math.min(end, string.length());
			return string.substring(begin, end);
		} else {
			return "";
		}
	}

	/**
	 * Splits a {@link String} using {@link StringTokenizer}.
	 *
	 * @param string
	 *            the {@link String} to split (never <i>null</i>)
	 * @param delimiter
	 *            the delimiter {@link String} (never <i>null</i> and not empty)
	 * @return the list of substrings
	 */
	public static List<String> tokenize(String string, String delimiter) {

		if (delimiter.isEmpty()) {
			throw new IllegalArgumentException("Delimiter must not be empty.");
		}

		var result = new ArrayList<String>();
		var tokenizer = new StringTokenizer(string, delimiter);
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		return result;
	}
}
