package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.dom.document.DomBody;
import java.util.Arrays;

/**
 * Enumerates the variants of the {@link PageTheme#MODERN} theme that a user
 * can pick.
 * <p>
 * A variant changes colours only. It is selected by the attribute
 * <code>data-fp-variant</code> on the body element (section VARIANTS of the
 * modern theme style sheet); the default {@link #LIGHT} has no attribute. The
 * identifier is what {@link UserPreferences#themeVariant} stores, so it must
 * never change once used.
 * <p>
 * Every variant comes with a menu ({@link #getMenu()}), which the user can
 * switch ({@link PageThemeMenu}); {@link #DARK_MENU} is {@link #LIGHT} with the
 * dark menu, and {@link #DARK} always keeps its dark menu.
 */
public enum PageThemeVariant {

	LIGHT("", CoreI18n.LIGHT, CoreI18n.DEFAULT_WHITE_AND_AIRY_AS_BEFORE, PageThemeMenu.LIGHT),
	INCREASED_CONTRAST(
			"contrast",
			CoreI18n.INCREASED_CONTRAST,
			CoreI18n.DARKER_TEXT_STRONGER_LINES_AND_FIELD_BORDERS_FOR_WEAKER_EYESIGHT_AND_BRIGHT_ROOMS,
			PageThemeMenu.LIGHT),
	CLASSIC("classic", CoreI18n.CLASSIC, CoreI18n.THE_BLUE_GREY_BANDS_AND_TABLE_GRID_OF_THE_OLD_THEME_IN_A_MODERN_STYLE, PageThemeMenu.LIGHT),
	STRUCTURED("structured", CoreI18n.STRUCTURED, CoreI18n.GREY_PAGE_WHITE_CARDS_AND_CLEARLY_SEPARATED_TABLE_ROWS, PageThemeMenu.LIGHT),
	DIMMED("dimmed", CoreI18n.DIMMED, CoreI18n.NO_PURE_WHITE_LESS_GLARE_FOR_A_FULL_WORKING_DAY_AT_THE_SCREEN, PageThemeMenu.LIGHT),
	DARK_MENU("dark-menu", CoreI18n.DARK_MENU, CoreI18n.A_DARK_MENU_WITH_LIGHT_CONTENT, PageThemeMenu.DARK),
	DARK("dark", CoreI18n.DARK, CoreI18n.DARK_SURFACES_AND_LIGHT_TEXT_FOR_WORKING_IN_DIM_LIGHT, PageThemeMenu.DARK),
	//
	;

	static final String ATTRIBUTE = "data-fp-variant";
	private final String identifier;
	private final IDisplayString title;
	private final IDisplayString description;
	private final PageThemeMenu menu;

	private PageThemeVariant(String identifier, IDisplayString title, IDisplayString description, PageThemeMenu menu) {

		this.identifier = identifier;
		this.title = title;
		this.description = description;
		this.menu = menu;
	}

	/**
	 * Returns the variant with the given identifier.
	 *
	 * @param identifier
	 *            the identifier (may be <i>null</i>)
	 * @return the matching {@link PageThemeVariant}; {@link #LIGHT} for
	 *         <i>null</i>, an empty or an unknown identifier
	 */
	public static PageThemeVariant fromIdentifier(String identifier) {

		return Arrays//
			.stream(values())
			.filter(variant -> variant.identifier.equals(identifier))
			.findFirst()
			.orElse(LIGHT);
	}

	/**
	 * Determines the variant of the current user.
	 *
	 * @return the {@link PageThemeVariant} of the current user; {@link #LIGHT}
	 *         if nobody is logged in
	 */
	public static PageThemeVariant getCurrent() {

		return PageTheme//
			.getLoggedInUser()
			.map(AGUser::getPreferences)
			.map(preferences -> fromIdentifier(preferences.themeVariant))
			.orElse(LIGHT);
	}

	/**
	 * Returns the value of the attribute <code>data-fp-variant</code> and of
	 * {@link UserPreferences#themeVariant}.
	 *
	 * @return the identifier; empty for {@link #LIGHT}
	 */
	public String getIdentifier() {

		return identifier;
	}

	public IDisplayString getTitle() {

		return title;
	}

	public IDisplayString getDescription() {

		return description;
	}

	/**
	 * Returns the menu this variant comes with, until the user switches it.
	 *
	 * @return the {@link PageThemeMenu} of this variant
	 */
	public PageThemeMenu getMenu() {

		return menu;
	}

	/**
	 * Determines whether the menu of this variant cannot be switched: the dark
	 * variant always has the dark menu.
	 *
	 * @return <i>true</i> for {@link #DARK}
	 */
	public boolean isMenuFixed() {

		return this == DARK;
	}

	/**
	 * Returns the menu shown with this variant.
	 *
	 * @param menuIdentifier
	 *            the menu the user switched to, as in
	 *            {@link UserPreferences#themeMenu}; empty for the menu of
	 *            the variant (may be <i>null</i>)
	 * @return the {@link PageThemeMenu} to show
	 */
	public PageThemeMenu getShownMenu(String menuIdentifier) {

		if (isMenuFixed()) {
			return menu;
		} else {
			return PageThemeMenu.fromIdentifier(menuIdentifier).orElse(menu);
		}
	}

	/**
	 * Sets or removes the attribute of this variant on the given
	 * {@link DomBody}.
	 *
	 * @param body
	 *            the {@link DomBody} of the page (never <i>null</i>)
	 */
	public void applyTo(DomBody body) {

		if (identifier.isEmpty()) {
			body.unsetAttribute(ATTRIBUTE);
		} else {
			body.setAttribute(ATTRIBUTE, identifier);
		}
	}
}
