package com.softicar.platform.core.module.access.permission.assignment;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.permission.AbstractModulePermissionTestBase;
import com.softicar.platform.core.module.access.permission.EmfSystemModulePermission;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import org.junit.Test;

public class EmfModulePermissionAssignmentCacheTest extends AbstractModulePermissionTestBase {

	private final EmfModulePermissionAssignmentCache cache;
	private final AGModuleInstance moduleInstance;
	private final IEmfModulePermission<AGCoreModuleInstance> standardModulePermission;
	private final EmfSystemModulePermission systemModulePermission;

	public EmfModulePermissionAssignmentCacheTest() {

		this.moduleInstance = insertModuleInstance(MODULE_UUID);
		this.cache = new EmfModulePermissionAssignmentCache(user);
		this.standardModulePermission = new TestModulePermission<>(PERMISSION_UUID, "TestPermission");
		this.systemModulePermission = new EmfSystemModulePermission(PERMISSION_UUID, IDisplayString.create("TestPermission"));
	}

	@Test
	public void testHasStandardModulePermissionWithAccessPermission() {

		insertModulePermissionAssignment(moduleInstance, true);

		assertTrue(cache.hasModulePermission(standardModulePermission.getAnnotatedUuid(), moduleInstance));
	}

	@Test
	public void testHasStandardModulePermissionWithoutAccessPermission() {

		assertFalse(cache.hasModulePermission(standardModulePermission.getAnnotatedUuid(), moduleInstance));
	}

	@Test
	public void testHasSystemModulePermissionWithAccessPermission() {

		insertPermissionAssignment(true);

		assertTrue(cache.hasModulePermission(systemModulePermission));
	}

	@Test
	public void testHasSystemModulePermissionWithoutAccessPermission() {

		assertFalse(cache.hasModulePermission(systemModulePermission));
	}
}
