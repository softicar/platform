package com.softicar.platform.core.module.page.variant;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.PageThemeMenu;
import com.softicar.platform.core.module.page.PageThemeVariant;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomSpaceKeyEventHandler;
import java.util.EnumMap;
import java.util.Map;

/**
 * Picks the {@link PageThemeVariant} of the modern theme for the current user:
 * one tile per variant with a small picture, its name and its description. A
 * click (or Enter or Space) saves the variant in the {@link UserPreferences}
 * and shows the page in it at once. The compact form ({@link #createCompact()})
 * for the user menu of the page header shows the pictures only, under the name
 * of the chosen variant; the other names are in the tooltips.
 * <p>
 * Below the variants, the check box "Dark menu" switches the
 * {@link PageThemeMenu}: picking a variant sets the menu of the variant, the
 * check box keeps the other one until the next variant. In the dark variant
 * the menu is always dark.
 */
public class PageThemeVariantChooser extends DomDiv {

	private final boolean compact;
	private final Map<PageThemeVariant, Tile> tiles;
	private final DomDiv currentNameDiv;
	private final MenuCheckbox menuCheckbox;

	/**
	 * Creates the full form, with names and descriptions.
	 */
	public PageThemeVariantChooser() {

		this(false);
	}

	/**
	 * Creates the compact form for the user menu of the page header.
	 *
	 * @return the compact {@link PageThemeVariantChooser}
	 */
	public static PageThemeVariantChooser createCompact() {

		return new PageThemeVariantChooser(true);
	}

	private PageThemeVariantChooser(boolean compact) {

		this.compact = compact;
		this.tiles = new EnumMap<>(PageThemeVariant.class);
		this.currentNameDiv = new DomDiv();

		addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_CHOOSER);
		addMarker(CoreTestMarker.PAGE_THEME_VARIANT_CHOOSER);
		setAttribute("role", "radiogroup");
		setAttribute("aria-label", CoreI18n.APPEARANCE.toString());
		if (compact) {
			addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_COMPACT);
			DomDiv headDiv = appendChild(new DomDiv());
			headDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_COMPACT_HEAD);
			headDiv.appendText(CoreI18n.APPEARANCE);
			currentNameDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_CURRENT);
			headDiv.appendChild(currentNameDiv);
		}
		DomDiv tilesDiv = appendChild(new DomDiv());
		tilesDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_TILES);
		for (PageThemeVariant variant: PageThemeVariant.values()) {
			tiles.put(variant, tilesDiv.appendChild(new Tile(variant)));
		}
		DomDiv menuDiv = appendChild(new DomDiv());
		menuDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_MENU);
		this.menuCheckbox = menuDiv.appendChild(new MenuCheckbox());
		if (!compact) {
			DomDiv hintDiv = appendChild(new DomDiv());
			hintDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_HINT);
			hintDiv.appendText(CoreI18n.APPLIES_AT_ONCE_AND_ON_EVERY_DEVICE_YOU_LOG_IN_WITH);
		}
		refresh();
	}

	/**
	 * Returns the variant of the current user.
	 *
	 * @return the selected {@link PageThemeVariant}
	 */
	public PageThemeVariant getSelectedVariant() {

		return PageThemeVariant.getCurrent();
	}

	/**
	 * Returns the menu shown to the current user.
	 *
	 * @return the selected {@link PageThemeMenu}
	 */
	public PageThemeMenu getSelectedMenu() {

		return PageThemeMenu.getCurrent();
	}

	private void select(PageThemeVariant variant) {

		savePreferences(variant.getIdentifier(), "");
	}

	private void selectMenu(PageThemeMenu menu) {

		savePreferences(getSelectedVariant().getIdentifier(), menu.getIdentifier());
	}

	private void savePreferences(String variantIdentifier, String menuIdentifier) {

		AGUser user = CurrentUser.get();
		UserPreferences preferences = user.getPreferences();
		preferences.themeVariant = variantIdentifier;
		preferences.themeMenu = menuIdentifier;
		user.savePreferences(preferences);
		var body = getDomDocument().getBody();
		getSelectedVariant().applyTo(body);
		getSelectedMenu().applyTo(body);
		refresh();
		refreshOtherChoosers(body);
	}

	/**
	 * Refreshes the other {@link PageThemeVariantChooser} elements below the
	 * given node, e.g. the one of the profile when the variant was picked in
	 * the user menu.
	 */
	private void refreshOtherChoosers(IDomNode node) {

		if (node instanceof PageThemeVariantChooser chooser) {
			if (chooser != this) {
				chooser.refresh();
			}
		} else if (node instanceof IDomParentElement parent) {
			parent.getChildren().forEach(this::refreshOtherChoosers);
		}
	}

	/**
	 * Shows the variant and the menu of the current user again, e.g. when the
	 * user menu that contains this chooser is opened: the variant may have
	 * been changed elsewhere since.
	 */
	public void refresh() {

		PageThemeVariant selected = getSelectedVariant();
		tiles.forEach((variant, tile) -> tile.setSelected(variant == selected));
		if (compact) {
			currentNameDiv.removeChildren();
			currentNameDiv.appendText(selected.getTitle());
		}
		menuCheckbox.setValue(getSelectedMenu() == PageThemeMenu.DARK);
		menuCheckbox.setEnabled(!selected.isMenuFixed());
	}

	/**
	 * The check box "Dark menu".
	 */
	public class MenuCheckbox extends DomCheckbox {

		public MenuCheckbox() {

			super(false);
			setLabel(CoreI18n.DARK_MENU);
			addMarker(CoreTestMarker.PAGE_THEME_VARIANT_MENU_CHECKBOX);
			addChangeCallback(() -> selectMenu(isChecked()? PageThemeMenu.DARK : PageThemeMenu.LIGHT));
		}
	}

	/**
	 * The tile of one {@link PageThemeVariant}.
	 */
	public class Tile extends DomDiv implements IDomClickEventHandler, IDomEnterKeyEventHandler, IDomSpaceKeyEventHandler {

		private final PageThemeVariant variant;

		public Tile(PageThemeVariant variant) {

			this.variant = variant;

			addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_TILE);
			addMarker(CoreTestMarker.PAGE_THEME_VARIANT_TILE);
			// the picture of the variant is drawn by the style sheet from this attribute
			setAttribute("data-variant", variant.getIdentifier().isEmpty()? "light" : variant.getIdentifier());
			setAttribute("role", "radio");
			setAttribute("tabindex", "0");
			appendChild(new DomDiv()).addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_PREVIEW);
			if (compact) {
				setTitle(variant.getTitle());
				setAttribute("aria-label", variant.getTitle().toString());
			} else {
				DomDiv textDiv = appendChild(new DomDiv());
				textDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_TEXT);
				DomDiv nameDiv = textDiv.appendChild(new DomDiv());
				nameDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_NAME);
				nameDiv.appendText(variant.getTitle());
				DomDiv descriptionDiv = textDiv.appendChild(new DomDiv());
				descriptionDiv.addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_DESCRIPTION);
				descriptionDiv.appendText(variant.getDescription());
			}
		}

		public PageThemeVariant getVariant() {

			return variant;
		}

		@Override
		public void handleClick(IDomEvent event) {

			select(variant);
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			select(variant);
		}

		@Override
		public void handleSpaceKey(IDomEvent event) {

			select(variant);
		}

		void setSelected(boolean selected) {

			setAttribute("aria-checked", Boolean.toString(selected));
			if (selected) {
				addCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_SELECTED);
			} else {
				removeCssClass(PageThemeVariantCssClasses.PAGE_THEME_VARIANT_SELECTED);
			}
		}
	}
}
