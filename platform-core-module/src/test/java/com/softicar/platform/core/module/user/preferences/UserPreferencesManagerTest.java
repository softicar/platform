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
				{"automaticallyCollapseFolders":false,"recursivelyCollapseFolders":false,"preferredPopupPlacement":"CENTERED"}
				""").save();

		// execute
		var preferences = manager.getPreferences();

		// assert results
		assertFalse(preferences.automaticallyCollapseFolders);
		assertFalse(preferences.recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, preferences.preferredPopupPlacement);
	}

	@Test
	public void testGetPreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		var preferences = manager.getPreferences();

		// assert results
		assertTrue(preferences.automaticallyCollapseFolders);
		assertTrue(preferences.recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, preferences.preferredPopupPlacement);
	}

	@Test
	public void testGetPreferencesWithCorruptedJsonField() {

		// setup
		user.setPreferencesJson("not-a-json-string").save();

		// execute
		var preferences = manager.getPreferences();

		// assert results
		assertTrue(preferences.automaticallyCollapseFolders);
		assertTrue(preferences.recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, preferences.preferredPopupPlacement);
	}

	@Test
	public void testSavePreferences() {

		// setup
		user.setPreferencesJson("""
				{"automaticallyCollapseFolders":false,"recursivelyCollapseFolders":false,"preferredPopupPlacement":"AT_EVENT_COORDINATES"}
				""").save();
		var preferences = new UserPreferences();
		preferences.automaticallyCollapseFolders = true;
		preferences.recursivelyCollapseFolders = true;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;

		// execute
		manager.savePreferences(preferences);

		// assert results
		assertTrue(manager.getPreferences().automaticallyCollapseFolders);
		assertTrue(manager.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testSavePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// setup
		var preferences = new UserPreferences();
		preferences.automaticallyCollapseFolders = false;
		preferences.recursivelyCollapseFolders = false;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;

		// execute
		manager.savePreferences(preferences);

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertFalse(manager.getPreferences().automaticallyCollapseFolders);
		assertFalse(manager.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testUpdatePreferences() {

		// setup
		user.setPreferencesJson("""
				{"automaticallyCollapseFolders":false,"recursivelyCollapseFolders":false,"preferredPopupPlacement":"AT_EVENT_COORDINATES"}
				""").save();

		// execute
		manager.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
			preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		});

		// assert results
		assertTrue(manager.getPreferences().automaticallyCollapseFolders);
		assertTrue(manager.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testUpdatePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		manager.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
			preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		});

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertTrue(manager.getPreferences().automaticallyCollapseFolders);
		assertTrue(manager.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, manager.getPreferences().preferredPopupPlacement);
	}
}
