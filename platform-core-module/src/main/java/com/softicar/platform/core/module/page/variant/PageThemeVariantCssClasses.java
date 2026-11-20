package com.softicar.platform.core.module.page.variant;

import com.softicar.platform.core.module.CoreCssFiles;
import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

/**
 * The CSS classes of {@link PageThemeVariantChooser}; their styles are part of
 * {@link CoreCssFiles#MODERN_THEME_STYLE}.
 */
public interface PageThemeVariantCssClasses {

	ICssClass PAGE_THEME_VARIANT_CHOOSER = new CssClass("PageThemeVariantChooser", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_COMPACT = new CssClass("PageThemeVariantCompact", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_COMPACT_HEAD = new CssClass("PageThemeVariantCompactHead", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_CURRENT = new CssClass("PageThemeVariantCurrent", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_DESCRIPTION = new CssClass("PageThemeVariantDescription", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_HINT = new CssClass("PageThemeVariantHint", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_MENU = new CssClass("PageThemeVariantMenu", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_NAME = new CssClass("PageThemeVariantName", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_PREVIEW = new CssClass("PageThemeVariantPreview", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_SELECTED = new CssClass("PageThemeVariantSelected", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_TEXT = new CssClass("PageThemeVariantText", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_TILE = new CssClass("PageThemeVariantTile", CoreCssFiles.MODERN_THEME_STYLE);
	ICssClass PAGE_THEME_VARIANT_TILES = new CssClass("PageThemeVariantTiles", CoreCssFiles.MODERN_THEME_STYLE);
}
