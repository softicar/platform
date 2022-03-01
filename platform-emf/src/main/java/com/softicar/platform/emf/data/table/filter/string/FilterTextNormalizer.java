package com.softicar.platform.emf.data.table.filter.string;

import java.util.Objects;

class FilterTextNormalizer {

	private final String filterText;

	public FilterTextNormalizer(String filterText) {

		this.filterText = Objects.requireNonNull(filterText);
	}

	public String getNormalized() {

		return getFilter().trim();
	}

	private String getFilter() {

		StringBuilder output = new StringBuilder(filterText.length());
		for (int i = 0; i < filterText.length(); i++) {
			output.append(getReplacement(filterText.charAt(i)));
		}
		return output.toString();
	}

	private char getReplacement(char input) {

		if (input < '\u0020') {
			// control characters
			return ' ';
		} else if (input >= '\u007F' && input <= '\u009F') {
			// unicode control characters
			return ' ';
		} else if (input == '\u00A0') {
			// non-breaking space
			return ' ';
		} else {
			return input;
		}
	}
}
