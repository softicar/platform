package com.softicar.platform.emf.module.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfModulePermissionTest extends AbstractTest {

	private final IBasicUser user;
	private final TestModuleInstance moduleInstance;
	private final IEmfModulePermission<TestModuleInstance> basePermission;
	private final IEmfModulePermission<TestModuleInstance> inheritingPermission;

	public EmfModulePermissionTest() {

		this.user = Mockito.mock(IBasicUser.class);
		this.moduleInstance = Mockito.mock(TestModuleInstance.class);
		this.basePermission = new EmfModulePermissionBuilder<TestModuleInstance>()//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.build();
		this.inheritingPermission = new EmfModulePermissionBuilder<TestModuleInstance>()//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.impliedBy(basePermission)
			.build();
	}

	@Test
	public void testWithNoPermissionAssigned() {

		assertFalse(basePermission.test(moduleInstance, user));
		assertFalse(inheritingPermission.test(moduleInstance, user));
	}

	@Test
	public void testWithBasePermissionAssigned() {

		Mockito.when(moduleInstance.hasPermission(basePermission, user)).thenReturn(true);

		assertTrue(basePermission.test(moduleInstance, user));
		assertTrue(inheritingPermission.test(moduleInstance, user));
	}

	@Test
	public void testWithInheritingPermissionAssigned() {

		Mockito.when(moduleInstance.hasPermission(inheritingPermission, user)).thenReturn(true);

		assertFalse(basePermission.test(moduleInstance, user));
		assertTrue(inheritingPermission.test(moduleInstance, user));
	}

	@Test
	public void testWithBothPermissionsAssigned() {

		Mockito.when(moduleInstance.hasPermission(basePermission, user)).thenReturn(true);
		Mockito.when(moduleInstance.hasPermission(inheritingPermission, user)).thenReturn(true);

		assertTrue(basePermission.test(moduleInstance, user));
		assertTrue(inheritingPermission.test(moduleInstance, user));
	}

	private static interface TestModuleInstance extends IEmfModuleInstance<TestModuleInstance> {

		// nothing to add
	}
}
