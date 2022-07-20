package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.core.i18n.LanguageEnum;

/**
 * Default implemenation of {@link ILocale}.
 *
 * @author Oliver Richers
 */
public class Locale implements ILocale {

	private LanguageEnum language;
	private String decimalSeparator;
	private String digitGroupSeparator;
	private String dateFormat;

	public Locale() {

		this.language = LanguageEnum.ENGLISH;
		this.decimalSeparator = ".";
		this.digitGroupSeparator = "";
		this.dateFormat = "yyyy-MM-dd";
	}

	@Override
	public LanguageEnum getLanguage() {

		return language;
	}

	public Locale setLanguage(LanguageEnum language) {

		this.language = language;
		return this;
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

	@Override
	public String getDateFormat() {

		return dateFormat;
	}

	public Locale setDateFormat(String dateFormat) {

		this.dateFormat = dateFormat;
		return this;
	}
}
