package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.access.permission.assignment.module.instance.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class AGUserTest extends AbstractCoreTest {

	private final AGUser user;

	public AGUserTest() {

		this.user = insertTestUser();
	}

	@Test
	public void testIsSuperUserWithSuperUserPermissionOwnership() {

		new AGModuleInstancePermissionAssignment()//
			.setActive(true)
			.setModuleInstance(AGCoreModuleInstance.getInstance().pk())
			.setPermission(CorePermissions.SUPER_USER.getAnnotatedUuid())
			.setUser(user)
			.save();

		assertTrue(user.isSuperUser());
	}

	@Test
	public void testIsSuperUserWithoutSuperUserPermissionOwnership() {

		assertFalse(user.isSuperUser());
	}
}
