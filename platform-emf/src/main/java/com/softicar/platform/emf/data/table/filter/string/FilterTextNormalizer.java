package com.softicar.platform.emf.data.table.filter.string;

class FilterTextNormalizer {

	private final String filterText;

	public FilterTextNormalizer(String filterText) {

		this.filterText = filterText;
	}

	public String getNormalized() {

		if (filterText != null) {
			return getFilter().trim();
		} else {
			return null;
		}
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
