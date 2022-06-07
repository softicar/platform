package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.permission.IEmfPermissionPredicate;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfStaticPermissionTest extends AbstractTest {

	private final IBasicUser user;
	private final TestModuleInstance moduleInstance;
	private final Predicate basePermissionPredicate;
	private final Predicate inheritingPermissionPredicate;
	private final IEmfStaticPermission<TestModuleInstance> basePermission;
	private final IEmfStaticPermission<TestModuleInstance> inheritingPermission;

	public EmfStaticPermissionTest() {

		this.user = Mockito.mock(IBasicUser.class);
		this.moduleInstance = Mockito.mock(TestModuleInstance.class);
		this.basePermissionPredicate = Mockito.mock(Predicate.class);
		this.inheritingPermissionPredicate = Mockito.mock(Predicate.class);
		this.basePermission = new EmfStaticPermissionBuilder<>(basePermissionPredicate)//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.build();
		this.inheritingPermission = new EmfStaticPermissionBuilder<>(inheritingPermissionPredicate)//
			.setUuid(UUID.randomUUID())
			.setTitle(IDisplayString.EMPTY)
			.impliedBy(basePermission)
			.build();
	}

	@Test
	public void testWithNoPermissionAssigned() {

		assertFalse(basePermission.test(moduleInstance, user));
		assertFalse(inheritingPermission.test(moduleInstance, user));
	}

	@Test
	public void testWithBasePermissionAssigned() {

		Mockito.when(basePermission.test(moduleInstance, user)).thenReturn(true);

		assertTrue(basePermission.test(moduleInstance, user));
		assertTrue(inheritingPermission.test(moduleInstance, user));
	}

	@Test
	public void testWithInheritingPermissionAssigned() {

		Mockito.when(inheritingPermissionPredicate.test(moduleInstance, user)).thenReturn(true);

		assertFalse(basePermission.test(moduleInstance, user));
		assertTrue(inheritingPermission.test(moduleInstance, user));
	}

	@Test
	public void testWithBothPermissionsAssigned() {

		Mockito.when(basePermission.test(moduleInstance, user)).thenReturn(true);
		Mockito.when(inheritingPermissionPredicate.test(moduleInstance, user)).thenReturn(true);

		assertTrue(basePermission.test(moduleInstance, user));
		assertTrue(inheritingPermission.test(moduleInstance, user));
	}

	private static interface Predicate extends IEmfPermissionPredicate<TestModuleInstance> {

		// nothing to add
	}

	private static interface TestModuleInstance extends IEmfModuleInstance<TestModuleInstance> {

		// nothing to add
	}
}
