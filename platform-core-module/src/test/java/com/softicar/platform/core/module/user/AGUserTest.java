package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class AGUserTest extends AbstractCoreTest {

	private final AGUser user;
	private final AGCoreModuleInstance instance;

	public AGUserTest() {

		this.user = insertTestUser();
		this.instance = AGCoreModuleInstance.getInstance();
	}

	@Test
	public void testIsSuperUserWithSuperUserPermissionOwnership() {

		insertPermissionAssignment(user, CorePermissions.ADMINISTRATION, instance);

		assertTrue(CorePermissions.ADMINISTRATION.test(instance, user));
	}

	@Test
	public void testIsSuperUserWithoutSuperUserPermissionOwnership() {

		assertFalse(CorePermissions.ADMINISTRATION.test(instance, user));
	}

	@Test
	public void testGetPreferences() {

		// setup
		user.setPreferencesJson("""
				{"automaticallyCollapseFolders":true,"recursivelyCollapseFolders":true,"preferredPopupPlacement":"CENTERED"}
				""").save();

		// execute
		var preferences = user.getPreferences();

		// assert results
		assertTrue(preferences.automaticallyCollapseFolders);
		assertTrue(preferences.recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, user.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testGetPreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		var preferences = user.getPreferences();

		// assert results
		assertFalse(preferences.automaticallyCollapseFolders);
		assertFalse(preferences.recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, user.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testGetPreferencesWithCorruptedJsonField() {

		// setup
		user.setPreferencesJson("not-a-json-string").save();

		// execute
		var preferences = user.getPreferences();

		// assert results
		assertFalse(preferences.automaticallyCollapseFolders);
		assertFalse(preferences.recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.AT_EVENT_COORDINATES, user.getPreferences().preferredPopupPlacement);
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
		user.savePreferences(preferences);

		// assert results
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, user.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testSavePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// setup
		var preferences = new UserPreferences();
		preferences.automaticallyCollapseFolders = true;
		preferences.recursivelyCollapseFolders = true;
		preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;

		// execute
		user.savePreferences(preferences);

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, user.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testUpdatePreferences() {

		// setup
		user.setPreferencesJson("""
				{"automaticallyCollapseFolders":false,"recursivelyCollapseFolders":false,"preferredPopupPlacement":"AT_EVENT_COORDINATES"}
				""").save();

		// execute
		user.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
			preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		});

		// assert results
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, user.getPreferences().preferredPopupPlacement);
	}

	@Test
	public void testUpdatePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		user.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
			preferences.preferredPopupPlacement = UserPreferencesPreferredPopupPlacement.CENTERED;
		});

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
		assertEquals(UserPreferencesPreferredPopupPlacement.CENTERED, user.getPreferences().preferredPopupPlacement);
	}
}
