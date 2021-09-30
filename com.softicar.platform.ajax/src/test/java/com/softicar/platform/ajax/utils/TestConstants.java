package com.softicar.platform.ajax.utils;

public interface TestConstants {

	/**
	 * Regular ASCII characters.
	 */
	String ASCII_TEXT = "abc +-;./\\foo";

	/**
	 * Special text that uses the HTML character encoding.
	 */
	String HTML_ENCODED_TEXT = "&quot;&apos;";

	/**
	 * Characters with diacritics as well as currency characters, etc.
	 */
	String NON_LATIN_CHARACTERS = "Đš\u1E9E\u20ac$\u00a3";

	/**
	 * Special unicode whitespace, e.g. no-break space, line break, etc.
	 */
	String SPECIAL_UNICODE_WHITESPACE = "\u00A0\u2028\u2029";

	/**
	 * The end tag for an HTML script.
	 */
	String END_OF_SCRIPT = "</script>";

	/**
	 * Combination of all special character texts.
	 */
	String SPECIAL_TEXT = ASCII_TEXT + HTML_ENCODED_TEXT + NON_LATIN_CHARACTERS + SPECIAL_UNICODE_WHITESPACE + END_OF_SCRIPT;

	/**
	 * Combination of special character texts without special unicode
	 * whitespace.
	 */
	String SPECIAL_TEXT_WITHOUT_UNICODE_WHITESPACE = ASCII_TEXT + HTML_ENCODED_TEXT + NON_LATIN_CHARACTERS + END_OF_SCRIPT;
}
