package com.softicar.platform.core.module.access.role;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.access.role.assignment.module.system.AGSystemModuleRoleAssignment;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.UUID;
import org.mockito.Mockito;

public abstract class AbstractModuleRoleTestBase extends AbstractModuleTest {

	protected static final UUID MODULE_UUID = UUID.fromString("09ccd4bd-b3ba-41ce-8d37-50daadf729c8");
	protected static final UUID ROLE_UUID = UUID.fromString("c6f8d2c6-224f-4afd-a413-6aac01416126");
	protected final AGUser user;
	protected final IEmfModule<?> module;

	public AbstractModuleRoleTestBase() {

		this.user = insertTestUser();
		this.module = Mockito.mock(IEmfModule.class);
		Mockito.when(this.module.getAnnotatedUuid()).thenReturn(MODULE_UUID);
	}

	protected AGModuleInstanceRoleAssignment insertModuleRoleMembership(AGModuleInstance moduleInstance, boolean active) {

		return new AGModuleInstanceRoleAssignment()//
			.setModuleInstance(moduleInstance)
			.setUser(user)
			.setRole(AGUuid.getOrCreate(ROLE_UUID))
			.setActive(active)
			.save();
	}

	protected AGSystemModuleRoleAssignment insertRoleMembership(boolean active) {

		return new AGSystemModuleRoleAssignment()//
			.setModule(AGUuid.getOrCreate(MODULE_UUID))
			.setUser(user)
			.setRole(AGUuid.getOrCreate(ROLE_UUID))
			.setActive(active)
			.save();
	}
}
