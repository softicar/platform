package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.II18nKey;

/**
 * Maps an {@link II18nKey} into an {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
class I18nKeyDisplayString extends AbstractDisplayString {

	private final II18nKey key;
	private final Object[] arguments;

	public I18nKeyDisplayString(II18nKey key, Object...arguments) {

		this.key = key;
		this.arguments = arguments;
	}

	@Override
	public String toString() {

		LanguageEnum languageEnum = CurrentLanguage.get();
		return key//
			.toLanguage(languageEnum)
			.map(this::formatString)
			.orElseGet(this::getFallbackTranslation);
	}

	private String formatString(String string) {

		return String.format(string, arguments);
	}

	private String getFallbackTranslation() {

		return InternalI18nTranslator.translate(key.toEnglish(), arguments);
	}
}
