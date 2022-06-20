package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.core.module.permission.AbstractModulePermissionTestBase;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import org.junit.Test;

public class EmfModulePermissionAssignmentCacheTest extends AbstractModulePermissionTestBase {

	private final EmfModulePermissionAssignmentCache cache;
	private final AGModuleInstance moduleInstance;
	private final IEmfModulePermission<AGCoreModuleInstance> modulePermission;

	public EmfModulePermissionAssignmentCacheTest() {

		this.moduleInstance = AGCoreModuleInstance.getInstance().pk();
		this.cache = new EmfModulePermissionAssignmentCache(user);
		this.modulePermission = new TestModulePermission<>(PERMISSION_UUID, "TestPermission");
	}

	@Test
	public void testHasModulePermissionWithAccessPermission() {

		insertModulePermissionAssignment(moduleInstance, true);

		assertTrue(cache.hasModulePermission(modulePermission.getAnnotatedUuid(), moduleInstance));
	}

	@Test
	public void testHasModulePermissionWithoutAccessPermission() {

		assertFalse(cache.hasModulePermission(modulePermission.getAnnotatedUuid(), moduleInstance));
	}
}
