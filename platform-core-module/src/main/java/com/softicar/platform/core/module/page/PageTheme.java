package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreProperties;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferencesTheme;
import com.softicar.platform.dom.document.DomBody;
import java.util.Optional;

/**
 * Enumerates the looks of the page.
 * <p>
 * The theme of the installation is configured by
 * {@link CoreProperties#PAGE_THEME}; every user can override it with the user
 * preference {@link UserPreferencesTheme}.
 * <p>
 * The {@link #MODERN} theme is a style sheet on top of the classic styles. It
 * is activated by a CSS class on the body element, so every one of its rules
 * is scoped to that class.
 */
public enum PageTheme {

	CLASSIC("classic"),
	MODERN("modern"),
	//
	;

	private final String identifier;

	private PageTheme(String identifier) {

		this.identifier = identifier;
	}

	/**
	 * Returns the identifier of this theme, as used in
	 * {@link CoreProperties#PAGE_THEME}.
	 *
	 * @return the identifier (never <i>null</i>)
	 */
	public String getIdentifier() {

		return identifier;
	}

	/**
	 * Determines the theme for the current user.
	 * <p>
	 * Without a current user (e.g. on the login page), or if the user did not
	 * select a theme, the theme of the installation is returned.
	 *
	 * @return the current {@link PageTheme} (never <i>null</i>)
	 */
	public static PageTheme getCurrent() {

		return getLoggedInUser()//
			.map(AGUser::getPreferences)
			.map(preferences -> preferences.theme)
			.flatMap(UserPreferencesTheme::getPageTheme)
			.orElseGet(PageTheme::getConfigured);
	}

	/**
	 * Returns the user who is logged in.
	 * <p>
	 * Unlike {@link CurrentUser#get()}, this does not fall back to the system
	 * user, so that the pages before the login use the settings of the
	 * installation.
	 *
	 * @return the logged-in {@link AGUser}; empty if nobody is logged in
	 */
	static Optional<AGUser> getLoggedInUser() {

		return Optional//
			.ofNullable(CurrentBasicUser.get())
			.map(user -> AGUser.get(user.getId()));
	}

	/**
	 * Returns the theme of the installation.
	 *
	 * @return the configured {@link PageTheme}; {@link #CLASSIC} if the
	 *         configured value is unknown
	 */
	public static PageTheme getConfigured() {

		return parse(CoreProperties.PAGE_THEME.getValue());
	}

	/**
	 * Returns the theme with the given identifier.
	 *
	 * @param identifier
	 *            the identifier, ignoring case and surrounding white space
	 *            (may be <i>null</i>)
	 * @return the matching {@link PageTheme}; {@link #CLASSIC} if the
	 *         identifier is <i>null</i> or unknown
	 */
	public static PageTheme parse(String identifier) {

		String trimmed = identifier == null? "" : identifier.trim();
		for (PageTheme theme: values()) {
			if (theme.identifier.equalsIgnoreCase(trimmed)) {
				return theme;
			}
		}
		return CLASSIC;
	}

	/**
	 * Applies this theme to the given {@link DomBody}, with the
	 * {@link PageThemeVariant} and the {@link PageThemeMenu} of the current user
	 * for the {@link #MODERN} theme.
	 *
	 * @param body
	 *            the {@link DomBody} of the page (never <i>null</i>)
	 */
	public void applyTo(DomBody body) {

		if (this == MODERN) {
			body.addCssClass(CoreCssClasses.MODERN_THEME);
			PageThemeVariant.getCurrent().applyTo(body);
			PageThemeMenu.getCurrent().applyTo(body);
		} else {
			body.removeCssClass(CoreCssClasses.MODERN_THEME);
			PageThemeVariant.LIGHT.applyTo(body);
			PageThemeMenu.LIGHT.applyTo(body);
		}
	}
}
