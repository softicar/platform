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
}
