package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This {@link Singleton} holds the currently selected {@link LanguageEnum}.
 * <p>
 * It is used to determine the destination language for translation.
 *
 * @author Oliver Richers
 */
public class CurrentLanguage {

	private static final Singleton<LanguageEnum> CURRENT_LANGUAGE = new Singleton<>(() -> LanguageEnum.ENGLISH).setInheritByIdentity();

	/**
	 * Returns the current language, which is English by default.
	 *
	 * @return the current language (never <i>null</i>)
	 */
	public static LanguageEnum get() {

		return CURRENT_LANGUAGE.get();
	}

	/**
	 * Sets the current language to the specified value.
	 *
	 * @param language
	 *            the {@link LanguageEnum} to use or <i>null</i> to reset to
	 *            default
	 */
	public static void set(LanguageEnum language) {

		CURRENT_LANGUAGE.set(language);
	}
}
