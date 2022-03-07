package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This {@link Singleton} holds the currently selected {@link ILocale}.
 *
 * @author Oliver Richers
 */
public class CurrentLocale {

	private static final ILocale DEFAULT_LOCALE = new Locale();
	private static final Singleton<ILocale> CURRENT_LOCALE = new Singleton<>(() -> DEFAULT_LOCALE).setInheritByIdentity();

	/**
	 * Returns the current {@link ILocale} to use.
	 *
	 * @return the current {@link ILocale} (never <i>null</i>)
	 */
	public static ILocale get() {

		return CURRENT_LOCALE.get();
	}

	/**
	 * Sets the current {@link ILocale} to the specified value.
	 *
	 * @param locale
	 *            the {@link ILocale} to use or <i>null</i> to reset to default
	 */
	public static void set(ILocale locale) {

		CURRENT_LOCALE.set(locale);
	}
}
