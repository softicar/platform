package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;
import org.mockito.Mockito;

public class CurrentEmfRoleRegistryTest extends AbstractDbTest {

	@Test
	public void testGet() {

		IEmfRoleRegistry roleRegistry = CurrentEmfRoleRegistry.get();

		assertNotNull(roleRegistry);
		assertTrue(roleRegistry instanceof EmfRoleRegistry);
	}

	@Test
	public void testSet() {

		IEmfRoleRegistry customRegistry = Mockito.mock(IEmfRoleRegistry.class);

		CurrentEmfRoleRegistry.set(customRegistry);

		assertEquals(customRegistry, CurrentEmfRoleRegistry.get());
	}
}
