package com.softicar.platform.common.core.i18n.key;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import java.util.Collection;
import java.util.Optional;

/**
 * Common interface of all I18N translation keys.
 * <p>
 * An {@link II18nKey} represents a {@link String} in various languages. The
 * English translation is always available while translations into other
 * languages are optional.
 *
 * @author Oliver Richers
 */
public interface II18nKey {

	/**
	 * Returns the English {@link String} of this {@link II18nKey}.
	 *
	 * @return the English {@link String} (never null)
	 */
	String toEnglish();

	/**
	 * Returns the {@link String} of this {@link II18nKey} for the given
	 * language.
	 *
	 * @param languageEnum
	 *            the target language (never null)
	 * @return the optional {@link String} in the given language
	 */
	Optional<String> toLanguage(LanguageEnum languageEnum);

	/**
	 * Returns the languages for which translations exist.
	 *
	 * @return a {@link Collection} of all supported languages (never null)
	 */
	Collection<LanguageEnum> getLanguages();
}
