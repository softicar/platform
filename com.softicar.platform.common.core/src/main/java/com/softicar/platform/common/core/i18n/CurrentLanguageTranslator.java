package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This is a {@link Singleton} to hold the currently used
 * {@link ILanguageTranslator}.
 * <p>
 * It is used to translate texts into the currently selected language.
 *
 * @author Oliver Richers
 */
public class CurrentLanguageTranslator {

	private static final Singleton<ILanguageTranslator> CURRENT_TRANSLATOR = new Singleton<>(IdentityLanguageTranslator::get);

	/**
	 * Returns the currently selected language translator, which is
	 * {@link IdentityLanguageTranslator} by default.
	 *
	 * @return the current translator, never null
	 */
	public static ILanguageTranslator get() {

		return CURRENT_TRANSLATOR.get();
	}

	/**
	 * Sets the currently selected language translator to the specified
	 * instance.
	 */
	public static void set(ILanguageTranslator translator) {

		CURRENT_TRANSLATOR.set(translator);
	}
}
