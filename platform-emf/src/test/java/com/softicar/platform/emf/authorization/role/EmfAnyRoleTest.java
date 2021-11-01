package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.authorization.AbstractEmfRoleTest;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.user.EmfTestUser;
import org.junit.Test;

public class EmfAnyRoleTest extends AbstractEmfRoleTest {

	@Test
	public void testWithEmptySet() {

		EmfAnyRole<EmfTestSubObject> emptyRole = new EmfAnyRole<>();

		assertFalse(emptyRole.test(new EmfTestSubObject(), EmfTestUser.insert("some", "user")));
	}

	@Test
	public void testWithTwoRoles() {

		EmfAnyRole<RoleEntity> role = new EmfAnyRole<>(OPERATOR, GUEST);

		RoleEntity entity = new RoleEntity();
		EmfTestUser operator = EmfTestUser.insert("operator", "user");
		EmfTestUser guest = EmfTestUser.insert("guest", "user");
		EmfTestUser nobody = EmfTestUser.insert("nobody", "user");

		entity.addOperator(operator);
		entity.addGuest(guest);

		assertTrue(role.test(entity, operator));
		assertTrue(role.test(entity, guest));
		assertFalse(role.test(entity, nobody));
	}

	@Test
	public void testWithNullEntity() {

		EmfTestUser somebody = EmfTestUser.insert("some", "user");

		assertFalse(new EmfAnyRole<>().test(null, somebody));
		assertFalse(new EmfAnyRole<>(OPERATOR).test(null, somebody));
	}

	@Test
	public void testGetTitleWithoutRoles() {

		EmfAnyRole<RoleEntity> role = new EmfAnyRole<>();

		assertEquals("", role.getTitle().toString());
	}

	@Test
	public void testGetTitleWithOneRole() {

		EmfAnyRole<RoleEntity> role = new EmfAnyRole<>(OPERATOR);

		assertEquals("Operator", role.getTitle().toString());
	}

	@Test
	public void testGetTitleWithTwoRoles() {

		EmfAnyRole<RoleEntity> role = new EmfAnyRole<>(OPERATOR, GUEST);

		assertEquals("Operator OR Guest", role.getTitle().toString());
	}
}
