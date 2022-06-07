package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.access.permission.assignment.module.system.AGSystemModulePermissionAssignment;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.uuid.AGUuid;
import org.junit.Test;

public class AGUserTest extends AbstractCoreTest {

	private final AGUser user;

	public AGUserTest() {

		this.user = insertTestUser();
	}

	@Test
	public void testIsSuperUserWithSuperUserPermissionOwnership() {

		new AGSystemModulePermissionAssignment()//
			.setActive(true)
			.setModule(AGUuid.getOrCreate(CoreModule.class))
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
