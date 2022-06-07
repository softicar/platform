package com.softicar.platform.core.module.access.permission;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.permission.assignment.module.instance.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.access.permission.assignment.module.system.AGSystemModulePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.UUID;
import org.mockito.Mockito;

public abstract class AbstractModulePermissionTestBase extends AbstractModuleTest {

	protected static final UUID MODULE_UUID = UUID.fromString("09ccd4bd-b3ba-41ce-8d37-50daadf729c8");
	protected static final UUID PERMISSION_UUID = UUID.fromString("c6f8d2c6-224f-4afd-a413-6aac01416126");
	protected final AGUser user;
	protected final IEmfModule<?> module;

	public AbstractModulePermissionTestBase() {

		this.user = insertTestUser();
		this.module = Mockito.mock(IEmfModule.class);
		Mockito.when(this.module.getAnnotatedUuid()).thenReturn(MODULE_UUID);
	}

	protected AGModuleInstancePermissionAssignment insertModulePermissionAssignment(AGModuleInstance moduleInstance, boolean active) {

		return new AGModuleInstancePermissionAssignment()//
			.setModuleInstance(moduleInstance)
			.setUser(user)
			.setPermission(AGUuid.getOrCreate(PERMISSION_UUID))
			.setActive(active)
			.save();
	}

	protected AGSystemModulePermissionAssignment insertPermissionAssignment(boolean active) {

		return new AGSystemModulePermissionAssignment()//
			.setModule(AGUuid.getOrCreate(MODULE_UUID))
			.setUser(user)
			.setPermission(AGUuid.getOrCreate(PERMISSION_UUID))
			.setActive(active)
			.save();
	}
}
