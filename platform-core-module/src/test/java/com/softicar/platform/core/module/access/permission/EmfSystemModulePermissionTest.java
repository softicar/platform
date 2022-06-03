package com.softicar.platform.core.module.access.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfSystemModulePermissionTest extends AbstractModulePermissionTestBase {

	private final EmfSystemModulePermission permission;
	private final IEmfModuleInstance<?> moduleInstance;

	public EmfSystemModulePermissionTest() {

		this.permission = new EmfSystemModulePermission(PERMISSION_UUID, IDisplayString.create("TestPermission"));
		this.moduleInstance = Mockito.mock(IEmfModuleInstance.class);
	}

	@Test
	public void testWithPermissionOwnership() {

		insertPermissionAssignment(true);

		assertTrue(permission.test(null, user));
	}

	@Test
	public void testWithoutPermissionOwnership() {

		assertFalse(permission.test(null, user));
	}

	@Test
	public void testWithInactivePermissionOwnership() {

		insertPermissionAssignment(false);

		assertFalse(permission.test(null, user));
	}

	@Test
	public void testToOtherEntityPermissionWithPermissionOwnership() {

		insertPermissionAssignment(true);

		assertTrue(permission.toOtherEntityPermission().test(moduleInstance, user));
	}

	@Test
	public void testToOtherEntityPermissionWithoutPermissionOwnership() {

		assertFalse(permission.toOtherEntityPermission().test(moduleInstance, user));
	}

	@Test
	public void testToOtherEntityPermissionWithInactivePermissionOwnership() {

		insertPermissionAssignment(false);

		assertFalse(permission.toOtherEntityPermission().test(moduleInstance, user));
	}
}
