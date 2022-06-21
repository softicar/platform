package com.softicar.platform.core.module.permission;

import com.softicar.platform.common.container.derived.DerivedObject;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.role.permission.AGRolePermission;
import com.softicar.platform.core.module.role.user.AGRoleUser;
import com.softicar.platform.core.module.user.AGUser;
import java.util.UUID;

public class UserModulePermissionCache {

	private final DerivedObject<UserModulePermissionMap> permissions;

	public UserModulePermissionCache(AGUser user) {

		this.permissions = new DerivedObject<>(() -> new UserModulePermissionMap(user))//
			.addDependsOn(AGModuleInstancePermissionAssignment.TABLE)
			.addDependsOn(AGRolePermission.TABLE)
			.addDependsOn(AGRoleUser.TABLE)
			.addDependsOn(AGRole.TABLE);
	}

	public boolean hasModulePermission(AGModuleInstance moduleInstance, UUID permissionUuid) {

		return permissions.get().hasPermission(moduleInstance, permissionUuid);
	}
}
