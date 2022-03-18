package com.softicar.platform.common.core.i18n;

/**
 * Implementation of {@link IDisplayString} for plain text.
 *
 * @author Alexander Schmidt
 */
class PlainDisplayString extends AbstractDisplayString {

	private final String string;

	public PlainDisplayString(String string) {

		this.string = string;
	}

	@Override
	public String toString() {

		return string;
	}

	/**
	 * Returns a {@link PlainDisplayString} which contains an empty
	 * {@link String}.
	 *
	 * @return an empty {@link PlainDisplayString} (never null)
	 */
	public static PlainDisplayString getEmpty() {

		return new PlainDisplayString("");
	}
}
