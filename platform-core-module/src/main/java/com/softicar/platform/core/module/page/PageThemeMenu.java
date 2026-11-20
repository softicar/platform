package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.dom.document.DomBody;
import java.util.Arrays;
import java.util.Optional;

/**
 * Enumerates the colours of the menu (the navigation) of the
 * {@link PageTheme#MODERN} theme.
 * <p>
 * Every {@link PageThemeVariant} comes with a menu
 * ({@link PageThemeVariant#getMenu()}). A user can switch to the other one;
 * {@link UserPreferences#themeMenu} keeps that choice until the user picks
 * another variant. {@link PageThemeVariant#DARK} always has the dark menu. The
 * dark menu is selected by the attribute <code>data-fp-menu</code> on the body
 * element; the light menu has no attribute. The identifier is what
 * {@link UserPreferences#themeMenu} stores (empty for the menu of the
 * variant), so it must never change once used.
 */
public enum PageThemeMenu {

	LIGHT("light"),
	DARK("dark"),
	//
	;

	static final String ATTRIBUTE = "data-fp-menu";
	private final String identifier;

	private PageThemeMenu(String identifier) {

		this.identifier = identifier;
	}

	/**
	 * Returns the menu with the given identifier.
	 *
	 * @param identifier
	 *            the identifier (may be <i>null</i>)
	 * @return the matching {@link PageThemeMenu}; empty for <i>null</i>, an
	 *         empty (the menu of the variant) or an unknown identifier
	 */
	public static Optional<PageThemeMenu> fromIdentifier(String identifier) {

		return Arrays//
			.stream(values())
			.filter(menu -> menu.identifier.equals(identifier))
			.findFirst();
	}

	/**
	 * Determines the menu shown to the current user.
	 *
	 * @return the {@link PageThemeMenu} of the current user; {@link #LIGHT}
	 *         if nobody is logged in
	 */
	public static PageThemeMenu getCurrent() {

		return PageTheme//
			.getLoggedInUser()
			.map(AGUser::getPreferences)
			.map(preferences -> PageThemeVariant.fromIdentifier(preferences.themeVariant).getShownMenu(preferences.themeMenu))
			.orElse(LIGHT);
	}

	/**
	 * Returns the value of {@link UserPreferences#themeMenu} for this menu.
	 *
	 * @return the identifier (never <i>null</i>)
	 */
	public String getIdentifier() {

		return identifier;
	}

	/**
	 * Sets or removes the attribute of this menu on the given {@link DomBody}.
	 *
	 * @param body
	 *            the {@link DomBody} of the page (never <i>null</i>)
	 */
	public void applyTo(DomBody body) {

		if (this == DARK) {
			body.setAttribute(ATTRIBUTE, identifier);
		} else {
			body.unsetAttribute(ATTRIBUTE);
		}
	}
}
