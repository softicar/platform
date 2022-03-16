package com.softicar.platform.common.core.i18n;

/**
 * Implementation of {@link IDisplayString} for formatable text.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class FormattingDisplayString extends AbstractDisplayString {

	private final String string;

	public FormattingDisplayString(String formatString, Object...arguments) {

		this.string = arguments.length > 0? String.format(formatString, arguments) : formatString;
	}

	@Override
	public String toString() {

		return string;
	}
}
