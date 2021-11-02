package com.softicar.platform.core.module.access.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfSystemModuleRoleTest extends AbstractModuleRoleTestBase {

	private final EmfSystemModuleRole role;
	private final IEmfModuleInstance<?> moduleInstance;

	public EmfSystemModuleRoleTest() {

		this.role = new EmfSystemModuleRole(ROLE_UUID, IDisplayString.create("TestRole"));
		this.moduleInstance = Mockito.mock(IEmfModuleInstance.class);
	}

	@Test
	public void testWithRoleMembership() {

		insertRoleMembership(true);

		assertTrue(role.test(null, user));
	}

	@Test
	public void testWithoutRoleMembership() {

		assertFalse(role.test(null, user));
	}

	@Test
	public void testWithInactiveRoleMembership() {

		insertRoleMembership(false);

		assertFalse(role.test(null, user));
	}

	@Test
	public void testToOtherEntityRoleWithRoleMembership() {

		insertRoleMembership(true);

		assertTrue(role.toOtherEntityRole().test(moduleInstance, user));
	}

	@Test
	public void testToOtherEntityRoleWithoutRoleMembership() {

		assertFalse(role.toOtherEntityRole().test(moduleInstance, user));
	}

	@Test
	public void testToOtherEntityRoleWithInactiveRoleMembership() {

		insertRoleMembership(false);

		assertFalse(role.toOtherEntityRole().test(moduleInstance, user));
	}
}
