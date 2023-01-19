package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import java.text.SimpleDateFormat;

/**
 * Interface for locale configuration.
 *
 * @author Oliver Richers
 */
public interface ILocale {

	/**
	 * Returns the {@link LanguageEnum} to be used for translations.
	 *
	 * @return the {@link LanguageEnum} to use (never <i>null</i>)
	 */
	LanguageEnum getLanguage();

	/**
	 * Returns the {@link String} to be used as decimal separator.
	 * <p>
	 * Common values are the decimal comma (,) or decimal point (.).
	 * <p>
	 * This separator must be different to the separator returned by
	 * {@link #getDigitGroupSeparator()}.
	 *
	 * @return the decimal separator (never <i>null</i> and not empty)
	 */
	String getDecimalSeparator();

	/**
	 * Returns the {@link String} to be used as separator for digit groups.
	 * <p>
	 * Common values are space ( ), comma (,), point (.) or apostrophe (') or
	 * the empty {@link String}.
	 * <p>
	 * This separator must be different to the separator returned by
	 * {@link #getDecimalSeparator()}.
	 *
	 * @return the decimal separator (never <i>null</i> but may be empty)
	 */
	String getDigitGroupSeparator();

	/**
	 * Returns the {@link String} to use as a date format, in
	 * {@link SimpleDateFormat} compliant syntax.
	 *
	 * @return the date format (never <i>null</i> and not empty)
	 */
	String getDateFormat();

	/**
	 * Returns a human-readable date format that uses abbreviations which
	 * correspond to the language returned by {@link #getLanguage()}.
	 *
	 * @return a localized, human-readable date format (never <i>null</i>)
	 */
	String getLocalizedDateFormat();

	/**
	 * Returns a human-readable time format that uses abbreviations which
	 * correspond to the language returned by {@link #getLanguage()}.
	 *
	 * @return a localized, human-readable time format (never <i>null</i>)
	 */
	String getLocalizedTimeFormat();

	/**
	 * Returns a human-readable date-and-time format that uses abbreviations
	 * which correspond to the language returned by {@link #getLanguage()}.
	 * <p>
	 * By default, a space-separated concatenation of
	 * {@link #getLocalizedDateFormat()} and {@link #getLocalizedTimeFormat()}
	 * is returned.
	 *
	 * @return a localized, human-readable date-and-time format (never
	 *         <i>null</i>)
	 */
	default String getLocalizedDayTimeFormat() {

		return "%s %s".formatted(getLocalizedDateFormat(), getLocalizedTimeFormat());
	}
}
