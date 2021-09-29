package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.test.module.EmfTestModule;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlpha;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlphaRoles;
import com.softicar.platform.emf.test.module.beta.TestModuleBeta;
import com.softicar.platform.emf.test.module.beta.TestModuleBetaRoles;
import com.softicar.platform.emf.test.module.role.RoleTestModule;
import com.softicar.platform.emf.test.module.role.RoleTestModuleInstance;
import com.softicar.platform.emf.test.module.role.RoleTestModuleInstanceTable;
import java.util.Collection;
import java.util.UUID;
import org.junit.Test;

/**
 * TODO add further test cases for {@link EmfRoleRegistry}
 *
 * @author Alexander Schmidt
 */
public class EmfRoleRegistryTest extends AbstractEmfTest {

	private final IEmfRoleRegistry roleRegistry;
	private final TestModuleAlpha moduleAlpha;
	private final TestModuleBeta moduleBeta;
	private final RoleTestModule roleTestModule;
	private final EmfTestModule testModule;

	public EmfRoleRegistryTest() {

		this.roleRegistry = CurrentEmfRoleRegistry.get();
		this.moduleAlpha = getModule(TestModuleAlpha.class);
		this.moduleBeta = getModule(TestModuleBeta.class);
		this.roleTestModule = getModule(RoleTestModule.class);
		this.testModule = getModule(EmfTestModule.class);
	}

	@Test
	public void testGetModules() {

		Collection<IEmfModule<?>> modules = roleRegistry.getModules();

		assertEquals(4, modules.size());
		assertTrue(modules.contains(moduleAlpha));
		assertTrue(modules.contains(moduleBeta));
		assertTrue(modules.contains(roleTestModule));
		assertTrue(modules.contains(testModule));
	}

	@Test
	public void testGetStaticRole() {

		IEmfStaticRole<?> alphaRole1 = roleRegistry.getStaticRole(TestModuleAlphaRoles.ROLE_ONE.getAnnotatedUuid()).get();
		IEmfStaticRole<?> alphaRole2 = roleRegistry.getStaticRole(TestModuleAlphaRoles.ROLE_TWO.getAnnotatedUuid()).get();
		IEmfStaticRole<?> betaRole1 = roleRegistry.getStaticRole(TestModuleBetaRoles.ROLE_ONE.getAnnotatedUuid()).get();
		IEmfStaticRole<?> betaRole2 = roleRegistry.getStaticRole(TestModuleBetaRoles.ROLE_TWO.getAnnotatedUuid()).get();
		IEmfStaticRole<?> roleTestRole = roleRegistry.getStaticRole(RoleTestModuleInstanceTable.STATIC_TEST_ROLE.getAnnotatedUuid()).get();

		assertSame(TestModuleAlphaRoles.ROLE_ONE, alphaRole1);
		assertSame(TestModuleAlphaRoles.ROLE_TWO, alphaRole2);
		assertSame(TestModuleBetaRoles.ROLE_ONE, betaRole1);
		assertSame(TestModuleBetaRoles.ROLE_TWO, betaRole2);
		assertSame(RoleTestModuleInstanceTable.STATIC_TEST_ROLE, roleTestRole);
	}

	@Test
	public void testGetStaticRoles() {

		Collection<IEmfStaticRole<?>> alphaRoles = roleRegistry.getStaticRoles(moduleAlpha);
		Collection<IEmfStaticRole<?>> betaRoles = roleRegistry.getStaticRoles(moduleBeta);
		Collection<IEmfStaticRole<?>> roleTestRoles = roleRegistry.getStaticRoles(roleTestModule);

		assertEquals(2, alphaRoles.size());
		assertTrue(alphaRoles.contains(TestModuleAlphaRoles.ROLE_ONE));
		assertTrue(alphaRoles.contains(TestModuleAlphaRoles.ROLE_TWO));
		assertEquals(2, betaRoles.size());
		assertTrue(betaRoles.contains(TestModuleBetaRoles.ROLE_ONE));
		assertTrue(betaRoles.contains(TestModuleBetaRoles.ROLE_TWO));
		assertEquals(1, roleTestRoles.size());
		assertTrue(roleTestRoles.contains(RoleTestModuleInstanceTable.STATIC_TEST_ROLE));
	}

	@Test
	public void testGetStaticRoleByTableAndUuid() {

		RoleTestModuleInstanceTable table = RoleTestModuleInstance.TABLE;
		UUID roleUuid = RoleTestModuleInstanceTable.STATIC_TEST_ROLE.getAnnotatedUuid();

		var staticRole = roleRegistry.getStaticRole(table, roleUuid);

		assertTrue(staticRole.isPresent());
		assertSame(RoleTestModuleInstanceTable.STATIC_TEST_ROLE, staticRole.get());
	}

	@Test
	public void testGetStaticRoleByTableAndUuidWithUnknownUuid() {

		RoleTestModuleInstanceTable table = RoleTestModuleInstance.TABLE;
		UUID roleUuid = UUID.fromString("deadbeef-0000-0000-0000-000000000000");

		var staticRole = roleRegistry.getStaticRole(table, roleUuid);

		assertFalse(staticRole.isPresent());
	}

	private <M extends IEmfModule<?>> M getModule(Class<M> moduleClass) {

		return CurrentEmfModuleRegistry.get().getModule(moduleClass);
	}
}
