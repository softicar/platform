package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.authorization.role.IEmfRolePredicate;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfStaticRoleTest extends AbstractTest {

	private final IBasicUser user;
	private final TestModuleInstance moduleInstance;
	private final Predicate baseRolePredicate;
	private final Predicate inheritingRolePredicate;
	private final IEmfStaticRole<TestModuleInstance> baseRole;
	private final IEmfStaticRole<TestModuleInstance> inheritingRole;

	public EmfStaticRoleTest() {

		this.user = Mockito.mock(IBasicUser.class);
		this.moduleInstance = Mockito.mock(TestModuleInstance.class);
		this.baseRolePredicate = Mockito.mock(Predicate.class);
		this.inheritingRolePredicate = Mockito.mock(Predicate.class);
		this.baseRole = new EmfStaticRoleBuilder<>(baseRolePredicate)//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.build();
		this.inheritingRole = new EmfStaticRoleBuilder<>(inheritingRolePredicate)//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.impliedBy(baseRole)
			.build();
	}

	@Test
	public void testWithNoRoleAssigned() {

		assertFalse(baseRole.test(moduleInstance, user));
		assertFalse(inheritingRole.test(moduleInstance, user));
	}

	@Test
	public void testWithBaseRoleAssigned() {

		Mockito.when(baseRole.test(moduleInstance, user)).thenReturn(true);

		assertTrue(baseRole.test(moduleInstance, user));
		assertTrue(inheritingRole.test(moduleInstance, user));
	}

	@Test
	public void testWithInheritingRoleAssigned() {

		Mockito.when(inheritingRolePredicate.test(moduleInstance, user)).thenReturn(true);

		assertFalse(baseRole.test(moduleInstance, user));
		assertTrue(inheritingRole.test(moduleInstance, user));
	}

	@Test
	public void testWithBothRolesAssigned() {

		Mockito.when(baseRole.test(moduleInstance, user)).thenReturn(true);
		Mockito.when(inheritingRolePredicate.test(moduleInstance, user)).thenReturn(true);

		assertTrue(baseRole.test(moduleInstance, user));
		assertTrue(inheritingRole.test(moduleInstance, user));
	}

	private static interface Predicate extends IEmfRolePredicate<TestModuleInstance> {

		// nothing to add
	}

	private static interface TestModuleInstance extends IEmfModuleInstance<TestModuleInstance> {

		// nothing to add
	}
}
