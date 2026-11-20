package com.softicar.platform.core.module.user.preferences;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.page.PageTheme;
import com.softicar.platform.dom.DomI18n;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Enumerates the page themes that can be selected as a user preference.
 * <p>
 * <b>WARNING:</b> Used in {@link UserPreferences}. Renaming constants in this
 * enum will partially invalidate stored user preferences.
 */
public enum UserPreferencesTheme implements IDisplayable {

	/** The theme configured for the installation, see {@link PageTheme}. */
	STANDARD(DomI18n.STANDARD, null),
	CLASSIC(CoreI18n.CLASSIC, PageTheme.CLASSIC),
	MODERN(CoreI18n.MODERN, PageTheme.MODERN),
	//
	;

	private final IDisplayString displayName;
	private final PageTheme pageTheme;

	private UserPreferencesTheme(IDisplayString displayName, PageTheme pageTheme) {

		this.displayName = Objects.requireNonNull(displayName);
		this.pageTheme = pageTheme;
	}

	/**
	 * Returns the name of this preference; for {@link #STANDARD} with the
	 * theme of the installation in parentheses, e.g. "Standard (Modern)".
	 */
	@Override
	public IDisplayString toDisplay() {

		if (pageTheme == null) {
			PageTheme configured = PageTheme.getConfigured();
			IDisplayString configuredName = Arrays//
				.stream(values())
				.filter(theme -> theme.pageTheme == configured)
				.map(theme -> theme.displayName)
				.findFirst()
				.orElse(IDisplayString.EMPTY);
			return displayName.concat(" ").concat(configuredName.encloseInParentheses());
		} else {
			return displayName;
		}
	}

	/**
	 * Returns the {@link PageTheme} that this preference selects.
	 *
	 * @return the selected {@link PageTheme}; empty for {@link #STANDARD}
	 */
	public Optional<PageTheme> getPageTheme() {

		return Optional.ofNullable(pageTheme);
	}
}
