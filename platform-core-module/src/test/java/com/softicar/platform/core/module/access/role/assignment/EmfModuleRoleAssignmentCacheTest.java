package com.softicar.platform.core.module.access.role.assignment;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.AbstractModuleRoleTestBase;
import com.softicar.platform.core.module.access.role.EmfSystemModuleRole;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.role.TestModuleRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import org.junit.Test;

public class EmfModuleRoleAssignmentCacheTest extends AbstractModuleRoleTestBase {

	private final EmfModuleRoleAssignmentCache cache;
	private final AGModuleInstance moduleInstance;
	private final IEmfModuleRole<AGCoreModuleInstance> standardModuleRole;
	private final EmfSystemModuleRole systemModuleRole;

	public EmfModuleRoleAssignmentCacheTest() {

		this.moduleInstance = insertModuleInstance(MODULE_UUID);
		this.cache = new EmfModuleRoleAssignmentCache(user);
		this.standardModuleRole = new TestModuleRole<>(ROLE_UUID, "TestRole");
		this.systemModuleRole = new EmfSystemModuleRole(ROLE_UUID, IDisplayString.create("TestRole"));
	}

	@Test
	public void testHasStandardModuleRoleWithAccessPermission() {

		insertModuleRoleMembership(moduleInstance, true);

		assertTrue(cache.hasModuleRole(standardModuleRole.getAnnotatedUuid(), moduleInstance));
	}

	@Test
	public void testHasStandardModuleRoleWithoutAccessPermission() {

		assertFalse(cache.hasModuleRole(standardModuleRole.getAnnotatedUuid(), moduleInstance));
	}

	@Test
	public void testHasSystemModuleRoleWithAccessPermission() {

		insertRoleMembership(true);

		assertTrue(cache.hasModuleRole(systemModuleRole));
	}

	@Test
	public void testHasSystemModuleRoleWithoutAccessPermission() {

		assertFalse(cache.hasModuleRole(systemModuleRole));
	}
}
