package com.softicar.platform.common.string.unicode;

/**
 * Replaces all unicode characters that are not from the Basic Multilingual
 * Plane (BMP).
 * <p>
 * The unicode characters in the BMP are exactly those characters that can be
 * encoded in a single 16-bit code point or as a 3-byte UTF-8 character.
 *
 * @author Oliver Richers
 */
public class UtfNonBmpCharacterReplacer {

	private static final String DEFAULT_REPLACEMENT = "??";
	private String replacement;

	public UtfNonBmpCharacterReplacer() {

		setReplacement(DEFAULT_REPLACEMENT);
	}

	public UtfNonBmpCharacterReplacer setReplacement(String replacement) {

		this.replacement = replacement;
		return this;
	}

	public String getReplacement() {

		return replacement;
	}

	public String getReplaced(String text) {

		if (text != null && hasNonBmpCharacters(text)) {
			return doClean(text);
		} else {
			return text;
		}
	}

	private String doClean(String text) {

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isHighSurrogate(c)) {
				builder.append(replacement);
			} else if (Character.isLowSurrogate(c)) {
				// skip
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	private boolean hasNonBmpCharacters(String text) {

		for (int i = 0; i < text.length(); i++) {
			if (Character.isSurrogate(text.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
