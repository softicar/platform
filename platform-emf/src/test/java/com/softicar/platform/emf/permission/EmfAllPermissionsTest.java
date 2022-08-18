package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.mapper.AbstractEmfPermissionTest;
import org.junit.Test;

public class EmfAllPermissionsTest extends AbstractEmfPermissionTest {

	@Test
	public void testGetTitleWithoutPermissions() {

		EmfAllPermissions<PermissionEntity> permission = new EmfAllPermissions<>();

		assertEquals("", permission.getTitle().toString());
	}

	@Test
	public void testGetTitleWithOnePermission() {

		EmfAllPermissions<PermissionEntity> permission = new EmfAllPermissions<>(OPERATOR);

		assertEquals("Operator", permission.getTitle().toString());
	}

	@Test
	public void testGetTitleWithTwoPermissions() {

		EmfAllPermissions<PermissionEntity> permission = new EmfAllPermissions<>(OPERATOR, GUEST);

		assertEquals("Operator and Guest", permission.getTitle().toString());
	}
}
