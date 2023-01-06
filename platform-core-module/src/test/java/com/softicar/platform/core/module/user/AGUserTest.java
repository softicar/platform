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
				{"automaticallyCollapseFolders":true,"recursivelyCollapseFolders":true}
				""").save();

		// execute
		var preferences = user.getPreferences();

		// assert results
		assertTrue(preferences.automaticallyCollapseFolders);
		assertTrue(preferences.recursivelyCollapseFolders);
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
	}

	@Test
	public void testSavePreferences() {

		// setup
		user.setPreferencesJson("""
				{"automaticallyCollapseFolders":false,"recursivelyCollapseFolders":false}
				""").save();
		var preferences = new UserPreferences();
		preferences.automaticallyCollapseFolders = true;
		preferences.recursivelyCollapseFolders = true;

		// execute
		user.savePreferences(preferences);

		// assert results
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
	}

	@Test
	public void testSavePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// setup
		var preferences = new UserPreferences();
		preferences.automaticallyCollapseFolders = true;
		preferences.recursivelyCollapseFolders = true;

		// execute
		user.savePreferences(preferences);

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
	}

	@Test
	public void testUpdatePreferences() {

		// setup
		user.setPreferencesJson("""
				{"automaticallyCollapseFolders":false,"recursivelyCollapseFolders":false}
				""").save();

		// execute
		user.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
		});

		// assert results
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
	}

	@Test
	public void testUpdatePreferencesWithEmptyJsonField() {

		// assert precondition
		assertTrue(user.getPreferencesJson().isEmpty());

		// execute
		user.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
		});

		// assert results
		assertFalse(user.getPreferencesJson().isEmpty());
		assertTrue(user.getPreferences().automaticallyCollapseFolders);
		assertTrue(user.getPreferences().recursivelyCollapseFolders);
	}
}
