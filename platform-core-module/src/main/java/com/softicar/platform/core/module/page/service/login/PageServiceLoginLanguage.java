package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.CurrentAjaxRequest;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The language of the pages before the login (login, one-time password and
 * password reset).
 * <p>
 * A visitor can choose it on the login page; the choice is kept in the cookie
 * {@link #COOKIE}. Without a choice, the language of the default localization
 * of the installation applies. After the login, the application uses the
 * localization of the user.
 */
public class PageServiceLoginLanguage {

	/** the languages offered on the login page */
	public static final List<LanguageEnum> LANGUAGES = List.of(LanguageEnum.BOSNIAN, LanguageEnum.ENGLISH, LanguageEnum.GERMAN);
	/** the cookie with the chosen language (ISO 639-1 code) */
	public static final String COOKIE = "loginLanguage";

	/**
	 * Returns the locale for the pages before the login: the default
	 * localization of the installation, in the chosen language.
	 *
	 * @param document
	 *            the current {@link IAjaxDocument} (never <i>null</i>)
	 * @return the {@link ILocale} to use (never <i>null</i>)
	 */
	public static ILocale getLocale(IAjaxDocument document) {

		ILocale defaultLocale = AGCoreModuleInstance.getInstance().getDefaultLocalization().getLocale();
		return getChosenLanguage(document)//
			.map(language -> (ILocale) copyWithLanguage(defaultLocale, language))
			.orElse(defaultLocale);
	}

	/**
	 * Returns the language chosen on the login page.
	 *
	 * @param document
	 *            the current {@link IAjaxDocument} (never <i>null</i>)
	 * @return the chosen {@link LanguageEnum}; empty if none or an unknown one
	 *         was chosen
	 */
	public static Optional<LanguageEnum> getChosenLanguage(IAjaxDocument document) {

		return CurrentAjaxRequest//
			.get(document)
			.map(IAjaxRequest::getHttpRequest)
			.map(HttpServletRequest::getCookies)
			.flatMap(PageServiceLoginLanguage::getChosenLanguage);
	}

	/**
	 * Returns the language chosen in the given cookies.
	 *
	 * @param cookies
	 *            the cookies of the request (never <i>null</i>)
	 * @return the chosen {@link LanguageEnum}; empty if none or an unknown one
	 *         was chosen
	 */
	public static Optional<LanguageEnum> getChosenLanguage(Cookie[] cookies) {

		return Arrays//
			.stream(cookies)
			.filter(cookie -> COOKIE.equals(cookie.getName()))
			.map(cookie -> LanguageEnum.getByIso6391(cookie.getValue()))
			.filter(Objects::nonNull)
			.filter(LANGUAGES::contains)
			.findFirst();
	}

	/**
	 * Returns the name of the given language in that language ("Bosanski",
	 * "English", "Deutsch").
	 *
	 * @param language
	 *            the language (never <i>null</i>)
	 * @return the name (never <i>null</i>)
	 */
	public static String getOwnName(LanguageEnum language) {

		try (var scope = new LocaleScope(new Locale().setLanguage(language))) {
			return language.toDisplay().toString();
		}
	}

	/**
	 * Returns the script code that stores the given language in the cookie and
	 * loads the page again.
	 *
	 * @param language
	 *            the language (never <i>null</i>)
	 * @return the script code (never <i>null</i>)
	 */
	public static String getChooseScriptCode(LanguageEnum language) {

		return "document.cookie='" + COOKIE + "=" + language.getIso6391() + "; path=/; max-age=31536000; SameSite=Lax';"
				+ "location.assign(location.href.split('#')[0]);";
	}

	private static Locale copyWithLanguage(ILocale locale, LanguageEnum language) {

		return new Locale()//
			.setLanguage(language)
			.setDecimalSeparator(locale.getDecimalSeparator())
			.setDigitGroupSeparator(locale.getDigitGroupSeparator())
			.setDateFormat(locale.getDateFormat());
	}
}
