package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.test.module.EmfTestModule;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlpha;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlphaPermissions;
import com.softicar.platform.emf.test.module.beta.TestModuleBeta;
import com.softicar.platform.emf.test.module.beta.TestModuleBetaPermissions;
import com.softicar.platform.emf.test.module.permission.PermissionTestModule;
import com.softicar.platform.emf.test.module.permission.PermissionTestModuleInstance;
import com.softicar.platform.emf.test.module.permission.PermissionTestModuleInstanceTable;
import java.util.Collection;
import java.util.UUID;
import org.junit.Test;

/**
 * TODO add further test cases for {@link EmfPermissionRegistry}
 *
 * @author Alexander Schmidt
 */
public class EmfPermissionRegistryTest extends AbstractEmfTest {

	private final IEmfPermissionRegistry permissionRegistry;
	private final TestModuleAlpha moduleAlpha;
	private final TestModuleBeta moduleBeta;
	private final PermissionTestModule permissionTestModule;
	private final EmfTestModule testModule;

	public EmfPermissionRegistryTest() {

		this.permissionRegistry = CurrentEmfPermissionRegistry.get();
		this.moduleAlpha = getModule(TestModuleAlpha.class);
		this.moduleBeta = getModule(TestModuleBeta.class);
		this.permissionTestModule = getModule(PermissionTestModule.class);
		this.testModule = getModule(EmfTestModule.class);
	}

	@Test
	public void testGetModules() {

		Collection<IEmfModule<?>> modules = permissionRegistry.getModules();

		assertEquals(4, modules.size());
		assertTrue(modules.contains(moduleAlpha));
		assertTrue(modules.contains(moduleBeta));
		assertTrue(modules.contains(permissionTestModule));
		assertTrue(modules.contains(testModule));
	}

	@Test
	public void testGetStaticPermission() {

		IEmfStaticPermission<?> alphaPermission1 = permissionRegistry.getStaticPermission(TestModuleAlphaPermissions.PERMISSION_ONE.getAnnotatedUuid()).get();
		IEmfStaticPermission<?> alphaPermission2 = permissionRegistry.getStaticPermission(TestModuleAlphaPermissions.PERMISSION_TWO.getAnnotatedUuid()).get();
		IEmfStaticPermission<?> betaPermission1 = permissionRegistry.getStaticPermission(TestModuleBetaPermissions.PERMISSION_ONE.getAnnotatedUuid()).get();
		IEmfStaticPermission<?> betaPermission2 = permissionRegistry.getStaticPermission(TestModuleBetaPermissions.PERMISSION_TWO.getAnnotatedUuid()).get();
		IEmfStaticPermission<?> permissionTestPermission =
				permissionRegistry.getStaticPermission(PermissionTestModuleInstanceTable.STATIC_TEST_PERMISSION.getAnnotatedUuid()).get();

		assertSame(TestModuleAlphaPermissions.PERMISSION_ONE, alphaPermission1);
		assertSame(TestModuleAlphaPermissions.PERMISSION_TWO, alphaPermission2);
		assertSame(TestModuleBetaPermissions.PERMISSION_ONE, betaPermission1);
		assertSame(TestModuleBetaPermissions.PERMISSION_TWO, betaPermission2);
		assertSame(PermissionTestModuleInstanceTable.STATIC_TEST_PERMISSION, permissionTestPermission);
	}

	@Test
	public void testGetStaticPermissions() {

		Collection<IEmfStaticPermission<?>> alphaPermissions = permissionRegistry.getStaticPermissions(moduleAlpha);
		Collection<IEmfStaticPermission<?>> betaPermissions = permissionRegistry.getStaticPermissions(moduleBeta);
		Collection<IEmfStaticPermission<?>> permissionTestPermissions = permissionRegistry.getStaticPermissions(permissionTestModule);

		assertEquals(2, alphaPermissions.size());
		assertTrue(alphaPermissions.contains(TestModuleAlphaPermissions.PERMISSION_ONE));
		assertTrue(alphaPermissions.contains(TestModuleAlphaPermissions.PERMISSION_TWO));
		assertEquals(2, betaPermissions.size());
		assertTrue(betaPermissions.contains(TestModuleBetaPermissions.PERMISSION_ONE));
		assertTrue(betaPermissions.contains(TestModuleBetaPermissions.PERMISSION_TWO));
		assertEquals(1, permissionTestPermissions.size());
		assertTrue(permissionTestPermissions.contains(PermissionTestModuleInstanceTable.STATIC_TEST_PERMISSION));
	}

	@Test
	public void testGetStaticPermissionByTableAndUuid() {

		PermissionTestModuleInstanceTable table = PermissionTestModuleInstance.TABLE;
		UUID permissionUuid = PermissionTestModuleInstanceTable.STATIC_TEST_PERMISSION.getAnnotatedUuid();

		var staticPermission = permissionRegistry.getStaticPermission(table, permissionUuid);

		assertTrue(staticPermission.isPresent());
		assertSame(PermissionTestModuleInstanceTable.STATIC_TEST_PERMISSION, staticPermission.get());
	}

	@Test
	public void testGetStaticPermissionByTableAndUuidWithUnknownUuid() {

		PermissionTestModuleInstanceTable table = PermissionTestModuleInstance.TABLE;
		UUID permissionUuid = UUID.fromString("deadbeef-0000-0000-0000-000000000000");

		var staticPermission = permissionRegistry.getStaticPermission(table, permissionUuid);

		assertFalse(staticPermission.isPresent());
	}

	private <M extends IEmfModule<?>> M getModule(Class<M> moduleClass) {

		return CurrentEmfModuleRegistry.get().getModule(moduleClass);
	}
}
