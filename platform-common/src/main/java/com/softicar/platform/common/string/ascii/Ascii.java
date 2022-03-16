package com.softicar.platform.common.string.ascii;

/**
 * Utility methods for the ASCII character set.
 *
 * @author Oliver Richers
 */
public class Ascii {

	/**
	 * Checks whether the given character is an ASCII character in the normal
	 * range [0-127].
	 *
	 * @param character
	 *            the character to check
	 * @return true if the character is a valid ASCII character
	 */
	public static boolean isAscii(char character) {

		return character >= 0 && character < 128;
	}

	/**
	 * Checks whether the given text contains only ASCII characters.
	 *
	 * @param text
	 *            the text to check
	 * @return true if the text contains only valid ASCII characters
	 */
	public static boolean isAscii(String text) {

		for (int i = 0; i < text.length(); i++) {
			if (!isAscii(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
