package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.preferences.UserPreferences;
import com.softicar.platform.core.module.user.preferences.UserPreferencesPreferredPopupPlacement;
import org.junit.Test;

public class UserProfilePreferencesDivTest extends AbstractCoreTest {

	public UserProfilePreferencesDivTest() {

		setNodeSupplier(UserProfilePreferencesDiv::new);
	}

	@Test
	public void testInitialState() {

		var preferences = new UserPreferences();
		preferences.automaticallyCollapseFolders = true;
		preferences.recursivelyCollapseFolders = true;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		CurrentUser.get().savePreferences(preferences);

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(true).assertNodeDisabled(false);
		findCheckbox(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(true).assertNodeDisabled(true);
		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT).assertValue(UserPreferencesPreferredPopupPlacement.CENTERED);
		assertTrue(getUserPreferences().automaticallyCollapseFolders);
		assertTrue(getUserPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, getUserPreferences().preferredPopupPlacement);
	}

	@Test
	public void testInitialStateWithDefaults() {

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(false).assertNodeDisabled(false);
		findCheckbox(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(false).assertNodeDisabled(false);
		findCheckboxGroup(CoreTestMarker.USER_PREFERENCES_PREFERRED_POPUP_PLACEMENT).assertValue(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES);
		assertFalse(getUserPreferences().automaticallyCollapseFolders);
		assertFalse(getUserPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, getUserPreferences().preferredPopupPlacement);
	}

	@Test
	public void testWithClickOnAutomaticallyCollapseFoldersCheckbox() {

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).click();

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(true).assertNodeDisabled(false);
		findCheckbox(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(true).assertNodeDisabled(true);
		assertFalse(getUserPreferences().automaticallyCollapseFolders);
		assertFalse(getUserPreferences().recursivelyCollapseFolders);

		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertTrue(getUserPreferences().automaticallyCollapseFolders);
		assertTrue(getUserPreferences().recursivelyCollapseFolders);
	}

	@Test
	public void testWithClickOnAutomaticallyCollapseFoldersCheckboxTwice() {

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).click().click();

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(false).assertNodeDisabled(false);
		findCheckbox(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(true).assertNodeDisabled(false);
		assertFalse(getUserPreferences().automaticallyCollapseFolders);
		assertFalse(getUserPreferences().recursivelyCollapseFolders);

		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertFalse(getUserPreferences().automaticallyCollapseFolders);
		assertTrue(getUserPreferences().recursivelyCollapseFolders);
	}

	@Test
	public void testWithClickOnRecursivelyCollapseFoldersCheckbox() {

		findCheckbox(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX).click();

		findCheckbox(CoreTestMarker.USER_PREFERENCES_AUTOMATICALLY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(false).assertNodeDisabled(false);
		findCheckbox(CoreTestMarker.USER_PREFERENCES_RECURSIVELY_COLLAPSE_FOLDERS_CHECKBOX).assertChecked(true).assertNodeDisabled(false);
		assertFalse(getUserPreferences().automaticallyCollapseFolders);
		assertFalse(getUserPreferences().recursivelyCollapseFolders);

		findButton(CoreTestMarker.USER_PREFERENCES_SAVE_BUTTON).click();

		assertFalse(getUserPreferences().automaticallyCollapseFolders);
		assertTrue(getUserPreferences().recursivelyCollapseFolders);
	}

	private UserPreferences getUserPreferences() {

		return CurrentUser.get().getPreferences();
	}
}
