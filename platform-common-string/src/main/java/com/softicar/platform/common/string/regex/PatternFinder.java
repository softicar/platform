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

	public static PatternFinderResult indexOfPattern(String regex, String haystack, int offset) {

		if (offset < 0) {
			throw new IllegalArgumentException("The offset must not be negative.");
		}

		haystack = haystack.substring(offset);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(haystack);

		if (matcher.find()) {
			return new PatternFinderResult(matcher.group(), matcher.start() + offset);
		} else {
			return new PatternFinderResult();
		}
	}

	/**
	 * Finds the index of the first occurrence of the given regex in the given
	 * haystack.
	 * 
	 * @param regex
	 * @param haystack
	 * @return A Pair of (the index of the first occurrence of the given pattern
	 *         in the haystack or -1, if not found) and (the matched substring,
	 *         or an empty String if no match was found).
	 */
	public static PatternFinderResult indexOfPattern(String regex, String haystack) {

		return indexOfPattern(regex, haystack, 0);
	}

	/**
	 * Finds the index of the last occurrence of the given regex in the given
	 * haystack. Inefficient for huge haystacks due to the necessity of crawling
	 * them from the start.
	 * 
	 * @param regex
	 * @param haystack
	 * @return The index of the last occurrence of the given pattern in the
	 *         haystack or -1, if not found.
	 */
	public static int lastIndexOfPattern(String regex, String haystack) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(haystack);

		int lastMatchIndex = -1;

		while (matcher.find()) {
			lastMatchIndex = matcher.start();
		}

		return lastMatchIndex;
	}

}
