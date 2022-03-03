package com.softicar.platform.common.core.locale;

/**
 * Default implemenation of {@link ILocale}.
 *
 * @author Oliver Richers
 */
public class Locale implements ILocale {

	private String decimalSeparator;
	private String digitGroupSeparator;

	public Locale() {

		this.decimalSeparator = ".";
		this.digitGroupSeparator = "";
	}

	@Override
	public String getDecimalSeparator() {

		return decimalSeparator;
	}

	public Locale setDecimalSeparator(String decimalSeparator) {

		this.decimalSeparator = decimalSeparator;
		return this;
	}

	@Override
	public String getDigitGroupSeparator() {

		return digitGroupSeparator;
	}

	public Locale setDigitGroupSeparator(String digitGroupSeparator) {

		this.digitGroupSeparator = digitGroupSeparator;
		return this;
	}
}
