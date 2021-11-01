package com.softicar.platform.common.core.i18n.key;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

	protected final String english;
	protected String german;

	/**
	 * Constructs this object with the English text as key.
	 *
	 * @param english
	 *            the English text (never null)
	 */
	protected AbstractI18nKey(String english) {

		this.english = Objects.requireNonNull(english);
		this.german = null;
	}

	@Override
	public String toEnglish() {

		return english;
	}

	@Override
	public Optional<String> toLanguage(LanguageEnum languageEnum) {

		switch (languageEnum) {
		case ENGLISH:
			return Optional.ofNullable(english);
		case GERMAN:
			return Optional.ofNullable(german);
		default:
			return Optional.empty();
		}
	}

	@Override
	public Collection<LanguageEnum> getLanguages() {

		return german != null? Arrays.asList(LanguageEnum.ENGLISH, LanguageEnum.GERMAN) : Collections.singleton(LanguageEnum.ENGLISH);
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
	 * Adds the German translation of the English text.
	 *
	 * @param german
	 *            the German translation (never null)
	 * @return this object
	 */
	public T de(String german) {

		this.german = Objects.requireNonNull(german);
		return getThis();
	}

	/**
	 * Returns this object.
	 *
	 * @return this object (never null)
	 */
	protected abstract T getThis();
}
