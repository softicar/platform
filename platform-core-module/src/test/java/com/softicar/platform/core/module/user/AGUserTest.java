package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class AGUserTest extends AbstractCoreTest {

	private final AGUser user;

	public AGUserTest() {

		this.user = insertTestUser();
	}

	@Test
	public void testIsSuperUserWithSuperUserPermissionOwnership() {

		insertPermissionAssignment(user, CorePermissions.ADMINISTRATION, AGCoreModuleInstance.getInstance());

		assertTrue(user.isCoreModuleAdmin());
	}

	@Test
	public void testIsSuperUserWithoutSuperUserPermissionOwnership() {

		assertFalse(user.isCoreModuleAdmin());
	}
}
