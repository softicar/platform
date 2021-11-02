package com.softicar.platform.emf.module.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfModuleRoleTest extends Assert {

	private final IBasicUser user;
	private final TestModuleInstance moduleInstance;
	private final IEmfModuleRole<TestModuleInstance> baseRole;
	private final IEmfModuleRole<TestModuleInstance> inheritingRole;

	public EmfModuleRoleTest() {

		this.user = Mockito.mock(IBasicUser.class);
		this.moduleInstance = Mockito.mock(TestModuleInstance.class);
		this.baseRole = new EmfModuleRoleBuilder<TestModuleInstance>()//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.build();
		this.inheritingRole = new EmfModuleRoleBuilder<TestModuleInstance>()//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.impliedBy(baseRole)
			.build();
	}

	@Test
	public void testWithNoRoleAssigned() {

		assertFalse(baseRole.test(moduleInstance, user));
		assertFalse(inheritingRole.test(moduleInstance, user));
	}

	@Test
	public void testWithBaseRoleAssigned() {

		Mockito.when(moduleInstance.hasRole(baseRole, user)).thenReturn(true);

		assertTrue(baseRole.test(moduleInstance, user));
		assertTrue(inheritingRole.test(moduleInstance, user));
	}

	@Test
	public void testWithInheritingRoleAssigned() {

		Mockito.when(moduleInstance.hasRole(inheritingRole, user)).thenReturn(true);

		assertFalse(baseRole.test(moduleInstance, user));
		assertTrue(inheritingRole.test(moduleInstance, user));
	}

	@Test
	public void testWithBothRolesAssigned() {

		Mockito.when(moduleInstance.hasRole(baseRole, user)).thenReturn(true);
		Mockito.when(moduleInstance.hasRole(inheritingRole, user)).thenReturn(true);

		assertTrue(baseRole.test(moduleInstance, user));
		assertTrue(inheritingRole.test(moduleInstance, user));
	}

	private static interface TestModuleInstance extends IEmfModuleInstance<TestModuleInstance> {

		// nothing to add
	}
}
