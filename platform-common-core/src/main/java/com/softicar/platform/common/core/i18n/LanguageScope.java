package com.softicar.platform.common.core.i18n;

/**
 * An {@link AutoCloseable} that controls {@link CurrentLanguage}.
 *
 * @author Oliver Richers
 */
public class LanguageScope implements AutoCloseable {

	private final LanguageEnum originalLanguage;

	/**
	 * Sets {@link CurrentLanguage} to the given {@link LanguageEnum}.
	 *
	 * @param language
	 *            the language to set (never null)
	 */
	public LanguageScope(LanguageEnum language) {

		this.originalLanguage = CurrentLanguage.get();
		CurrentLanguage.set(language);
	}

	/**
	 * Reverts {@link CurrentLanguage} to the original value.
	 */
	@Override
	public void close() {

		CurrentLanguage.set(originalLanguage);
	}
}
