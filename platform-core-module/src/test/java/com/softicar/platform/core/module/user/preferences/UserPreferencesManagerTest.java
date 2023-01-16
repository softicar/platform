package com.softicar.platform.core.module.user.preferences;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import org.junit.Test;

public class UserPreferencesManagerTest extends AbstractCoreTest {

	private final AGUser user;
	private final UserPreferencesManager manager;

	public UserPreferencesManagerTest() {

		this.user = CurrentUser.get();
		this.manager = new UserPreferencesManager(user);
	}

	@Test
	public void testGetPreferences() {

		// setup
		user.setPreferencesJson("""
				{"navigationFolderCollapseMode":"MANUAL","preferredPopupPlacement":"CENTERED"}
				""").save();

		// execute
		var preferences = manager.getPreferences();

		// assert results
		assertEquals(UserPreferencesNavigationFolderCollapseMode.MANUAL, preferences.navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, preferences.preferredPopupPlacement);
	}

	@Test
	public void testGetPreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		var preferences = manager.getPreferences();

		// assert results
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE, preferences.navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, preferences.preferredPopupPlacement);
	}

	@Test
	public void testGetPreferencesWithCorruptedJsonField() {

		// setup
		user.setPreferencesJson("not-a-json-string").save();

		// execute
		var preferences = manager.getPreferences();

		// assert results
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE, preferences.navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, preferences.preferredPopupPlacement);
	}

	@Test
	public void testSavePreferences() {

		// setup
		user.setPreferencesJson("""
				{"navigationFolderCollapseMode":"MANUAL","preferredPopupPlacement":"AT_EVENT_COORDINATES"}
				""").save();
		var preferences = new UserPreferences();
		preferences.navigationFolderCollapseMode = UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;

		// execute
		manager.savePreferences(preferences);

		// assert results
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE, manager.getPreferences().navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testSavePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// setup
		var preferences = new UserPreferences();
		preferences.navigationFolderCollapseMode = UserPreferencesNavigationFolderCollapseMode.MANUAL;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;

		// execute
		manager.savePreferences(preferences);

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertEquals(UserPreferencesNavigationFolderCollapseMode.MANUAL, manager.getPreferences().navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testUpdatePreferences() {

		// setup
		user.setPreferencesJson("""
				{"navigationFolderCollapseMode":"MANUAL","preferredPopupPlacement":"AT_EVENT_COORDINATES"}
				""").save();

		// execute
		manager.updatePreferences(preferences -> {
			preferences.navigationFolderCollapseMode = UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE;
			preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		});

		// assert results
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE, manager.getPreferences().navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testUpdatePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		manager.updatePreferences(preferences -> {
			preferences.navigationFolderCollapseMode = UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE;
			preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		});

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertEquals(UserPreferencesNavigationFolderCollapseMode.AUTOMATIC_RECURSIVE, manager.getPreferences().navigationFolderCollapseMode);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}
}
