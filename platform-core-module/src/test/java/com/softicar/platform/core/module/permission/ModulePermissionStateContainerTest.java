package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.permission.TestModulePermission;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import java.util.Collection;
import org.junit.Test;

public class ModulePermissionStateContainerTest extends AbstractDbTest {

	private static final IEmfModulePermission<TestModuleInstance> PERMISSION_ONE =//
			new TestModulePermission<>("e9f3ef4e-7eec-43ef-abd2-52d231161b4e", "permissionOne");
	private static final IEmfModulePermission<TestModuleInstance> PERMISSION_TWO =//
			new TestModulePermission<>("ff1324db-e419-44b1-ac3b-1a20f27dcb66", "permissionTwo");
	private static final IEmfModulePermission<TestModuleInstance> PERMISSION_THREE =//
			new TestModulePermission<>("acdc5653-441b-4d2e-8ca3-a7cff8f71d0f", "permissionThree");
	private final ModulePermissionStateContainer container;

	public ModulePermissionStateContainerTest() {

		this.container = new ModulePermissionStateContainer();
	}

	@Test
	public void testGetPermissionsWithEmptyContainer() {

		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertTrue(permissions.isEmpty());
	}

	@Test
	public void testPutAndGetPermissions() {

		container.put(PERMISSION_ONE, true);
		container.put(PERMISSION_TWO, false);

		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertEquals(2, permissions.size());
		assertTrue(permissions.contains(PERMISSION_ONE));
		assertTrue(permissions.contains(PERMISSION_TWO));
	}

	@Test
	public void testIsActive() {

		container.put(PERMISSION_ONE, true);
		container.put(PERMISSION_TWO, false);

		assertTrue(container.isActive(PERMISSION_ONE));
		assertFalse(container.isActive(PERMISSION_TWO));
	}

	@Test
	public void testIsActiveWithUpdatedState() {

		container.put(PERMISSION_ONE, true);
		container.put(PERMISSION_ONE, false);

		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertEquals(1, permissions.size());
		assertFalse(container.isActive(permissions.iterator().next()));
	}

	@Test
	public void testIsActiveWithUnknownPermission() {

		assertFalse(container.isActive(PERMISSION_ONE));
	}

	@Test
	public void testIsActiveWithNullPermission() {

		assertFalse(container.isActive(null));
	}

	@Test
	public void testIsAllInactiveWithEmptyContainer() {

		assertTrue(container.isAllInactive());
	}

	@Test
	public void testIsAllInactiveWithOnlyActiveStates() {

		container.put(PERMISSION_ONE, true);
		container.put(PERMISSION_TWO, true);

		assertFalse(container.isAllInactive());
	}

	@Test
	public void testIsAllInactiveWithMixedActiveStates() {

		container.put(PERMISSION_ONE, true);
		container.put(PERMISSION_TWO, false);

		assertFalse(container.isAllInactive());
	}

	@Test
	public void testIsAllInactiveWithOnlyInactiveStates() {

		container.put(PERMISSION_ONE, false);
		container.put(PERMISSION_TWO, false);

		assertTrue(container.isAllInactive());
	}

	// -------------------- merge -------------------- //

	@Test
	public void testMergeWithEmptyContainers() {

		ModulePermissionStateContainer other = new ModulePermissionStateContainer();
		ModulePermissionStateContainer merged = container.merge(other);

		assertSame(container, merged);
		assertTrue(merged.getPermissions().isEmpty());
	}

	@Test
	public void testMergeWithEmptyOtherContainer() {

		container.put(PERMISSION_ONE, true);
		ModulePermissionStateContainer other = new ModulePermissionStateContainer();
		ModulePermissionStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertEquals(1, permissions.size());
		assertTrue(container.isActive(permissions.iterator().next()));
	}

	@Test
	public void testMergeWithEmptyOriginalContainer() {

		ModulePermissionStateContainer other = new ModulePermissionStateContainer();
		other.put(PERMISSION_ONE, true);
		ModulePermissionStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertEquals(1, permissions.size());
		assertTrue(container.isActive(permissions.iterator().next()));
	}

	@Test
	public void testMergeWithDisjunctElements() {

		container.put(PERMISSION_ONE, true);
		ModulePermissionStateContainer other = new ModulePermissionStateContainer();
		other.put(PERMISSION_TWO, false);
		ModulePermissionStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertEquals(2, permissions.size());
		assertTrue(container.isActive(PERMISSION_ONE));
		assertFalse(container.isActive(PERMISSION_TWO));
	}

	@Test
	public void testMergeWithOverlappingElement() {

		container.put(PERMISSION_ONE, true);
		container.put(PERMISSION_THREE, false);
		ModulePermissionStateContainer other = new ModulePermissionStateContainer();
		other.put(PERMISSION_TWO, false);
		other.put(PERMISSION_THREE, true);
		ModulePermissionStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModulePermission<?>> permissions = container.getPermissions();
		assertEquals(3, permissions.size());
		assertTrue(container.isActive(PERMISSION_ONE));
		assertFalse(container.isActive(PERMISSION_TWO));
		assertTrue(container.isActive(PERMISSION_THREE));
	}

	private interface TestModuleInstance extends IModuleInstance<TestModuleInstance> {

		// nothing
	}
}
