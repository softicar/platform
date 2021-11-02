package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.authorization.AbstractEmfRoleTest;
import org.junit.Test;

public class EmfAllRolesTest extends AbstractEmfRoleTest {

	@Test
	public void testGetTitleWithoutRoles() {

		EmfAllRoles<RoleEntity> role = new EmfAllRoles<>();

		assertEquals("", role.getTitle().toString());
	}

	@Test
	public void testGetTitleWithOneRole() {

		EmfAllRoles<RoleEntity> role = new EmfAllRoles<>(OPERATOR);

		assertEquals("Operator", role.getTitle().toString());
	}

	@Test
	public void testGetTitleWithTwoRoles() {

		EmfAllRoles<RoleEntity> role = new EmfAllRoles<>(OPERATOR, GUEST);

		assertEquals("Operator AND Guest", role.getTitle().toString());
	}
}
