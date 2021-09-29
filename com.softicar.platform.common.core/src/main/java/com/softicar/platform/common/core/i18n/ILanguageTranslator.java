package com.softicar.platform.common.core.i18n;

/**
 * Common interface of translation engines.
 *
 * @author Oliver Richers
 */
public interface ILanguageTranslator {

	/**
	 * Translates the specified text.
	 *
	 * @param language
	 *            the destination language
	 * @param text
	 *            the text to translate, never null
	 * @return the text translated into the destination language, never null
	 */
	String translate(LanguageEnum language, String text);
}
