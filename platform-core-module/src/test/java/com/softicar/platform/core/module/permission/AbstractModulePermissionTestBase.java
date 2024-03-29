package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.UUID;

public abstract class AbstractModulePermissionTestBase extends AbstractModuleTest {

	protected static final UUID PERMISSION_UUID = UUID.fromString("c6f8d2c6-224f-4afd-a413-6aac01416126");
	protected final AGUser user;

	public AbstractModulePermissionTestBase() {

		this.user = insertTestUser();
	}

	protected AGModuleInstancePermissionAssignment insertPermissionAssignment(boolean active) {

		return new AGModuleInstancePermissionAssignment()//
			.setModuleInstanceBase(AGCoreModuleInstance.getInstance().pk())
			.setUser(user)
			.setPermission(AGUuid.getOrCreate(PERMISSION_UUID))
			.setActive(active)
			.save();
	}
}
