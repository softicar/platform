package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This is a {@link Singleton} to hold the currently selected
 * {@link LanguageEnum}.
 * <p>
 * It is used to determine the destination language for translation.
 *
 * @author Oliver Richers
 */
public class CurrentLanguage {

	private static final Singleton<LanguageEnum> CURRENT_LANGUAGE = new Singleton<>(() -> LanguageEnum.ENGLISH).setInheritByIdentity();

	/**
	 * Returns the currently selected language, which is English by default.
	 *
	 * @return the current language, never null
	 */
	public static LanguageEnum get() {

		return CURRENT_LANGUAGE.get();
	}

	/**
	 * Sets the currently selected language to the specified value.
	 */
	public static void set(LanguageEnum language) {

		CURRENT_LANGUAGE.set(language);
	}
}
