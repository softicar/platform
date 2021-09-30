package com.softicar.platform.common.ui.wiki.element.inline;

class WikiWhitespaceNormalizer {

	private final String text;
	private StringBuilder builder;
	private boolean whitespace;

	public WikiWhitespaceNormalizer(String text) {

		this.text = text;
	}

	public String getNormalized() {

		this.builder = new StringBuilder();
		this.whitespace = false;

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			if (Character.isWhitespace(character)) {
				appendWhitespace();
			} else {
				appendNonWhitespace(character);
			}
		}

		return builder.toString();
	}

	// -------------------- private -------------------- //

	private void appendWhitespace() {

		if (!whitespace) {
			builder.append(' ');
			whitespace = true;
		}
	}

	private void appendNonWhitespace(char character) {

		builder.append(character);
		whitespace = false;
	}
}
