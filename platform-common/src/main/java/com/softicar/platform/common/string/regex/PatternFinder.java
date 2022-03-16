package com.softicar.platform.common.string.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility methods to search for {@link Pattern} in strings.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class PatternFinder {

	/**
	 * Finds the index of the first occurrence of the given regular expression
	 * in the given text.
	 *
	 * @param regex
	 *            the regular expression (never <i>null</i>)
	 * @param text
	 *            the text to search in (never <i>null</i>)
	 * @return a {@link PatternFinderResult} that contains the search result
	 *         (never <i>null</i>)
	 */
	public static PatternFinderResult indexOfPattern(String regex, String text) {

		return indexOfPattern(regex, text, 0);
	}

	/**
	 * Finds the index of the first occurrence of the given regular expression
	 * in the given text. Starts searching at the given offset.
	 *
	 * @param regex
	 *            the regular expression (never <i>null</i>)
	 * @param text
	 *            the text to search in (never <i>null</i>)
	 * @return a {@link PatternFinderResult} that contains the search result
	 *         (never <i>null</i>)
	 */
	public static PatternFinderResult indexOfPattern(String regex, String text, int offset) {

		return indexOfPattern(Pattern.compile(regex), text, offset);
	}

	/**
	 * Finds the index of the first occurrence of the given {@link Pattern} in
	 * the given text. Starts searching at the given offset.
	 *
	 * @param pattern
	 *            the {@link Pattern} (never <i>null</i>)
	 * @param text
	 *            the text to search in (never <i>null</i>)
	 * @return a {@link PatternFinderResult} that contains the search result
	 *         (never <i>null</i>)
	 */
	public static PatternFinderResult indexOfPattern(Pattern pattern, String text, int offset) {

		if (offset < 0) {
			throw new IllegalArgumentException("The offset must not be negative.");
		}

		text = text.substring(offset);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			return new PatternFinderResult(matcher.group(), matcher.start() + offset);
		} else {
			return new PatternFinderResult(null, -1);
		}
	}

	/**
	 * Finds the index of the last occurrence of the given regular expression in
	 * the given text.
	 *
	 * @param regex
	 *            the regular expression (never <i>null</i>)
	 * @param text
	 *            the text to search in (never <i>null</i>)
	 * @return a {@link PatternFinderResult} that contains the search result
	 *         (never <i>null</i>)
	 */
	public static int lastIndexOfPattern(String regex, String text) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		int lastMatchIndex = -1;

		while (matcher.find()) {
			lastMatchIndex = matcher.start();
		}

		return lastMatchIndex;
	}

}
