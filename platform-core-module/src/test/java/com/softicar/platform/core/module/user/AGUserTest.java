package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.access.role.assignment.module.system.AGSystemModuleRoleAssignment;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.uuid.AGUuid;
import org.junit.Test;

public class AGUserTest extends AbstractCoreTest {

	private final AGUser user;

	public AGUserTest() {

		this.user = insertTestUser();
	}

	@Test
	public void testIsSuperUserWithSuperUserRoleMembership() {

		new AGSystemModuleRoleAssignment()//
			.setActive(true)
			.setModule(AGUuid.getOrCreate(CoreModule.class))
			.setRole(CoreRoles.SUPER_USER.getAnnotatedUuid())
			.setUser(user)
			.save();

		assertTrue(user.isSuperUser());
	}

	@Test
	public void testIsSuperUserWithoutSuperUserRoleMembership() {

		assertFalse(user.isSuperUser());
	}
}
