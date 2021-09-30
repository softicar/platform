package com.softicar.platform.common.core.i18n;

/**
 * This is the identity language translator.
 * <p>
 * The texts to be translated are returned as-is by the implementation.
 * 
 * @author Oliver Richers
 */
public class IdentityLanguageTranslator implements ILanguageTranslator {

	private static IdentityLanguageTranslator singleton = new IdentityLanguageTranslator();

	/**
	 * Returns a reference to the singleton instance of this class.
	 */
	public static IdentityLanguageTranslator get() {

		return singleton;
	}

	/**
	 * Returns the given text without modification.
	 * <p>
	 * The language parameter is ignored by this method.
	 */
	@Override
	public String translate(LanguageEnum language, String text) {

		return text;
	}
}
