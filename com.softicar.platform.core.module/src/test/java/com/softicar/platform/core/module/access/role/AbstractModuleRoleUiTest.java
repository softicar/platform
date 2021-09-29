package com.softicar.platform.core.module.access.role;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.test.module.standard.alpha.TestStandardModuleAlpha;
import com.softicar.platform.core.module.test.module.standard.beta.TestStandardModuleBeta;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.test.module.EmfTestModuleRegistry;
import java.util.UUID;
import org.junit.After;

public abstract class AbstractModuleRoleUiTest extends AbstractModuleTest {

	private final EmfTestModuleRegistry moduleRegistry;

	public AbstractModuleRoleUiTest() {

		this.moduleRegistry = new EmfTestModuleRegistry();
		this.moduleRegistry.registerModule(new TestStandardModuleAlpha());
		this.moduleRegistry.registerModule(new TestStandardModuleBeta());

		CurrentEmfModuleRegistry.set(moduleRegistry);
	}

	@After
	public void resetModuleRegistry() {

		CurrentEmfModuleRegistry.reset();
	}

	protected EmfTestModuleRegistry getModuleRegistry() {

		return moduleRegistry;
	}

	protected TestStandardModuleAlpha getTestModuleAlpha() {

		return getModule(TestStandardModuleAlpha.class);
	}

	protected TestStandardModuleBeta getTestModuleBeta() {

		return getModule(TestStandardModuleBeta.class);
	}

	protected <M extends IEmfModule<?>> M getModule(Class<M> moduleClass) {

		return CurrentEmfModuleRegistry.get().getModule(moduleClass);
	}

	protected AGModuleInstanceRoleAssignment insertOrUpdateMembership(AGModuleInstance moduleInstance, UUID roleUuid, AGUser user, boolean active) {

		AGModuleInstanceRoleAssignment membership = AGModuleInstanceRoleAssignment//
			.loadByUserAndModuleInstanceAndRole(user, moduleInstance, AGUuid.getOrCreate(roleUuid));
		if (membership == null) {
			membership = new AGModuleInstanceRoleAssignment()//
				.setModuleInstance(moduleInstance)
				.setRole(roleUuid)
				.setUser(user);
		}
		return membership//
			.setActive(active)
			.save();
	}
}
