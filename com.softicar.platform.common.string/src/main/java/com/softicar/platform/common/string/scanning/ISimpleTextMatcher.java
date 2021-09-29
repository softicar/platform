package com.softicar.platform.common.string.scanning;

public interface ISimpleTextMatcher {

	/**
	 * Checks the given text for a matching prefix.
	 * <p>
	 * If the given text start with a matching prefix, this method returns the
	 * number of matching characters.
	 * 
	 * @param text
	 *            the text to check
	 * @return the matching length or 0 if there is no match
	 */
	int getMatchingLength(String text);

	/**
	 * Consumes the matching text.
	 * 
	 * @param text
	 *            the text to consume
	 */
	void consumeMatchingText(String text);
}
