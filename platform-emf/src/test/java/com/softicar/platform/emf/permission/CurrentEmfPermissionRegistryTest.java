package com.softicar.platform.emf.permission;

import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;
import org.mockito.Mockito;

public class CurrentEmfPermissionRegistryTest extends AbstractDbTest {

	@Test
	public void testGet() {

		IEmfPermissionRegistry permissionRegistry = CurrentEmfPermissionRegistry.get();

		assertNotNull(permissionRegistry);
		assertTrue(permissionRegistry instanceof EmfPermissionRegistry);
	}

	@Test
	public void testSet() {

		IEmfPermissionRegistry customRegistry = Mockito.mock(IEmfPermissionRegistry.class);

		CurrentEmfPermissionRegistry.set(customRegistry);

		assertEquals(customRegistry, CurrentEmfPermissionRegistry.get());
	}
}
