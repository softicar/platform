package com.softicar.platform.common.core.i18n;

/**
 * Implementation of {@link IDisplayString} for plain text.
 *
 * @author Alexander Schmidt
 */
class PlainDisplayString implements IDisplayString {

	private final String string;

	public PlainDisplayString(String string) {

		this.string = string;
	}

	@Override
	public String toString() {

		return string;
	}
}
