package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.mapper.AbstractEmfPermissionTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfAnyPermissionTest extends AbstractEmfPermissionTest {

	@Test
	public void testWithEmptySet() {

		EmfAnyPermission<EmfTestSubObject> emptyPermission = new EmfAnyPermission<>();

		assertFalse(emptyPermission.test(new EmfTestSubObject(), EmfTestUser.insert("some", "user")));
	}

	@Test
	public void testWithTwoPermissions() {

		EmfAnyPermission<PermissionEntity> permission = new EmfAnyPermission<>(OPERATOR, GUEST);

		PermissionEntity entity = new PermissionEntity();
		EmfTestUser operator = EmfTestUser.insert("operator", "user");
		EmfTestUser guest = EmfTestUser.insert("guest", "user");
		EmfTestUser nobody = EmfTestUser.insert("nobody", "user");

		entity.addOperator(operator);
		entity.addGuest(guest);

		assertTrue(permission.test(entity, operator));
		assertTrue(permission.test(entity, guest));
		assertFalse(permission.test(entity, nobody));
	}

	@Test
	public void testWithNullEntity() {

		EmfTestUser somebody = EmfTestUser.insert("some", "user");

		assertFalse(new EmfAnyPermission<>().test(null, somebody));
		assertFalse(new EmfAnyPermission<>(OPERATOR).test(null, somebody));
	}

	@Test
	public void testGetTitleWithoutPermissions() {

		EmfAnyPermission<PermissionEntity> permission = new EmfAnyPermission<>();

		assertEquals("", permission.getTitle().toString());
	}

	@Test
	public void testGetTitleWithOnePermission() {

		EmfAnyPermission<PermissionEntity> permission = new EmfAnyPermission<>(OPERATOR);

		assertEquals("Operator", permission.getTitle().toString());
	}

	@Test
	public void testGetTitleWithTwoPermissions() {

		EmfAnyPermission<PermissionEntity> permission = new EmfAnyPermission<>(OPERATOR, GUEST);

		assertEquals("Operator OR Guest", permission.getTitle().toString());
	}
}
