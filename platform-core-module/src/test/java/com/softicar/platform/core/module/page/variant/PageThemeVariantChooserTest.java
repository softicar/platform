package com.softicar.platform.core.module.page.variant;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.PageThemeMenu;
import com.softicar.platform.core.module.page.PageThemeVariant;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import org.junit.Test;

public class PageThemeVariantChooserTest extends AbstractCoreTest {

	public PageThemeVariantChooserTest() {

		setNodeSupplier(PageThemeVariantChooser::new);
	}

	@Test
	public void testShowsEveryVariantAndSelectsTheSavedOne() {

		CurrentUser.get().updatePreferences(preferences -> preferences.themeVariant = "dimmed");

		assertEquals(PageThemeVariant.values().length, findNodes(CoreTestMarker.PAGE_THEME_VARIANT_TILE).size());
		assertSelected(PageThemeVariant.DIMMED);
	}

	@Test
	public void testClickSavesAndShowsTheVariant() {

		clickTile(PageThemeVariant.DARK);
		assertEquals("dark", getPreferences().themeVariant);
		assertEquals("dark", getBodyAttribute("data-fp-variant"));
		assertEquals("dark", getBodyAttribute("data-fp-menu"));
		assertSelected(PageThemeVariant.DARK);

		clickTile(PageThemeVariant.LIGHT);
		assertEquals("", getPreferences().themeVariant);
		assertNull(getBodyAttribute("data-fp-variant"));
		assertNull(getBodyAttribute("data-fp-menu"));
		assertSelected(PageThemeVariant.LIGHT);
	}

	@Test
	public void testMenuCheckboxKeepsTheMenuUntilTheNextVariant() {

		clickTile(PageThemeVariant.CLASSIC);
		assertFalse(getMenuCheckbox().isChecked());

		findMenuCheckbox().click();
		assertEquals("dark", getPreferences().themeMenu);
		assertEquals("classic", getPreferences().themeVariant);
		assertSame(PageThemeMenu.DARK, PageThemeMenu.getCurrent());
		assertEquals("dark", getBodyAttribute("data-fp-menu"));

		// another variant comes with its own menu again
		clickTile(PageThemeVariant.STRUCTURED);
		assertEquals("", getPreferences().themeMenu);
		assertFalse(getMenuCheckbox().isChecked());
		assertNull(getBodyAttribute("data-fp-menu"));
	}

	@Test
	public void testMenuCheckboxSwitchesTheDarkMenuOff() {

		clickTile(PageThemeVariant.DARK_MENU);
		assertTrue(getMenuCheckbox().isChecked());

		findMenuCheckbox().click();
		assertEquals("light", getPreferences().themeMenu);
		assertSame(PageThemeMenu.LIGHT, PageThemeMenu.getCurrent());
		assertNull(getBodyAttribute("data-fp-menu"));
	}

	@Test
	public void testMenuOfTheDarkVariantCannotBeSwitched() {

		clickTile(PageThemeVariant.DARK);
		assertTrue(getMenuCheckbox().isChecked());
		assertTrue(getMenuCheckbox().isDisabled());

		clickTile(PageThemeVariant.LIGHT);
		assertFalse(getMenuCheckbox().isDisabled());
	}

	@Test
	public void testCompactShowsTheNameOfTheChosenVariant() {

		setNodeSupplier(PageThemeVariantChooser::createCompact);
		CurrentUser.get().updatePreferences(preferences -> preferences.themeVariant = "classic");

		findBody().assertContainsText(CoreI18n.APPEARANCE);
		findBody().assertContainsText(CoreI18n.CLASSIC);
		findBody().assertDoesNotContainText(CoreI18n.DIMMED);

		clickTile(PageThemeVariant.DIMMED);
		findBody().assertContainsText(CoreI18n.DIMMED);
		findBody().assertDoesNotContainText(CoreI18n.CLASSIC);
	}

	@Test
	public void testRefreshShowsAVariantPickedElsewhere() {

		setNodeSupplier(PageThemeVariantChooser::createCompact);
		assertSelected(PageThemeVariant.LIGHT);

		// e.g. picked in the profile while this chooser waited in the closed user menu
		CurrentUser.get().updatePreferences(preferences -> preferences.themeVariant = "dark");
		findNodes(CoreTestMarker.PAGE_THEME_VARIANT_CHOOSER).assertOne().assertType(PageThemeVariantChooser.class).refresh();

		assertSelected(PageThemeVariant.DARK);
		findBody().assertContainsText(CoreI18n.DARK);
		assertTrue(getMenuCheckbox().isChecked());
	}

	@Test
	public void testPickRefreshesTheOtherChoosersOfThePage() {

		setNodeSupplier(() -> {
			var div = new com.softicar.platform.dom.elements.DomDiv();
			div.appendChild(new PageThemeVariantChooser());
			div.appendChild(PageThemeVariantChooser.createCompact());
			return div;
		});
		var choosers = findNodes(CoreTestMarker.PAGE_THEME_VARIANT_CHOOSER).toList();
		assertEquals(2, choosers.size());

		// a click in the first chooser
		findNodes(CoreTestMarker.PAGE_THEME_VARIANT_TILE)//
			.toList()
			.stream()
			.filter(tile -> tile.assertType(PageThemeVariantChooser.Tile.class).getVariant() == PageThemeVariant.STRUCTURED)
			.findFirst()
			.orElseThrow()
			.click();

		// both show it
		long selected = findNodes(CoreTestMarker.PAGE_THEME_VARIANT_TILE)//
			.toList()
			.stream()
			.filter(tile -> tile.assertType(PageThemeVariantChooser.Tile.class).getVariant() == PageThemeVariant.STRUCTURED)
			.filter(tile -> "true".equals(tile.getNode().getAttributeValue("aria-checked").orElse(null)))
			.count();
		assertEquals(2, selected);
	}

	private void clickTile(PageThemeVariant variant) {

		findNodes(CoreTestMarker.PAGE_THEME_VARIANT_TILE)//
			.toList()
			.stream()
			.filter(tile -> tile.assertType(PageThemeVariantChooser.Tile.class).getVariant() == variant)
			.findFirst()
			.orElseThrow()
			.click();
	}

	private void assertSelected(PageThemeVariant variant) {

		for (DomNodeTester tile: findNodes(CoreTestMarker.PAGE_THEME_VARIANT_TILE).toList()) {
			boolean selected = tile.assertType(PageThemeVariantChooser.Tile.class).getVariant() == variant;
			assertEquals(Boolean.toString(selected), tile.getNode().getAttributeValue("aria-checked").orElse(null));
		}
	}

	private DomNodeTester findMenuCheckbox() {

		return findNodes(CoreTestMarker.PAGE_THEME_VARIANT_MENU_CHECKBOX).assertOne();
	}

	private DomCheckbox getMenuCheckbox() {

		return findMenuCheckbox().assertType(DomCheckbox.class);
	}

	private UserPreferences getPreferences() {

		return CurrentUser.get().getPreferences();
	}

	private String getBodyAttribute(String name) {

		return CurrentDomDocument.get().getBody().getAttributeValue(name).orElse(null);
	}
}
