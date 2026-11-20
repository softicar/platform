package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferencesTheme;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import org.junit.Test;

public class PageThemeVariantTest extends AbstractCoreTest {

	@Test
	public void testFromIdentifier() {

		assertSame(PageThemeVariant.DARK, PageThemeVariant.fromIdentifier("dark"));
		assertSame(PageThemeVariant.DARK_MENU, PageThemeVariant.fromIdentifier("dark-menu"));
		assertSame(PageThemeVariant.LIGHT, PageThemeVariant.fromIdentifier(""));
		assertSame(PageThemeVariant.LIGHT, PageThemeVariant.fromIdentifier("no-such-variant"));
		assertSame(PageThemeVariant.LIGHT, PageThemeVariant.fromIdentifier(null));
	}

	@Test
	public void testGetShownMenu() {

		assertSame(PageThemeMenu.LIGHT, PageThemeVariant.CLASSIC.getShownMenu(""));
		assertSame(PageThemeMenu.DARK, PageThemeVariant.CLASSIC.getShownMenu("dark"));
		assertSame(PageThemeMenu.DARK, PageThemeVariant.DARK_MENU.getShownMenu(null));
		assertSame(PageThemeMenu.LIGHT, PageThemeVariant.DARK_MENU.getShownMenu("light"));
		// the dark variant always keeps its dark menu
		assertSame(PageThemeMenu.DARK, PageThemeVariant.DARK.getShownMenu("light"));
	}

	@Test
	public void testGetCurrent() {

		assertSame(PageThemeVariant.LIGHT, PageThemeVariant.getCurrent());
		assertSame(PageThemeMenu.LIGHT, PageThemeMenu.getCurrent());

		CurrentUser.get().updatePreferences(preferences -> preferences.themeVariant = "structured");
		assertSame(PageThemeVariant.STRUCTURED, PageThemeVariant.getCurrent());
		assertSame(PageThemeMenu.LIGHT, PageThemeMenu.getCurrent());

		CurrentUser.get().updatePreferences(preferences -> preferences.themeMenu = "dark");
		assertSame(PageThemeMenu.DARK, PageThemeMenu.getCurrent());
	}

	@Test
	public void testApplyToWithModernTheme() {

		CurrentUser.get().updatePreferences(preferences -> {
			preferences.theme = UserPreferencesTheme.MODERN;
			preferences.themeVariant = "dark";
		});
		DomBody body = CurrentDomDocument.get().getBody();

		PageTheme.MODERN.applyTo(body);
		assertEquals("dark", body.getAttributeValue("data-fp-variant").orElse(null));
		assertEquals("dark", body.getAttributeValue("data-fp-menu").orElse(null));

		// the classic theme has no variants
		PageTheme.CLASSIC.applyTo(body);
		assertTrue(body.getAttributeValue("data-fp-variant").isEmpty());
		assertTrue(body.getAttributeValue("data-fp-menu").isEmpty());
	}

	@Test
	public void testApplyToWithLightVariant() {

		DomBody body = CurrentDomDocument.get().getBody();
		PageThemeVariant.DIMMED.applyTo(body);
		assertEquals("dimmed", body.getAttributeValue("data-fp-variant").orElse(null));

		PageThemeVariant.LIGHT.applyTo(body);
		assertTrue(body.getAttributeValue("data-fp-variant").isEmpty());
	}
}
