package com.softicar.platform.common.core.locale;

/**
 * An {@link AutoCloseable} that controls {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class LocaleScope implements AutoCloseable {

	private final ILocale originalLocale;

	/**
	 * Sets {@link CurrentLocale} to the given {@link ILocale}.
	 *
	 * @param locale
	 *            the {@link ILocale} to set (never <i>null</i>)
	 */
	public LocaleScope(ILocale locale) {

		this.originalLocale = CurrentLocale.get();
		CurrentLocale.set(locale);
	}

	/**
	 * Reverts {@link CurrentLocale} to the original value.
	 */
	@Override
	public void close() {

		CurrentLocale.set(originalLocale);
	}
}
