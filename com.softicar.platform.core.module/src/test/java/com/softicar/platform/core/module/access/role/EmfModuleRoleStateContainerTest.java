package com.softicar.platform.core.module.access.role;

import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.module.role.TestModuleRole;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.Collection;
import org.junit.Test;

public class EmfModuleRoleStateContainerTest extends AbstractDbTest {

	private static final IEmfModuleRole<TestModuleInstance> ROLE_ONE = new TestModuleRole<>("e9f3ef4e-7eec-43ef-abd2-52d231161b4e", "roleOne");
	private static final IEmfModuleRole<TestModuleInstance> ROLE_TWO = new TestModuleRole<>("ff1324db-e419-44b1-ac3b-1a20f27dcb66", "roleTwo");
	private static final IEmfModuleRole<TestModuleInstance> ROLE_THREE = new TestModuleRole<>("acdc5653-441b-4d2e-8ca3-a7cff8f71d0f", "roleThree");
	private final EmfModuleRoleStateContainer container;

	public EmfModuleRoleStateContainerTest() {

		this.container = new EmfModuleRoleStateContainer();
	}

	@Test
	public void testGetRolesWithEmptyContainer() {

		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertTrue(roles.isEmpty());
	}

	@Test
	public void testPutAndGetRoles() {

		container.put(ROLE_ONE, true);
		container.put(ROLE_TWO, false);

		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertEquals(2, roles.size());
		assertTrue(roles.contains(ROLE_ONE));
		assertTrue(roles.contains(ROLE_TWO));
	}

	@Test
	public void testIsActive() {

		container.put(ROLE_ONE, true);
		container.put(ROLE_TWO, false);

		assertTrue(container.isActive(ROLE_ONE));
		assertFalse(container.isActive(ROLE_TWO));
	}

	@Test
	public void testIsActiveWithUpdatedState() {

		container.put(ROLE_ONE, true);
		container.put(ROLE_ONE, false);

		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertEquals(1, roles.size());
		assertFalse(container.isActive(roles.iterator().next()));
	}

	@Test
	public void testIsActiveWithUnknownRole() {

		assertFalse(container.isActive(ROLE_ONE));
	}

	@Test
	public void testIsActiveWithNullRole() {

		assertFalse(container.isActive(null));
	}

	@Test
	public void testIsAllInactiveWithEmptyContainer() {

		assertTrue(container.isAllInactive());
	}

	@Test
	public void testIsAllInactiveWithOnlyActiveStates() {

		container.put(ROLE_ONE, true);
		container.put(ROLE_TWO, true);

		assertFalse(container.isAllInactive());
	}

	@Test
	public void testIsAllInactiveWithMixedActiveStates() {

		container.put(ROLE_ONE, true);
		container.put(ROLE_TWO, false);

		assertFalse(container.isAllInactive());
	}

	@Test
	public void testIsAllInactiveWithOnlyInactiveStates() {

		container.put(ROLE_ONE, false);
		container.put(ROLE_TWO, false);

		assertTrue(container.isAllInactive());
	}

	// -------- TODO testMerge --------

	@Test
	public void testMergeWithEmptyContainers() {

		EmfModuleRoleStateContainer other = new EmfModuleRoleStateContainer();
		EmfModuleRoleStateContainer merged = container.merge(other);

		assertSame(container, merged);
		assertTrue(merged.getRoles().isEmpty());
	}

	@Test
	public void testMergeWithEmptyOtherContainer() {

		container.put(ROLE_ONE, true);
		EmfModuleRoleStateContainer other = new EmfModuleRoleStateContainer();
		EmfModuleRoleStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertEquals(1, roles.size());
		assertTrue(container.isActive(roles.iterator().next()));
	}

	@Test
	public void testMergeWithEmptyOriginalContainer() {

		EmfModuleRoleStateContainer other = new EmfModuleRoleStateContainer();
		other.put(ROLE_ONE, true);
		EmfModuleRoleStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertEquals(1, roles.size());
		assertTrue(container.isActive(roles.iterator().next()));
	}

	@Test
	public void testMergeWithDisjunctElements() {

		container.put(ROLE_ONE, true);
		EmfModuleRoleStateContainer other = new EmfModuleRoleStateContainer();
		other.put(ROLE_TWO, false);
		EmfModuleRoleStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertEquals(2, roles.size());
		assertTrue(container.isActive(ROLE_ONE));
		assertFalse(container.isActive(ROLE_TWO));
	}

	@Test
	public void testMergeWithOverlappingElement() {

		container.put(ROLE_ONE, true);
		container.put(ROLE_THREE, false);
		EmfModuleRoleStateContainer other = new EmfModuleRoleStateContainer();
		other.put(ROLE_TWO, false);
		other.put(ROLE_THREE, true);
		EmfModuleRoleStateContainer merged = container.merge(other);

		assertSame(container, merged);
		Collection<IEmfModuleRole<?>> roles = container.getRoles();
		assertEquals(3, roles.size());
		assertTrue(container.isActive(ROLE_ONE));
		assertFalse(container.isActive(ROLE_TWO));
		assertTrue(container.isActive(ROLE_THREE));
	}

	private interface TestModuleInstance extends IStandardModuleInstance<TestModuleInstance> {

		// nothing
	}
}
