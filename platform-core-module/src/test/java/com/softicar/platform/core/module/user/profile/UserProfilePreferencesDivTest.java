package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.core.module.user.preferences.UserPreferencesNavigationFolderCollapseMode;
import com.softicar.platform.core.module.user.preferences.UserPreferencesPreferredPopupPlacement;
import com.softicar.platform.core.module.user.preferences.UserPreferencesTheme;
import com.softicar.platform.dom.document.CurrentDomDocument;
import org.junit.Test;

public class UserProfilePreferencesDivTest extends AbstractCoreTest {

	public UserProfilePreferencesDivTest() {

		setNodeSupplier(UserProfilePreferencesDiv::new);
	}

	@Test
	public void testInitialState() {

		var preferences = new UserPreferences();
		preferences.navigationFolderCollapseMode = UserPreferencesNavigationFolderCollapseMode.MANUAL;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		CurrentUser.get().savePreferences(preferences);

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_NAVIGATION_FOLDER_COLLAPSE_MODE).assertValue(UserPreferencesNavigationFolderCollapseMode.MANUAL);
		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT).assertValue(UserPreferencesPreferredPopupPlacement.CENTERED);
		assertEquals(UserPreferencesNavigationFolderCollapseMode.MANUAL, getUserPreferences().navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, getUserPreferences().preferredPopupPlacement);
	}

	@Test
	public void testInitialStateWithDefaults() {

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_NAVIGATION_FOLDER_COLLAPSE_MODE)
			.assertValue(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC);
		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT).assertValue(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES);
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC, getUserPreferences().navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, getUserPreferences().preferredPopupPlacement);
	}

	@Test
	public void testWithNavigationFolderCollapseModeChanged() {

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_NAVIGATION_FOLDER_COLLAPSE_MODE).selectValue(UserPreferencesNavigationFolderCollapseMode.MANUAL);
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC, getUserPreferences().navigationFolderCollapseMode);

		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertEquals(UserPreferencesNavigationFolderCollapseMode.MANUAL, getUserPreferences().navigationFolderCollapseMode);
	}

	@Test
	public void testWithPreferredPopupPlacementChanged() {

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT).selectValue(UserPreferencesPreferredPopupPlacement.CENTERED);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, getUserPreferences().preferredPopupPlacement);

		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, getUserPreferences().preferredPopupPlacement);
	}

	@Test
	public void testWithThemeChanged() {

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_THEME).assertValue(UserPreferencesTheme.STANDARD);

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_THEME).selectValue(UserPreferencesTheme.MODERN);
		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertEquals(UserPreferencesTheme.MODERN, getUserPreferences().theme);
		assertTrue(getBodyClasses().contains("ModernTheme"));

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_THEME).selectValue(UserPreferencesTheme.CLASSIC);
		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertEquals(UserPreferencesTheme.CLASSIC, getUserPreferences().theme);
		assertFalse(getBodyClasses().contains("ModernTheme"));
	}

	@Test
	public void testSaveKeepsTheVariantChosenMeanwhile() {

		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT).selectValue(UserPreferencesPreferredPopupPlacement.CENTERED);
		// the variant chooser saves at once, after this div was built
		CurrentUser.get().updatePreferences(preferences -> {
			preferences.themeVariant = "dark";
			preferences.themeMenu = "light";
		});

		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, getUserPreferences().preferredPopupPlacement);
		assertEquals("dark", getUserPreferences().themeVariant);
		assertEquals("light", getUserPreferences().themeMenu);
	}

	private String getBodyClasses() {

		return CurrentDomDocument.get().getBody().getAttributeValue("class").orElse("");
	}

	private UserPreferences getUserPreferences() {

		return CurrentUser.get().getPreferences();
	}
}
