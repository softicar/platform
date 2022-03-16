package com.softicar.platform.common.string.trim;

/**
 * Trims empty lines from beginning and ending of a string.
 *
 * @author Oliver Richers
 */
public class MultiLineStringTrimmer {

	private String value;

	public MultiLineStringTrimmer(String value) {

		this.value = value;
	}

	public String trim() {

		trimLeadingEmptyLines();
		trimTrailingEmptyLines();
		trimCarriageReturn();
		return value;
	}

	private void trimLeadingEmptyLines() {

		while (true) {
			int index = value.indexOf("\n");
			if (index >= 0 && value.substring(0, index).trim().isEmpty()) {
				this.value = value.substring(index + 1);
			} else {
				break;
			}
		}
	}

	private void trimTrailingEmptyLines() {

		while (true) {
			int index = value.lastIndexOf("\n");
			if (index >= 0 && value.substring(index).trim().isEmpty()) {
				this.value = value.substring(0, index);
			} else {
				break;
			}
		}
	}

	private void trimCarriageReturn() {

		while (value.endsWith("\r")) {
			this.value = value.substring(0, value.length() - 1);
		}
	}
}
