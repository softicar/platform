package com.softicar.platform.core.module.page;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferencesTheme;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import org.junit.Test;

public class PageThemeTest extends AbstractCoreTest {

	@Test
	public void testParse() {

		assertSame(PageTheme.MODERN, PageTheme.parse("modern"));
		assertSame(PageTheme.MODERN, PageTheme.parse(" Modern "));
		assertSame(PageTheme.CLASSIC, PageTheme.parse("classic"));
		assertSame(PageTheme.CLASSIC, PageTheme.parse("unknown"));
		assertSame(PageTheme.CLASSIC, PageTheme.parse(""));
		assertSame(PageTheme.CLASSIC, PageTheme.parse(null));
	}

	@Test
	public void testGetCurrentWithUserPreference() {

		CurrentUser.get().updatePreferences(preferences -> preferences.theme = UserPreferencesTheme.MODERN);
		assertSame(PageTheme.MODERN, PageTheme.getCurrent());

		CurrentUser.get().updatePreferences(preferences -> preferences.theme = UserPreferencesTheme.CLASSIC);
		assertSame(PageTheme.CLASSIC, PageTheme.getCurrent());
	}

	@Test
	public void testGetCurrentWithStandardPreference() {

		CurrentUser.get().updatePreferences(preferences -> preferences.theme = UserPreferencesTheme.STANDARD);
		assertSame(PageTheme.getConfigured(), PageTheme.getCurrent());
	}

	@Test
	public void testGetCurrentWithoutLoggedInUserIgnoresTheSystemUser() {

		// without a logged-in user, CurrentUser.get() returns the system user; the login page must not use its settings
		AGUser systemUser = CurrentUser.get();
		AGCoreModuleInstance.getInstance().setSystemUser(systemUser).save();
		PageTheme other = PageTheme.getConfigured() == PageTheme.MODERN? PageTheme.CLASSIC : PageTheme.MODERN;
		systemUser.updatePreferences(preferences -> {
			preferences.theme = other == PageTheme.MODERN? UserPreferencesTheme.MODERN : UserPreferencesTheme.CLASSIC;
			preferences.themeVariant = "dark";
			preferences.themeMenu = "dark";
		});
		assertSame(other, PageTheme.getCurrent());

		CurrentUser.reset();

		assertSame(PageTheme.getConfigured(), PageTheme.getCurrent());
		assertSame(PageThemeVariant.LIGHT, PageThemeVariant.getCurrent());
		assertSame(PageThemeMenu.LIGHT, PageThemeMenu.getCurrent());
	}

	@Test
	public void testApplyTo() {

		DomBody body = CurrentDomDocument.get().getBody();

		PageTheme.MODERN.applyTo(body);
		assertTrue(getClasses(body).contains("ModernTheme"));

		PageTheme.CLASSIC.applyTo(body);
		assertFalse(getClasses(body).contains("ModernTheme"));
	}

	@Test
	public void testStandardPreferenceNamesTheConfiguredTheme() {

		String name = UserPreferencesTheme.STANDARD.toDisplay().toString();
		String configuredName = PageTheme.getConfigured() == PageTheme.MODERN? UserPreferencesTheme.MODERN.toDisplay().toString()
				: UserPreferencesTheme.CLASSIC.toDisplay().toString();
		assertTrue(name, name.contains("(" + configuredName + ")"));
	}

	private String getClasses(DomBody body) {

		return body.getAttributeValue("class").orElse("");
	}
}
