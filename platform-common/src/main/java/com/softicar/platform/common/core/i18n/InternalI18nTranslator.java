package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.locale.CurrentLocale;
import java.util.IllegalFormatException;

class InternalI18nTranslator {

	public static String translate(String text, Object...args) {

		return translate(CurrentLocale.get().getLanguage(), text, args);
	}

	public static String translate(LanguageEnum language, String text, Object...args) {

		if (isNullOrEmpty(text)) {
			return text;
		}

		String translation = getTranslation(language, text);

		return getExpanded(translation, args);
	}

	private static boolean isNullOrEmpty(String text) {

		return text == null || text.trim().isEmpty();
	}

	private static String getTranslation(LanguageEnum language, String text) {

		if (language != null) {
			return CurrentLanguageTranslator.get().translate(language, text);
		} else {
			return text;
		}
	}

	private static String getExpanded(String translation, Object...args) {

		try {
			return String.format(translation, args);
		} catch (IllegalFormatException exception) {
			if (args.length == 0) {
				return translation;
			} else {
				throw exception;
			}
		}
	}
}
