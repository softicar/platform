package com.softicar.platform.common.core.i18n.key;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of {@link II18nKey}.
 *
 * @param <T>
 *            the type of the derived class
 * @author Oliver Richers
 */
public abstract class AbstractI18nKey<T extends AbstractI18nKey<T>> implements II18nKey {

	private final Map<LanguageEnum, String> texts;

	/**
	 * Constructs this object with the English text as key.
	 *
	 * @param english
	 *            the English text (never null)
	 */
	protected AbstractI18nKey(String english) {

		this.texts = new HashMap<>();
		put(LanguageEnum.ENGLISH, english);
	}

	@Override
	public String toEnglish() {

		return texts.get(LanguageEnum.ENGLISH);
	}

	@Override
	public Optional<String> toLanguage(LanguageEnum languageEnum) {

		return Optional.ofNullable(texts.get(languageEnum));
	}

	@Override
	public Collection<LanguageEnum> getLanguages() {

		return Collections.unmodifiableCollection(texts.keySet());
	}

	/**
	 * Returns the same as {@link #toEnglish()}.
	 *
	 * @return {@link #toEnglish()} (never null)
	 */
	@Override
	public String toString() {

		return toEnglish();
	}

	/**
	 * Adds the Bosnian translation of the English text.
	 *
	 * @param bosnian
	 *            the Bosnian translation (never <i>null</i>)
	 * @return this
	 */
	public T bs(String bosnian) {

		return put(LanguageEnum.BOSNIAN, bosnian);
	}

	/**
	 * Adds the German translation of the English text.
	 *
	 * @param german
	 *            the German translation (never <i>null</i>)
	 * @return this
	 */
	public T de(String german) {

		return put(LanguageEnum.GERMAN, german);
	}

	/**
	 * Adds the Croatian translation of the English text.
	 *
	 * @param croatian
	 *            the Croatian translation (never <i>null</i>)
	 * @return this
	 */
	public T hr(String croatian) {

		return put(LanguageEnum.CROATIAN, croatian);
	}

	/**
	 * Adds the Serbian translation of the English text.
	 *
	 * @param serbian
	 *            the Serbian translation (never <i>null</i>)
	 * @return this
	 */
	public T sr(String serbian) {

		return put(LanguageEnum.SERBIAN, serbian);
	}

	/**
	 * Returns this object.
	 *
	 * @return this object (never null)
	 */
	protected abstract T getThis();

	private T put(LanguageEnum languageEnum, String text) {

		Objects.requireNonNull(languageEnum);
		Objects.requireNonNull(text);

		texts.put(languageEnum, text);
		return getThis();
	}
}
