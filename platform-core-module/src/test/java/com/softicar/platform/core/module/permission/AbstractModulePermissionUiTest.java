package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.test.module.standard.alpha.TestModuleAlpha;
import com.softicar.platform.core.module.test.module.standard.beta.TestModuleBeta;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.test.module.EmfTestModuleRegistry;
import java.util.UUID;
import org.junit.After;

public abstract class AbstractModulePermissionUiTest extends AbstractModuleTest {

	private final EmfTestModuleRegistry moduleRegistry;

	public AbstractModulePermissionUiTest() {

		this.moduleRegistry = new EmfTestModuleRegistry();
		this.moduleRegistry.registerModule(new TestModuleAlpha());
		this.moduleRegistry.registerModule(new TestModuleBeta());

		CurrentEmfModuleRegistry.set(moduleRegistry);
	}

	@After
	public void resetModuleRegistry() {

		CurrentEmfModuleRegistry.reset();
	}

	protected EmfTestModuleRegistry getModuleRegistry() {

		return moduleRegistry;
	}

	protected TestModuleAlpha getTestModuleAlpha() {

		return getModule(TestModuleAlpha.class);
	}

	protected TestModuleBeta getTestModuleBeta() {

		return getModule(TestModuleBeta.class);
	}

	protected <M extends IEmfModule<?>> M getModule(Class<M> moduleClass) {

		return CurrentEmfModuleRegistry.get().getModule(moduleClass);
	}

	protected AGModuleInstancePermissionAssignment insertOrUpdateOwnership(AGModuleInstance moduleInstance, UUID permissionUuid, AGUser user, boolean active) {

		AGModuleInstancePermissionAssignment assignment = AGModuleInstancePermissionAssignment//
			.loadByUserAndModuleInstanceAndPermission(user, moduleInstance, AGUuid.getOrCreate(permissionUuid));
		if (assignment == null) {
			assignment = new AGModuleInstancePermissionAssignment()//
				.setModuleInstance(moduleInstance)
				.setPermission(permissionUuid)
				.setUser(user);
		}
		return assignment//
			.setActive(active)
			.save();
	}
}
