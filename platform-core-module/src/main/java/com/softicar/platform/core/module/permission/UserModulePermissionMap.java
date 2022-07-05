package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.role.permission.AGRolePermission;
import com.softicar.platform.core.module.role.user.AGRoleUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

class UserModulePermissionMap {

	private final AGUser user;
	private final Map<AGModuleInstanceBase, Set<UUID>> permissions;

	private UserModulePermissionMap(AGUser user) {

		this.user = user;
		this.permissions = new TreeMap<>();
	}

	public static UserModulePermissionMap load(AGUser user) {

		var map = new UserModulePermissionMap(user);
		map.loadDirectAssignments();
		map.loadRolePermissions();
		return map;
	}

	public boolean hasPermission(AGModuleInstanceBase moduleInstanceBase, UUID permissionUuid) {

		return permissions//
			.getOrDefault(moduleInstanceBase, Collections.emptySet())
			.contains(permissionUuid);
	}

	private void loadDirectAssignments() {

		AGModuleInstancePermissionAssignment.TABLE//
			.createSelect()
			.where(AGModuleInstancePermissionAssignment.USER.isEqual(user))
			.where(AGModuleInstancePermissionAssignment.ACTIVE)
			.forEach(this::addPermission);
	}

	private void loadRolePermissions() {

		AGRolePermission.TABLE//
			.createSelect()
			.where(AGRolePermission.ACTIVE)
			.join(AGRolePermission.ROLE)
			.where(AGRole.ACTIVE)
			.joinReverse(AGRoleUser.ROLE)
			.where(AGRoleUser.ACTIVE)
			.where(AGRoleUser.USER.isEqual(user))
			.forEach(this::addPermission);
	}

	private void addPermission(AGModuleInstancePermissionAssignment assignment) {

		addPermission(assignment.getModuleInstanceBase(), assignment.getPermission());
	}

	private void addPermission(AGRolePermission permission) {

		addPermission(permission.getModuleInstanceBase(), permission.getPermissionUuid());
	}

	private void addPermission(AGModuleInstanceBase moduleInstanceBase, AGUuid permissionUuid) {

		permissions//
			.computeIfAbsent(moduleInstanceBase, dummy -> new TreeSet<>())
			.add(permissionUuid.getUuid());
	}
}
