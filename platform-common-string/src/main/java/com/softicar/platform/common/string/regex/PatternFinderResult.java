package com.softicar.platform.common.string.regex;

/**
 * Represents the result of a {@link PatternFinder} method.
 *
 * @author Alexander Schmidt
 */
public class PatternFinderResult {

	private final String matchingText;
	private final int offset;

	PatternFinderResult(String matchingText, Integer offset) {

		this.matchingText = matchingText;
		this.offset = offset;
	}

	/**
	 * Returns the index of the occurrence of the pattern, or {@code -1} if no
	 * match was found.
	 *
	 * @return the pattern index, or {@code -1} if not found
	 */
	public int getOffset() {

		return offset;
	}

	/**
	 * Returns the {@link String} against which the pattern matched, or
	 * <i>null</i> if no match was found.
	 *
	 * @return the {@link String} that matched the pattern, or <i>null</i> if
	 *         not found
	 */
	public String getMatchingText() {

		return matchingText;
	}
}
