package com.softicar.platform.core.module.user.preferences;

/**
 * Provides user specific preferences.
 * <p>
 * <b>WARNING:</b> Instances are serialized and un-serialized via Gson. Renaming
 * fields in this class will partially invalidate stored user preferences.
 *
 * @author Alexander Schmidt
 */
public class UserPreferences {

	public UserPreferencesNavigationFolderCollapseMode navigationFolderCollapseMode = UserPreferencesNavigationFolderCollapseMode.AUTOMATIC;
	public UserPreferencesPreferredPopupPlacement preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES;
}
